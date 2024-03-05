package org.skyemoon.index12306.biz.orderservice.mq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.skyemoon.index12306.biz.orderservice.common.constant.OrderRocketMQConstant;
import org.skyemoon.index12306.biz.orderservice.mq.domain.MessageWrapper;
import org.skyemoon.index12306.biz.orderservice.mq.event.PayResultCallbackOrderEvent;
import org.skyemoon.index12306.framework.starter.idempotent.annotation.Idempotent;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentSceneEnum;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付结果回调订单消费者
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = OrderRocketMQConstant.PAY_GLOBAL_TOPIC_KEY,
        selectorExpression = OrderRocketMQConstant.PAY_RESULT_CALLBACK_TAG_KEY,
        consumerGroup = OrderRocketMQConstant.PAY_RESULT_CALLBACK_ORDER_CG_KEY
)
public class PayResultCallbackOrderConsumer implements RocketMQListener<MessageWrapper<PayResultCallbackOrderEvent>> {

//    private final OrderService orderService;

    @Override
    @Idempotent(
            uniqueKeyPrefix = "index12306-order:pay_result_callback:",
            key = "#message.getKeys()+'_'+#message.hashCode()",
            type = IdempotentTypeEnum.SPEL,
            scene = IdempotentSceneEnum.MQ,
            keyTimeout = 7200L
    )
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(MessageWrapper<PayResultCallbackOrderEvent> payResultCallbackOrderEventMessageWrapper) {

    }
}
