package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.tokenbucket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 列车车票余量令牌锁，防瞬时并发
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class TicketAvailabilityTokenBucket {

    private static final String LUA_TICKET_AVAILABILITY_TOKEN_BUCKET_PATH = "lua/ticket_availability_token_bucket.lua";

    private static final String LUA_TICKET_AVAILABILITY_ROLLBACK_TOKEN_BUCKET_PATH = "lua/ticket_availability_rollback_token_bucket.lua";
}
