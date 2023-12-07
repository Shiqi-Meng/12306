package org.skyemoon.index12306.biz.userservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skyemoon.index12306.biz.userservice.common.enums.VerifyStatusEnum;
import org.skyemoon.index12306.biz.userservice.dao.entity.PassengerDO;
import org.skyemoon.index12306.biz.userservice.dao.mapper.PassengerMapper;
import org.skyemoon.index12306.biz.userservice.dto.req.PassengerReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerActualRespDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerRespDTO;
import org.skyemoon.index12306.biz.userservice.service.PassengerService;
import org.skyemoon.index12306.framework.starter.cache.DistributedCache;
import org.skyemoon.index12306.framework.starter.convention.exception.ServiceException;
import org.skyemoon.index12306.frameworks.starter.user.core.UserContext;
import org.springframework.stereotype.Service;
import org.skyemoon.index12306.framework.starter.common.toolkit.BeanUtil;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.skyemoon.index12306.biz.userservice.common.constant.RedisKeyConstant.USER_PASSENGER_LIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerMapper passengerMapper;
    private final PlatformTransactionManager transactionManager;
    private final DistributedCache distributedCache;

    @Override
    public List<PassengerRespDTO> listPassengerQueryByUsername(String username) {
        String actualUserPassengerListStr = getActualUserPassengerListStr(username);
        return Optional.ofNullable(actualUserPassengerListStr)
                .map(each -> JSON.parseArray(each, PassengerDO.class))
                .map(each -> BeanUtil.convert(each, PassengerRespDTO.class))
                .orElse(null);
    }

    @Override
    public List<PassengerActualRespDTO> listPassengerQueryByIds(String username, List<Long> ids) {
        String actualUserPassengerListStr = getActualUserPassengerListStr(username);
        if (StrUtil.isEmpty(actualUserPassengerListStr)) {
            return null;
        }
        return JSON.parseArray(actualUserPassengerListStr, PassengerDO.class)
                .stream().filter(passengerDO -> ids.contains(passengerDO.getId()))
                .map(each -> BeanUtil.convert(each, PassengerActualRespDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void savePassenger(PassengerReqDTO requestParam) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        String username = UserContext.getUsername();
        try {
            PassengerDO passengerDO = BeanUtil.convert(requestParam, PassengerDO.class);
            passengerDO.setUsername(username);
            passengerDO.setCreateDate(new Date());
            passengerDO.setVerifyStatus(VerifyStatusEnum.REVIEWED.getCode());
            int inserted = passengerMapper.insert(passengerDO);
            if (!SqlHelper.retBool(inserted)) {
                throw new ServiceException(String.format("[%s] 新增乘车人失败", username));
            }
            transactionManager.commit(transactionStatus);
        } catch (Exception ex) {
            if (ex instanceof ServiceException) {
                log.error("{}, 请求参数：{}", ex.getMessage(), JSON.toJSONString(requestParam));
            } else {
                log.error("[{}] 新增乘车人失败，请求参数：{}", username, JSON.toJSONString(requestParam), ex);
            }
            transactionManager.rollback(transactionStatus);
            throw ex;
        }
//        delUserPassengerCache(username);
    }

    private String getActualUserPassengerListStr(String username) {
        return distributedCache.safeGet(
                USER_PASSENGER_LIST + username,
                String.class,
                () -> {
                    LambdaQueryWrapper<PassengerDO> queryWrapper = Wrappers.lambdaQuery(PassengerDO.class)
                            .eq(PassengerDO::getUsername, username);
                    List<PassengerDO> passengerDOList = passengerMapper.selectList(queryWrapper);
                    return CollUtil.isNotEmpty(passengerDOList) ? JSON.toJSONString(passengerDOList) : null;
                },
                1,
                TimeUnit.DAYS
        );
    }
}
