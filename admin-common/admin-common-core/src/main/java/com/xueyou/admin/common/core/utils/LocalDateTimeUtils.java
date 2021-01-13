package com.xueyou.admin.common.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author xueyou
 * @date 2020/12/24
 */
public class LocalDateTimeUtils {

    public static String Zone = "+8";

    /**
     * 获取时间戳
     */
    public static int getTime(LocalDateTime localDateTime) {
        return (int) localDateTime.toEpochSecond(ZoneOffset.of(Zone));
    }

}
