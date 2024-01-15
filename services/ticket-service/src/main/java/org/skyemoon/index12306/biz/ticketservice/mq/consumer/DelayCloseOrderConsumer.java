package org.skyemoon.index12306.biz.ticketservice.mq.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.skyemoon.index12306.biz.ticketservice.common.constant.TicketRocketMQConstant;
import org.skyemoon.index12306.biz.ticketservice.dto.domain.RouteDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.req.CancelTicketOrderReqDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TrainPurchaseTicketRespDTO;
import org.skyemoon.index12306.biz.ticketservice.mq.domain.MessageWrapper;
import org.skyemoon.index12306.biz.ticketservice.mq.event.DelayCloseOrderEvent;
import org.skyemoon.index12306.biz.ticketservice.remote.TicketOrderRemoteService;
import org.skyemoon.index12306.biz.ticketservice.remote.dto.TicketOrderDetailRespDTO;
import org.skyemoon.index12306.biz.ticketservice.remote.dto.TicketOrderPassengerDetailRespDTO;
import org.skyemoon.index12306.biz.ticketservice.service.SeatService;
import org.skyemoon.index12306.biz.ticketservice.service.TrainStationService;
import org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.tokenbucket.TicketAvailabilityTokenBucket;
import org.skyemoon.index12306.framework.starter.cache.DistributedCache;
import org.skyemoon.index12306.framework.starter.common.toolkit.BeanUtil;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.skyemoon.index12306.biz.ticketservice.common.constant.RedisKeyConstant.TRAIN_STATION_REMAINING_TICKET;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = TicketRocketMQConstant.ORDER_DELAY_CLOSE_TOPIC_KEY,
        selectorExpression = TicketRocketMQConstant.ORDER_DELAY_CLOSE_TAG_KEY,
        consumerGroup = TicketRocketMQConstant.TICKET_DELAY_CLOSE_CG_KEY
)
public final class DelayCloseOrderConsumer implements RocketMQListener<MessageWrapper<DelayCloseOrderEvent>> {

    private final DistributedCache distributedCache;
    private final SeatService seatService;
    private final TrainStationService trainStationService;
    private final TicketOrderRemoteService ticketOrderRemoteService;
    private final TicketAvailabilityTokenBucket ticketAvailabilityTokenBucket;

    @Value("${ticket.availability.cache-update.type:}")
    private String ticketAvailabilityCacheUpdateType;

    @Override
    public void onMessage(MessageWrapper<DelayCloseOrderEvent> delayCloseOrderEventMessageWrapper) {
        log.info("[延迟关闭订单] 开始消费:{}", JSON.toJSONString(delayCloseOrderEventMessageWrapper));
        DelayCloseOrderEvent delayCloseOrderEvent = delayCloseOrderEventMessageWrapper.getMessage();
        String orderSn = delayCloseOrderEvent.getOrderSn();
        Result<Boolean> closedTickOrder;
        try {
            closedTickOrder = ticketOrderRemoteService.closeTickOrder(new CancelTicketOrderReqDTO(orderSn));
        } catch (Throwable ex) {
            log.error("[延迟关闭订单] 订单号:{} 远程调用订单服务失败", orderSn, ex);
            throw ex;
        }
        if (closedTickOrder.isSuccess() && !StrUtil.equals(ticketAvailabilityCacheUpdateType, "binlog")) {
            if (!closedTickOrder.getData()) {
                log.info("[延迟关闭订单] 订单号:{} 用户已支付订单", orderSn);
                return;
            }
            String trainId = delayCloseOrderEvent.getTrainId();
            String departure = delayCloseOrderEvent.getDeparture();
            String arrival = delayCloseOrderEvent.getArrival();
            List<TrainPurchaseTicketRespDTO> trainPurchaseTicketResults = delayCloseOrderEvent.getTrainPurchaseTicketResults();
            try {
                seatService.unlock(trainId, departure, arrival, trainPurchaseTicketResults);
            } catch (Throwable ex) {
                log.error("[延迟关闭订单] 订单号：{} 回滚列车DB座位状态失败", orderSn, ex);
                throw ex;
            }
            try {
                StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) distributedCache.getInstance();
                Map<Integer, List<TrainPurchaseTicketRespDTO>> seatTypeMap = trainPurchaseTicketResults.stream()
                        .collect(Collectors.groupingBy(TrainPurchaseTicketRespDTO::getSeatType));
                List<RouteDTO> routeDTOList = trainStationService.listTakeoutTrainStationRoute(trainId, departure, arrival);
                routeDTOList.forEach(each -> {
                    String keySuffix = StrUtil.join("_", trainId, each.getStartStation(), each.getEndStation());
                    seatTypeMap.forEach((seatType, trainPurchaseTicketRespDTOList) -> {
                        stringRedisTemplate.opsForHash()
                                .increment(TRAIN_STATION_REMAINING_TICKET + keySuffix, String.valueOf(seatType), trainPurchaseTicketRespDTOList.size());
                    });
                });
                TicketOrderDetailRespDTO ticketOrderDetail = BeanUtil.convert(delayCloseOrderEvent, TicketOrderDetailRespDTO.class);
                ticketOrderDetail.setPassengerDetails(BeanUtil.convert(delayCloseOrderEvent.getTrainPurchaseTicketResults(), TicketOrderPassengerDetailRespDTO.class));
                ticketAvailabilityTokenBucket.rollbackInBucket(ticketOrderDetail);
            } catch (Throwable ex) {
                log.error("[延迟关闭订单] 订单号：{} 回滚列车Cache余票失败", orderSn, ex);
                throw ex;
            }
        }
    }

}
