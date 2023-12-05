package org.skyemoon.index12306.framework.starter.web.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import static org.skyemoon.index12306.framework.starter.web.config.WebAutoConfiguration.INITIALIZE_PATH;

@Slf4j(topic = "Initialize DispatcherServlet")
public final class InitializeDispatcherServletController {

    @GetMapping(INITIALIZE_PATH)
    public void initializeDispatcherServlet() {
        log.info("Initialized the dispatcherServlet to improve the first response time of the interface...");
    }
}
