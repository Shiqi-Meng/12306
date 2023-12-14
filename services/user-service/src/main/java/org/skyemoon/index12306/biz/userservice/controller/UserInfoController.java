package org.skyemoon.index12306.biz.userservice.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryRespDTO;
import org.skyemoon.index12306.biz.userservice.service.UserLoginService;
import org.skyemoon.index12306.biz.userservice.service.UserService;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.skyemoon.index12306.framework.starter.web.Results;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;
    private final UserLoginService userLoginService;

    /**
     * 根据用户名查询用户信息
     */
    public Result<UserQueryRespDTO> queryUserByUsername(@RequestParam("username") @NotEmpty String username) {
        return Results.success(userService.queryUserByUsername(username));
    }
}
