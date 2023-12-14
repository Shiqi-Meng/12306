package org.skyemoon.index12306.biz.userservice.service;

import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryRespDTO;

/**
 * 用户信息接口层
 */
public interface UserService {

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param username 用户 ID
     * @return 用户详细信息
     */
    UserQueryRespDTO queryUserByUsername(String username);
}
