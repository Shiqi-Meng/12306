package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.filter.purchase;

import org.skyemoon.index12306.biz.ticketservice.common.enums.TicketChainMarkEnum;
import org.skyemoon.index12306.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import org.skyemoon.index12306.framework.starter.designpattern.chain.AbstractChainHandler;

public interface TrainPurchaseTicketChainFilter<T extends PurchaseTicketReqDTO> extends AbstractChainHandler<PurchaseTicketReqDTO> {

    @Override
    default String mark() {
        return TicketChainMarkEnum.TRAIN_PURCHASE_TICKET_FILTER.name();
    }
}
