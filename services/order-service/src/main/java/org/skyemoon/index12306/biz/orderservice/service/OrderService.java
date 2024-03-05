package org.skyemoon.index12306.biz.orderservice.service;

import org.skyemoon.index12306.biz.orderservice.dto.domain.OrderStatusReversalDTO;
import org.skyemoon.index12306.biz.orderservice.dto.req.CancelTicketOrderReqDTO;
import org.skyemoon.index12306.biz.orderservice.dto.req.TicketOrderCreateReqDTO;
import org.skyemoon.index12306.biz.orderservice.dto.req.TicketOrderPageQueryReqDTO;
import org.skyemoon.index12306.biz.orderservice.dto.req.TicketOrderSelfPageQueryReqDTO;
import org.skyemoon.index12306.biz.orderservice.dto.resp.TicketOrderDetailRespDTO;
import org.skyemoon.index12306.biz.orderservice.dto.resp.TicketOrderDetailSelfRespDTO;
import org.skyemoon.index12306.biz.orderservice.mq.event.PayResultCallbackOrderEvent;
import org.skyemoon.index12306.framework.starter.convention.page.PageResponse;

/**
 * 订单接口层
 */
public interface OrderService {

    /**
     * 根据订单号查询订单，没有缓存直接查询数据库
     *
     * @param orderSn 订单号
     * @return 订单详情
     */
    TicketOrderDetailRespDTO queryTicketOrderByOrderSn(String orderSn);

    /**
     * 根据用户名分页查询车票订单
     *
     * @param requestParam 车票分页查询对象
     * @return 订单分页详情
     */
    PageResponse<TicketOrderDetailRespDTO> pageTicketOrder(TicketOrderPageQueryReqDTO requestParam);

    /**
     * 创建火车票订单
     *
     * @param requestParam 车票订单创建参数
     * @return 订单号
     */
    String createTicketOrder(TicketOrderCreateReqDTO requestParam);

    /**
     * 关闭火车票订单
     *
     * @param requestParam 关闭火车票订单入参
     * @return 是否关闭成功
     */
    boolean closeTickOrder(CancelTicketOrderReqDTO requestParam);

    /**
     * 取消火车票订单
     *
     * @param requestParam 取消火车票订单参数
     * @return 是否取消成功
     */
    boolean cancelTickOrder(CancelTicketOrderReqDTO requestParam);

    /**
     * 订单状态反转
     *
     * @param requestParam 请求入参
     */
    void statusReversal(OrderStatusReversalDTO requestParam);

    /**
     * 支付结果回调订单
     *
     * @param requestParam 请求入参
     */
    void payCallbackOrder(PayResultCallbackOrderEvent requestParam);

    /**
     * 查询本人车票订单
     *
     * @param requestParam 请求参数
     * @return 本人车票订单集合
     */
    PageResponse<TicketOrderDetailSelfRespDTO> pageSelfTicketOrder(TicketOrderSelfPageQueryReqDTO requestParam);
}
