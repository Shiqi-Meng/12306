package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.filter.query;

import org.skyemoon.index12306.biz.ticketservice.common.enums.TicketChainMarkEnum;
import org.skyemoon.index12306.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import org.skyemoon.index12306.framework.starter.designpattern.chain.AbstractChainHandler;

/**
 * 列车车票查询过滤器
 */
public interface TrainTicketQueryChainFilter<T extends TicketPageQueryReqDTO> extends AbstractChainHandler<TicketPageQueryReqDTO> {

    @Override
    default String mark() {
        return TicketChainMarkEnum.TRAIN_QUERY_FILTER.name();
    }
}
