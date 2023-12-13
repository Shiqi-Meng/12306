package org.skyemoon.index12306.framework.starter.log.controller;

import org.skyemoon.index12306.framework.starter.log.annotation.ILog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {

    @ILog
    @GetMapping("/aop")
    public String AOPTest(String message) {
        System.out.println("My ILog message: " + message);
        return "Success!";
    }
}
