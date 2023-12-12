package org.skyemoon.index12306.framework.starter.idempotent.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public enum IdempotentMQConsumeStatusEnum {
    CONSUMING("0"),
    CONSUMED("1");

    @Getter
    private final String code;

    public static boolean isError(String consumeStatus) {
        return Objects.equals(CONSUMING.code, consumeStatus);
    }
}
