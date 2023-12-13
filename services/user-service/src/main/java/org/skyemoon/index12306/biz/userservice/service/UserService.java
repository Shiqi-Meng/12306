package org.skyemoon.index12306.biz.userservice.service;

import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryRespDTO;

public interface UserService {
    UserQueryRespDTO queryUserByUsername(String username);
}
