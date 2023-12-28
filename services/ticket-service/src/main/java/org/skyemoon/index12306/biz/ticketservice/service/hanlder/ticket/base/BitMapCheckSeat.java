package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.base;

import org.skyemoon.index12306.framework.starter.cache.DistributedCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象验证座位实体类
 */
public interface BitMapCheckSeat {

    /**
     * 座位是否存在检查方法
     *
     * @param key               缓存 key
     * @param convert           座位统计 Map
     * @param distributedCache  分布式缓存接口
     * @return 判断座位是否存在 true or false
     */
    boolean checkSeat(String key, HashMap<Integer, Integer> convert, DistributedCache distributedCache);

    /**
     * 检查座位是否存在 v2
     *
     * @param chooseSeatList
     * @param actualSeats
     * @param SEAT_Y_INT
     * @return
     */
    boolean checkChooseSeat(List<String> chooseSeatList, int[][] actualSeats, Map<Character, Integer> SEAT_Y_INT);
}
