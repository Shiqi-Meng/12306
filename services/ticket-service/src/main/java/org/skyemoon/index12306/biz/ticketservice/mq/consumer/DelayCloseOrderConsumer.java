package org.skyemoon.index12306.biz.ticketservice.mq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.skyemoon.index12306.biz.ticketservice.common.constant.TicketRocketMQConstant;
import org.skyemoon.index12306.biz.ticketservice.mq.event.DelayCloseOrderEvent;
import org.skyemoon.index12306.framework.starter.cache.DistributedCache;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = TicketRocketMQConstant.ORDER_DELAY_CLOSE_TOPIC_KEY,
        selectorExpression = TicketRocketMQConstant.ORDER_DELAY_CLOSE_TAG_KEY,
        consumerGroup = TicketRocketMQConstant.TICKET_DELAY_CLOSE_CG_KEY
)
public final class DelayCloseOrderConsumer implements RocketMQListener<DelayCloseOrderEvent> {

    private final DistributedCache distributedCache;
    @Override
    public void onMessage(DelayCloseOrderEvent delayCloseOrderEvent) {

    }
}
