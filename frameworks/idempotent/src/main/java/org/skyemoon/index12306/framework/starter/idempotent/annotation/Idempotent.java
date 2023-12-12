package org.skyemoon.index12306.framework.starter.idempotent.annotation;

import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentSceneEnum;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentTypeEnum;

import java.lang.annotation.*;

/**
 * 幂等注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    String key() default "";

    String message() default "您操作太快，请稍后再试";

    String uniqueKeyPrefix() default "";

    long keyTimeout() default 3600L;

    IdempotentTypeEnum type() default IdempotentTypeEnum.PARAM;

    IdempotentSceneEnum scene() default IdempotentSceneEnum.RESTAPI;
}
