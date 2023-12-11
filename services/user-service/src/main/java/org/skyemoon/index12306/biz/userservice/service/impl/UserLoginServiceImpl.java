package org.skyemoon.index12306.biz.userservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.skyemoon.index12306.biz.userservice.dao.entity.UserDO;
import org.skyemoon.index12306.biz.userservice.dao.entity.UserMailDO;
import org.skyemoon.index12306.biz.userservice.dao.entity.UserPhoneDO;
import org.skyemoon.index12306.biz.userservice.dao.mapper.*;
import org.skyemoon.index12306.biz.userservice.dto.req.UserDeletionReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.req.UserLoginReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.req.UserRegisterReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserLoginRespDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.UserRegisterRespDTO;
import org.skyemoon.index12306.biz.userservice.service.UserLoginService;
import org.skyemoon.index12306.biz.userservice.service.UserService;
import org.skyemoon.index12306.framework.starter.cache.DistributedCache;
import org.skyemoon.index12306.framework.starter.convention.exception.ClientException;
import org.skyemoon.index12306.framework.starter.convention.exception.ServiceException;
import org.skyemoon.index12306.framework.starter.designpattern.chain.AbstractChainContext;
import org.skyemoon.index12306.frameworks.starter.user.core.UserInfoDTO;
import org.skyemoon.index12306.frameworks.starter.user.toolkit.JWTUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.skyemoon.index12306.biz.userservice.common.constant.RedisKeyConstant.USER_REGISTER_REUSE_SHARDING;
import static org.skyemoon.index12306.biz.userservice.toolkit.UserReuseUtil.hashShardingIdx;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserReuseMapper userReuseMapper;
    private final UserDeletionMapper userDeletionMapper;
    private final UserPhoneMapper userPhoneMapper;
    private final UserMailMapper userMailMapper;
    private final RedissonClient redissonClient;
    private final DistributedCache distributedCache;
    private final AbstractChainContext<UserRegisterReqDTO> abstractChainContext;
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        String usernameOrMailOrPhone = requestParam.getUsernameOrMailOrPhone();
        boolean mailFlag = false;
        for (char c : usernameOrMailOrPhone.toCharArray()) {
            if (c == '@') {
                mailFlag = true;
                break;
            }
        }
        String username;
        if (mailFlag) {
            LambdaQueryWrapper<UserMailDO> queryWrapper = Wrappers.lambdaQuery(UserMailDO.class)
                    .eq(UserMailDO::getMail, usernameOrMailOrPhone);
            username = Optional.ofNullable(userMailMapper.selectOne(queryWrapper))
                    .map(UserMailDO::getUsername)
                    .orElseThrow(() -> new ClientException("用户名/手机号/邮箱不存在"));
        } else {
            LambdaQueryWrapper<UserPhoneDO> queryWrapper = Wrappers.lambdaQuery(UserPhoneDO.class)
                    .eq(UserPhoneDO::getPhone, usernameOrMailOrPhone);
            username = Optional.ofNullable(userPhoneMapper.selectOne(queryWrapper))
                    .map(UserPhoneDO::getUsername)
                    .orElse(null);
        }
        username = Optional.ofNullable(username).orElse(requestParam.getUsernameOrMailOrPhone());
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getPassword, requestParam.getPassword())
                .select(UserDO::getId, UserDO::getUsername, UserDO::getRealName);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO != null) {
            UserInfoDTO userInfo = UserInfoDTO.builder()
                    .userId(String.valueOf(userDO.getId()))
                    .username(userDO.getUsername())
                    .realName(userDO.getRealName())
                    .build();
            String accessToken = JWTUtil.generateAccessToken(userInfo);
            UserLoginRespDTO actual = new UserLoginRespDTO(userInfo.getUserId(), requestParam.getUsernameOrMailOrPhone(), userDO.getRealName(), accessToken);
            distributedCache.put(accessToken, JSON.toJSONString(actual), 50, TimeUnit.MINUTES);
            return actual;
        }
        throw new ServiceException("账号不存在或密码错误");
    }

    @Override
    public UserLoginRespDTO checkLogin(String accessToken) {
        return distributedCache.get(accessToken, UserLoginRespDTO.class);
    }

    @Override
    public void logout(String accessToken) {
        if (StrUtil.isNotBlank(accessToken)) {
            distributedCache.delete(accessToken);
        }
    }

    @Override
    public Boolean hasUsername(String username) {
        boolean hasUsername = userRegisterCachePenetrationBloomFilter.contains(username);
        if (hasUsername) {
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            return instance.opsForSet().isMember(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
        }
        return true;
    }

    @Override
    public UserRegisterRespDTO register(UserRegisterReqDTO requestParam) {
        return null;
    }

    @Override
    public void deletion(UserDeletionReqDTO requestParam) {

    }
}
