package org.skyemoon.index12306.framework.starter.distributedid.core.snowflake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WorkerId 包装器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkIdWrapper {

    /**
     * 工作ID
     */
    private Long workId;

    /**
     * 数据中心ID
     */
    private Long dataCenterId;
}
