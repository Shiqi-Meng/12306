package org.skyemoon.index12306.biz.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.userservice.config.Result;
import org.skyemoon.index12306.biz.userservice.config.Results;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerRespDTO;
import org.skyemoon.index12306.biz.userservice.service.PassengerService;
import org.skyemoon.index12306.frameworks.starter.user.core.UserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PassengerController {

    private final PassengerService passengerService;

    /**
     * 根据用户名查询乘车人列表
     */
    @GetMapping("api/user-service/passenger/query")
    public Result<List<PassengerRespDTO>> listPassengerQueryByUsername() {
        return Results.success(passengerService.listPassengerQueryByUsername(UserContext.getUsername()));
    }

    /**
     * 根据乘车人ID集合查询乘车人列表
     */
}
