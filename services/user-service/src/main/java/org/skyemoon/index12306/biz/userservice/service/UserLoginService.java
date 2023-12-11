package org.skyemoon.index12306.biz.userservice.service;

import org.skyemoon.index12306.biz.userservice.dto.req.UserLoginReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserLoginRespDTO;

public interface UserLoginService {

    /**
     * 用户登录接口
     *
     * @param requestParam 用户登录入参
     * @return 用户登录返回结果
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 通过 Token 检查用户是否登录
     *
     * @param accessToken 用户登录 Token 凭证
     * @return 用户是否登录返回结果
     */
    UserLoginRespDTO checkLogin(String accessToken);

    /**
     * 用户退出登录
     * @param accessToken 用户登录 Token 凭证
     */
    void logout(String accessToken);
}

