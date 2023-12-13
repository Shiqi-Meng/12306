package org.skyemoon.index12306.biz.userservice.service.impl;

import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryRespDTO;
import org.skyemoon.index12306.biz.userservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserQueryRespDTO queryUserByUsername(String username) {
        return null;
    }
}
