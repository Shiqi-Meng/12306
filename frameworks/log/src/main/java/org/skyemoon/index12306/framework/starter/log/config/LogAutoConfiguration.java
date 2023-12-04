package org.skyemoon.index12306.framework.starter.log.config;

import org.skyemoon.index12306.framework.starter.log.annotation.ILog;
import org.skyemoon.index12306.framework.starter.log.core.ILogPrintAspect;
import org.springframework.context.annotation.Bean;

public class LogAutoConfiguration {

    /**
     * {@link ILog} 日志打印 AOP 切面
     */
    @Bean
    public ILogPrintAspect iLogPrintAspect() {
        return new ILogPrintAspect();
    }
}
