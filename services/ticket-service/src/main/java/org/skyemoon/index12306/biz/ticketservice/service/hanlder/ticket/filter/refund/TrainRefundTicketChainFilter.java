package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.filter.refund;

import org.skyemoon.index12306.biz.ticketservice.common.enums.TicketChainMarkEnum;
import org.skyemoon.index12306.biz.ticketservice.dto.req.RefundTicketReqDTO;
import org.skyemoon.index12306.framework.starter.designpattern.chain.AbstractChainHandler;

public interface TrainRefundTicketChainFilter<T extends RefundTicketReqDTO> extends AbstractChainHandler<RefundTicketReqDTO> {

    @Override
    default String mark() {
        return TicketChainMarkEnum.TRAIN_REFUND_TICKET_FILTER.name();
    }
}
