package org.skyemoon.index12306.biz.userservice.service;

import org.skyemoon.index12306.biz.userservice.dto.req.UserLoginReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserLoginRespDTO;

public interface UserLoginService {

    /**
     * 用户登录接口
     * @param requestParam 用户登录入参
     * @return 用户登录返回结果
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    UserLoginRespDTO checkLogin(String accessToken);
}

