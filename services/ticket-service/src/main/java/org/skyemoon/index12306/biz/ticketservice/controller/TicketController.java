package org.skyemoon.index12306.biz.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TicketPageQueryRespDTO;
import org.skyemoon.index12306.biz.ticketservice.service.TicketService;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.skyemoon.index12306.framework.starter.web.Results;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * 根据条件查询车票
     */
    @GetMapping("/api/ticket-service/ticket/query")
    public Result<TicketPageQueryRespDTO> pageListTicketQuery(TicketPageQueryReqDTO requestParam) {
        return Results.success(ticketService.pageListTicketQueryV1(requestParam));
    }
}
