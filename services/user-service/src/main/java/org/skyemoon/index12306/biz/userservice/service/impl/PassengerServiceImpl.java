package org.skyemoon.index12306.biz.userservice.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skyemoon.index12306.biz.userservice.dao.entity.PassengerDO;
import org.skyemoon.index12306.biz.userservice.dao.mapper.PassengerMapper;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerRespDTO;
import org.skyemoon.index12306.biz.userservice.service.PassengerService;
import org.springframework.stereotype.Service;
import org.skyemoon.index12306.framework.starter.common.toolkit.BeanUtil;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

//    private final PassengerMapper passengerMapper;

    @Override
    public List<PassengerRespDTO> listPassengerQueryByUsername(String username) {
        String actualUserPassengerListStr = getActualUserPassengerListStr(username);
        return Optional.ofNullable(actualUserPassengerListStr)
                .map(each -> JSON.parseArray(each, PassengerDO.class))
                .map(each -> BeanUtil.convert(each, PassengerRespDTO.class))
                .orElse(null);
    }

    private String getActualUserPassengerListStr(String username) {
        return null;
    }
}
