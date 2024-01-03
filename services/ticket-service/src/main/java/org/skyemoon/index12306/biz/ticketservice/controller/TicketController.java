package org.skyemoon.index12306.biz.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.ticketservice.dto.req.PurchaseTicketReqDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.req.TicketPageQueryReqDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TicketPageQueryRespDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TicketPurchaseRespDTO;
import org.skyemoon.index12306.biz.ticketservice.service.TicketService;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.skyemoon.index12306.framework.starter.idempotent.annotation.Idempotent;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentSceneEnum;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentTypeEnum;
import org.skyemoon.index12306.framework.starter.log.annotation.ILog;
import org.skyemoon.index12306.framework.starter.web.Results;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /**
     * 购买车票 V1
     */
    @ILog
    @Idempotent(
            uniqueKeyPrefix = "index12306-ticket:lock_purchase-tickets:",
            key = "T(org.opengoofy.index12306.framework.starter.bases.ApplicationContextHolder).getBean('environment').getProperty('unique-name', '')"
            + "+'_'+"
            + "T(org.opengoofy.index12306.frameworks.starter.user.core.UserContext).getUsername()",
            message = "正在执行下单流程，请稍后...",
            type = IdempotentTypeEnum.SPEL,
            scene = IdempotentSceneEnum.RESTAPI
    )
    @PostMapping("/api/ticket-service/ticket/purchase")
    public Result<TicketPurchaseRespDTO> purchaseTickets(@RequestBody PurchaseTicketReqDTO requestParam) {
        return Results.success(ticketService.purchaseTicketsV1(requestParam));
    }
}
