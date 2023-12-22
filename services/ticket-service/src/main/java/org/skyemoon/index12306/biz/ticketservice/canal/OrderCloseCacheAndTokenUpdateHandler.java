package org.skyemoon.index12306.biz.ticketservice.canal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skyemoon.index12306.biz.ticketservice.mq.event.CanalBinlogEvent;
import org.skyemoon.index12306.framework.starter.designpattern.strategy.AbstractExecuteStrategy;
import org.springframework.stereotype.Component;

/**
 * 订单关闭或取消后置处理组件
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCloseCacheAndTokenUpdateHandler implements AbstractExecuteStrategy<CanalBinlogEvent, Void> {

//    private final TicketOrderRemoteService ticketOrderRemoteService;

}
