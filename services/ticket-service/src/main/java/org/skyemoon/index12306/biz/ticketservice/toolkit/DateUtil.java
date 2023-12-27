package org.skyemoon.index12306.biz.ticketservice.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 */
@Slf4j
public final class DateUtil {

    /**
     * 计算小时差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 12:23
     */
    public static String calculateHourDifference(Date startTime, Date endTime) {
        LocalDateTime startDateTime = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(startDateTime, endDateTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * 日期转换为列车行驶开始时间和结束时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String convertDateToLocalTime(Date date, String pattern) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(outputFormatter);
    }
}
