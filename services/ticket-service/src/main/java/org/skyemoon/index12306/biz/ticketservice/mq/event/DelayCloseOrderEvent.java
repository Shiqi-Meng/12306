package org.skyemoon.index12306.biz.ticketservice.mq.event;

import org.skyemoon.index12306.biz.ticketservice.dto.resp.TrainPurchaseTicketRespDTO;

import java.util.List;

public class DelayCloseOrderEvent {

    /**
     * 车次 ID
     */
    private String trainId;

    /**
     * 出发站点
     */
    private String departure;

    /**
     * 到达站点
     */
    private String arrival;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 乘车人购票信息
     */
    private List<TrainPurchaseTicketRespDTO> trainPurchaseTicketResult;
}
