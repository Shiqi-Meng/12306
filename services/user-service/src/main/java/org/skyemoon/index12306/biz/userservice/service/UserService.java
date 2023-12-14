package org.skyemoon.index12306.biz.userservice.service;

import jakarta.validation.constraints.NotEmpty;
import org.skyemoon.index12306.biz.userservice.dto.req.UserUpdateReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryActualRespDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryRespDTO;

/**
 * 用户信息接口层
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户详细信息
     */
    UserQueryRespDTO queryUserByUsername(@NotEmpty String username);

    /**
     * 根据用户名查询用户无脱敏信息
     *
     * @param username 用户名
     * @return 用户无脱敏信息
     */
    UserQueryActualRespDTO queryActualUserByUsername(@NotEmpty String username);

    /**
     * 修改用户
     *
     * @param requestParam 用户信息入参
     */
    void update(UserUpdateReqDTO requestParam);
}
