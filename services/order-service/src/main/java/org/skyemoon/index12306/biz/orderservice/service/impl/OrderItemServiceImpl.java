package org.skyemoon.index12306.biz.orderservice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.skyemoon.index12306.biz.orderservice.common.enums.OrderCanalErrorCodeEnum;
import org.skyemoon.index12306.biz.orderservice.dao.entity.OrderDO;
import org.skyemoon.index12306.biz.orderservice.dao.entity.OrderItemDO;
import org.skyemoon.index12306.biz.orderservice.dao.mapper.OrderItemMapper;
import org.skyemoon.index12306.biz.orderservice.dao.mapper.OrderMapper;
import org.skyemoon.index12306.biz.orderservice.dto.domain.OrderItemStatusReversalDTO;
import org.skyemoon.index12306.biz.orderservice.dto.req.TicketOrderItemQueryReqDTO;
import org.skyemoon.index12306.biz.orderservice.dto.resp.TicketOrderPassengerDetailRespDTO;
import org.skyemoon.index12306.biz.orderservice.service.OrderItemService;
import org.skyemoon.index12306.framework.starter.common.toolkit.BeanUtil;
import org.skyemoon.index12306.framework.starter.convention.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单详情接口实现层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemDO> implements OrderItemService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public void orderItemStatusReversal(OrderItemStatusReversalDTO requestParam) {
        LambdaQueryWrapper<OrderDO> queryWrapper = Wrappers.lambdaQuery(OrderDO.class)
                .eq(OrderDO::getOrderSn, requestParam.getOrderSn());
        OrderDO orderDO = orderMapper.selectOne(queryWrapper);
        if (orderDO == null) {
            throw new ServiceException(OrderCanalErrorCodeEnum.ORDER_CANAL_UNKNOWN_ERROR);
        }
        RLock lock = redissonClient.getLock(StrBuilder.create("order:status-reversal:order_sn_").append(requestParam.getOrderSn()).toString());
        if (!lock.tryLock()) {
            log.warn("订单重复修改状态，状态反转请求参数：{}", JSON.toJSONString(requestParam));
        }
        try {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(requestParam.getOrderStatus());
            LambdaQueryWrapper<OrderDO> updateWrapper = Wrappers.lambdaQuery(OrderDO.class)
                    .eq(OrderDO::getOrderSn, requestParam.getOrderSn());
            int orderUpdateResult = orderMapper.update(updateOrderDO, updateWrapper);
            if (orderUpdateResult <= 0) {
                throw new ServiceException(OrderCanalErrorCodeEnum.ORDER_STATUS_REVERSAL_ERROR);
            }
            if (CollectionUtil.isNotEmpty(requestParam.getOrderItemDOList())) {
                List<OrderItemDO> orderItemDOList = requestParam.getOrderItemDOList();
                if (CollectionUtil.isNotEmpty(orderItemDOList)) {
                    orderItemDOList.forEach(o -> {
                        OrderItemDO orderItemDO = new OrderItemDO();
                        orderItemDO.setStatus(requestParam.getOrderItemStatus());
                        LambdaUpdateWrapper<OrderItemDO> orderItemUpdateWrapper = Wrappers.lambdaUpdate(OrderItemDO.class)
                                .eq(OrderItemDO::getOrderSn, requestParam.getOrderSn())
                                .eq(OrderItemDO::getRealName, o.getRealName());
                        int orderItemUpdateResult = orderItemMapper.update(orderItemDO, orderItemUpdateWrapper);
                        if (orderItemUpdateResult <= 0) {
                            throw new ServiceException(OrderCanalErrorCodeEnum.ORDER_ITEM_STATUS_REVERSAL_ERROR);
                        }
                    });
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<TicketOrderPassengerDetailRespDTO> queryTicketItemOrderById(TicketOrderItemQueryReqDTO requestParam) {
        LambdaQueryWrapper<OrderItemDO> queryWrapper = Wrappers.lambdaQuery(OrderItemDO.class)
                .eq(OrderItemDO::getOrderSn, requestParam.getOrderSn())
                .in(OrderItemDO::getId, requestParam.getOrderItemRecordIds());
        List<OrderItemDO> orderItemDOList = orderItemMapper.selectList(queryWrapper);
        return BeanUtil.convert(orderItemDOList, TicketOrderPassengerDetailRespDTO.class);
    }
}
