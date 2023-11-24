package org.skyemoon.index12306.framework.starter.bases;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Singleton object holder
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Singleton {

    private static final ConcurrentHashMap<String, Object> SINGLE_OBJECT_POOL = new ConcurrentHashMap<>();

    /**
     * get singleton object by key
     * @param key
     * @return
     * @param <T>
     */
    public static <T> T get(String key) {
        Object result = SINGLE_OBJECT_POOL.get(key);
        return result == null ? null : (T) result;
    }

    /**
     * get singleton object by key
     * <p> 为空时，通过 supplier 构建单例对象并放入容器
     * @param key
     * @param supplier
     * @return
     * @param <T>
     */
    public static <T> T get(String key, Supplier<T> supplier) {
        Object result = SINGLE_OBJECT_POOL.get(key);
        if (result == null && (result = supplier.get()) != null) {
            SINGLE_OBJECT_POOL.put(key, result);
        }
        return result != null ? (T) result : null;
    }

    /**
     * put object into holder
     * @param value
     */
    public static void put(Object value) {
        put(value.getClass().getName(), value);
    }

    /**
     * put object into holder
     * @param key
     * @param value
     */
    public static void put (String key, Object value) {
        SINGLE_OBJECT_POOL.put(key, value);
    }
}
