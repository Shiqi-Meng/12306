package org.skyemoon.index12306.biz.ticketservice.mq.consumer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.skyemoon.index12306.biz.ticketservice.common.constant.TicketRocketMQConstant;
import org.skyemoon.index12306.biz.ticketservice.common.enums.CanalExecuteStrategyMarkEnum;
import org.skyemoon.index12306.biz.ticketservice.mq.event.CanalBinlogEvent;
import org.skyemoon.index12306.framework.starter.designpattern.strategy.AbstractStrategyChoose;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 列车车票余量缓存更新消费端
 */
@RequiredArgsConstructor
@Slf4j
@Component
@RocketMQMessageListener(
        topic = TicketRocketMQConstant.CANAL_COMMON_SYNC_TOPIC_KEY,
        consumerGroup = TicketRocketMQConstant.CANAL_COMMON_SYNC_CG_KEY
)
public class CanalCommonSyncBinlogConsumer implements RocketMQListener<CanalBinlogEvent> {

    private final AbstractStrategyChoose abstractStrategyChoose;

    @Value("${ticket.availability.cache-update.type:}")
    private String ticketAvailabilityCacheUpdateType;

    @Override
    public void onMessage(CanalBinlogEvent message) {
        if (message.getIsDdl()
                || CollUtil.isEmpty(message.getOld())
                || !Objects.equal("UPDATE", message.getType())
                || !StrUtil.equals(ticketAvailabilityCacheUpdateType, "binlog")) {
            return;
        }
        abstractStrategyChoose.chooseAndExecute(
                message.getTable(),
                message,
                CanalExecuteStrategyMarkEnum.isPatternMatch(message.getTable())
        );
    }
}
