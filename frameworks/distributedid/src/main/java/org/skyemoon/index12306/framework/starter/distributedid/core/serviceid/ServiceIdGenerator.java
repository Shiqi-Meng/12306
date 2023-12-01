package org.skyemoon.index12306.framework.starter.distributedid.core.serviceid;

import org.skyemoon.index12306.framework.starter.distributedid.core.IdGenerator;
import org.skyemoon.index12306.framework.starter.distributedid.core.snowflake.SnowflakeIdInfo;

/**
 * 业务 ID 生成器
 */
public interface ServiceIdGenerator extends IdGenerator {

    /**
     * 根据 {@param serviceId} 生成雪花算法 ID
     */
    default long nextId(long serviceId) {
        return 0L;
    }

    default long nextId(String serviceId) {
        return 0L;
    }

    default String nextIdStr(long serviceId) {
        return null;
    }

    default String nextIdStr(String serviceId) {
        return null;
    }

    SnowflakeIdInfo parseSnowflakeId(long snowflakeId);
}
