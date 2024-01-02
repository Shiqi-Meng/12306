package org.skyemoon.index12306.biz.ticketservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.skyemoon.index12306.biz.ticketservice.dao.entity.TicketDO;
import org.skyemoon.index12306.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TicketPageQueryRespDTO;

/**
 * 车票接口
 */
public interface TicketService extends IService<TicketDO> {

    /**
     * 根据条件分页查询车票
     *
     * @param requestParam 分页查询车票请求参数
     * @return 查询车票返回结果
     */
    TicketPageQueryRespDTO pageListTicketQueryV1(TicketPageQueryReqDTO requestParam);

    /**
     * 根据条件分页查询车票 高性能版本
     *
     * @param requestParam 分页查询车票请求参数
     * @return 查询车票返回结果
     */
    TicketPageQueryRespDTO pageListTicketQueryV2(TicketPageQueryReqDTO requestParam);

}