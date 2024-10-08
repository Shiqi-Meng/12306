package org.skyemoon.index12306.biz.ticketservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.skyemoon.index12306.biz.ticketservice.common.enums.RegionStationQueryTypeEnum;
import org.skyemoon.index12306.biz.ticketservice.dao.entity.RegionDO;
import org.skyemoon.index12306.biz.ticketservice.dao.entity.StationDO;
import org.skyemoon.index12306.biz.ticketservice.dao.mapper.RegionMapper;
import org.skyemoon.index12306.biz.ticketservice.dao.mapper.StationMapper;
import org.skyemoon.index12306.biz.ticketservice.dto.req.RegionStationQueryReqDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.RegionStationQueryRespDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.StationQueryRespDTO;
import org.skyemoon.index12306.biz.ticketservice.service.RegionStationService;
import org.skyemoon.index12306.framework.starter.cache.DistributedCache;
import org.skyemoon.index12306.framework.starter.cache.core.CacheLoader;
import org.skyemoon.index12306.framework.starter.cache.toolkit.CacheUtil;
import org.skyemoon.index12306.framework.starter.common.enums.FlagEnum;
import org.skyemoon.index12306.framework.starter.common.toolkit.BeanUtil;
import org.skyemoon.index12306.framework.starter.convention.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.skyemoon.index12306.biz.ticketservice.common.constant.Index12306Constant.ADVANCE_TICKET_DAY;
import static org.skyemoon.index12306.biz.ticketservice.common.constant.RedisKeyConstant.*;

@Service
@RequiredArgsConstructor
public class RegionStationImpl implements RegionStationService {

    private final RegionMapper regionMapper;
    private final StationMapper stationMapper;
    private final DistributedCache distributedCache;
    private final RedissonClient redissonClient;

    @Override
    public List<RegionStationQueryRespDTO> listRegionStation(RegionStationQueryReqDTO requestParam) {
        String key;
        if (StrUtil.isNotBlank(requestParam.getName())) {
            key = REGION_STATION + requestParam.getName();
            return safeGetRegionStation(
                    key,
                    () -> {
                        LambdaQueryWrapper<StationDO> queryWrapper = Wrappers.lambdaQuery(StationDO.class)
                                .likeRight(StationDO::getName, requestParam.getName())
                                .or()
                                .likeRight(StationDO::getSpell, requestParam.getName());
                        List<StationDO> stationDOList = stationMapper.selectList(queryWrapper);
                        return JSON.toJSONString(BeanUtil.convert(stationDOList, RegionStationQueryRespDTO.class));
                    },
                    requestParam.getName()
            );
        }
        key = REGION_STATION + requestParam.getQueryType();
        LambdaQueryWrapper<RegionDO> queryWrapper = switch (requestParam.getQueryType()) {
            case 0 -> Wrappers.lambdaQuery(RegionDO.class)
                    .eq(RegionDO::getPopularFlag, FlagEnum.TRUE.code());
            case 1 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.A_E.getSpells());
            case 2 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.F_J.getSpells());
            case 3 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.K_O.getSpells());
            case 4 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.P_T.getSpells());
            case 5 -> Wrappers.lambdaQuery(RegionDO.class)
                    .in(RegionDO::getInitial, RegionStationQueryTypeEnum.U_Z.getSpells());
            default -> throw new ClientException("查询失败，请检查查询参数是否正确");
        };
        return safeGetRegionStation(
                key,
                () -> {
                    List<RegionDO> regionDOList = regionMapper.selectList(queryWrapper);
                    return com.alibaba.fastjson2.JSON.toJSONString(BeanUtil.convert(regionDOList,
                            RegionStationQueryRespDTO.class));
                },
                String.valueOf(requestParam.getQueryType())
        );
    }

    private List<RegionStationQueryRespDTO> safeGetRegionStation(final String key, CacheLoader<String> loader,
                                                                 String param) {
        // 返回的最终结果
        List<RegionStationQueryRespDTO> result;
        // 获取缓存，缓存不为空直接返回
        if (CollUtil.isNotEmpty(result = JSON.parseArray(distributedCache.get(key, String.class),
                RegionStationQueryRespDTO.class))) {
            return result;
        }
        // 缓存为空，加载数据库数据到缓存
        String lockKey = String.format(LOCK_QUERY_REGION_STATION_LIST, param);
        // 获取分布式锁
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        try {
            // 双重判定，防止已经加载到缓存的数据继续读数据库
            if (CollUtil.isEmpty(result = JSON.parseArray(distributedCache.get(key, String.class),
                    RegionStationQueryRespDTO.class))) {
                // 加载缓存,并返回结果
                if (CollUtil.isEmpty(result = loadAndSet(key, loader))) {
                    // 还为空即不存在这个数据
                    return Collections.emptyList();
                }
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    private List<RegionStationQueryRespDTO> loadAndSet(final String key, CacheLoader<String> loader) {
        // 获取数据库查询结果
        String result = loader.load();
        // 结果为空或空字符串，返回空列表
        if (CacheUtil.isNullOrBlank(result)) {
            return Collections.emptyList();
        }
        // 结果不为空，加载到缓存
        List<RegionStationQueryRespDTO> respDTOList = JSON.parseArray(result, RegionStationQueryRespDTO.class);
        distributedCache.put(
                key,
                result,
                ADVANCE_TICKET_DAY,
                TimeUnit.DAYS
        );
        return respDTOList;
    }

    @Override
    public List<StationQueryRespDTO> listAllStation() {
        return distributedCache.safeGet(
                STATION_ALL,
                List.class,
                () -> BeanUtil.convert(stationMapper.selectList(Wrappers.emptyWrapper()), StationQueryRespDTO.class),
                ADVANCE_TICKET_DAY,
                TimeUnit.DAYS
        );
    }
}
