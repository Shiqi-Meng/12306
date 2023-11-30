package org.skyemoon.index12306.framework.starter.common.threadpool.build;

import java.io.Serializable;

public interface Builder<T> extends Serializable {

    /**
     * 构建方法
     *
     * @return 构建对象
     */
    T build();
}
