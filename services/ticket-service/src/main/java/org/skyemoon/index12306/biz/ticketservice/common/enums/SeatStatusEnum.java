package org.skyemoon.index12306.biz.ticketservice.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SeatStatusEnum {

    AVAILABLE(0),

    LOCKED(1),

    SOLD(2);

    @Getter
    private final Integer code;
}
