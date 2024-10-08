package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.filter.purchase;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import org.springframework.stereotype.Component;

/**
 * 购票流程过滤器 验证乘客是否重复购买
 */
@Component
@RequiredArgsConstructor
public class TrainPurchaseTicketRepeatChainHandler implements TrainPurchaseTicketChainFilter<PurchaseTicketReqDTO>{
    @Override
    public void handler(PurchaseTicketReqDTO requestParam) {
        // TODO 重复购买验证
    }

    @Override
    public int getOrder() {
        return 30;
    }
}
