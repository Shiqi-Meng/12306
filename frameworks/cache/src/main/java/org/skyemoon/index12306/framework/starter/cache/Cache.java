package org.skyemoon.index12306.framework.starter.cache;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

/**
 * 缓存接口
 */
public interface Cache {

    /**
     * 获取缓存
     * 泛型方法需要在返回值前加 <T> 标识
     * {@link NotBlank} 只能作用在String上，不能为null，且调用trim()后，长度必须大于零
     */
    <T> T get(@NotBlank String key, Class<T> clazz);

    /**
     * 放入缓存
     */
    void put(@NotBlank String key, Object value);

    /**
     * 如果 keys 全部不存在，则新增，返回 true，反之 false
     * {@link NotNull} 被注解的元素不能为null
     */
    Boolean putIfAllAbsent(@NotNull Collection<String> keys);

    /**
     * 删除缓存
     */
    Boolean delete(@NotBlank String key);

    /**
     * 删除 keys，返回删除数量
     */
    Long delete(@NotNull Collection<String> keys);

    /**
     * 判断 key 是否存在
     */
    Boolean hasKey(@NotBlank String key);

    /**
     * 获取缓存组件实例
     */
    Object getInstance();
}
