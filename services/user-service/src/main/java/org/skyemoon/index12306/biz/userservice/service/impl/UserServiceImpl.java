package org.skyemoon.index12306.biz.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.userservice.dao.entity.UserDO;
import org.skyemoon.index12306.biz.userservice.dao.mapper.UserDeletionMapper;
import org.skyemoon.index12306.biz.userservice.dao.mapper.UserMailMapper;
import org.skyemoon.index12306.biz.userservice.dao.mapper.UserMapper;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserQueryRespDTO;
import org.skyemoon.index12306.biz.userservice.service.UserService;
import org.skyemoon.index12306.framework.starter.common.toolkit.BeanUtil;
import org.skyemoon.index12306.framework.starter.convention.exception.ClientException;
import org.springframework.stereotype.Service;

/**
 * 用户信息接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserDeletionMapper userDeletionMapper;
    private final UserMailMapper userMailMapper;

    @Override
    public UserQueryRespDTO queryUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在，请检查用户名是否正确");
        }
        return BeanUtil.convert(userDO, UserQueryRespDTO.class);
    }
}
