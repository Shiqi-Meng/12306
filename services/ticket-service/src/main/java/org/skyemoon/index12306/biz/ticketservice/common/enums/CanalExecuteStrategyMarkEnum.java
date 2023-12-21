package org.skyemoon.index12306.biz.ticketservice.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public enum CanalExecuteStrategyMarkEnum {

    T_SEAT("t_seat", null),

    T_ORDER("t_order", "^t_order_([0-9]+|1[0-6])");

    @Getter
    private final String actualTable;

    @Getter
    private final String patternMatchTable;

    public static boolean isPatternMatch(String tableName) {
        return Arrays.stream(CanalExecuteStrategyMarkEnum.values())
                .anyMatch(each -> StrUtil.isNotBlank(each.getPatternMatchTable()) &&
                        Pattern.compile(each.getPatternMatchTable()).matcher(tableName).matches());
    }
}
