package org.skyemoon.index12306.framework.starter.cache.core;

/**
 * 缓存加载器
 * {@link FunctionalInterface 函数式接口，只定义唯一抽象方法的接口}
 */
@FunctionalInterface
public interface CacheLoader<T> {

    /**
     * 加载缓存
     */
    T load();
}
