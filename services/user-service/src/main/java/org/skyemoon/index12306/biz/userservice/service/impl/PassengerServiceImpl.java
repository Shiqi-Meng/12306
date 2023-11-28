package org.skyemoon.index12306.biz.userservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skyemoon.index12306.biz.userservice.dao.entity.PassengerDO;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerRespDTO;
import org.skyemoon.index12306.biz.userservice.service.PassengerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    @Override
    public List<PassengerRespDTO> listPassengerQueryByUsername(String username) {
        return null;
    }
}
