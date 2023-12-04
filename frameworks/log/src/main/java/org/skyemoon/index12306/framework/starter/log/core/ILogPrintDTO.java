package org.skyemoon.index12306.framework.starter.log.core;

import lombok.Data;

@Data
public class ILogPrintDTO {

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 请求入参
     */
    private Object[] inputParams;

    /**
     * 返回参数
     */
    private Object outputParams;
}
