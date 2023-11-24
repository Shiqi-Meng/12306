package org.skyemoon.index12306.framework.starter.bases.config;

import org.skyemoon.index12306.framework.starter.bases.ApplicationContextHolder;
import org.skyemoon.index12306.framework.starter.bases.init.ApplicationContentPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.skyemoon.index12306.framework.starter.bases.safa.FastJsonSafaMode;

/**
 * Application base auto configuration
 */
public class ApplicationBaseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextHolder congoApplicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContentPostProcessor congoApplicationContentPostProcessor(ApplicationContext applicationContext) {
        return new ApplicationContentPostProcessor(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "framework.fastjson.safa-mode", havingValue = "true")
    public FastJsonSafaMode congoFastJsonSafeMode() {
        return new FastJsonSafaMode();
    }
}
