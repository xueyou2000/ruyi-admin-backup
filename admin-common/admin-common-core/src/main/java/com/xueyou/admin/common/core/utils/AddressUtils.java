package com.xueyou.admin.common.core.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
@Slf4j
public class AddressUtils {

    public static final String IP_URL = "http://ip-api.com/json/%s?lang=zh-CN";

    /**
     * 根据ip获取真实地址
     *
     * @param ip    ip
     */
    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }

        String rspStr = HttpUtil.get(String.format(IP_URL, ip));
        if (StringUtils.isBlank(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }

        try {
            JSONObject obj = JSON.parseObject(rspStr);
            address = obj.getString("country") + "," + obj.getString("regionName") + "," + obj.getString("city");
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }

}
