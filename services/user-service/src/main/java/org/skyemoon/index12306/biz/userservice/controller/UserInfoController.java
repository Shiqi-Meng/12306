package org.skyemoon.index12306.biz.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.userservice.service.UserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;
}
