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

    public static final String IP_URL = "https://ip.taobao.com/outGetIpInfo?ip=%s";

    /**
     * 根据ip获取真实地址
     *
     * @param ip    ip
     */
    public static String getRealAddressByIP(String ip) {
        String address = "-";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }

        try {
            String rspStr = HttpUtils.requestJson(String.format(IP_URL, ip),  null,"GET");
            log.info("ip={} 地理位置={}", ip, rspStr);
            if (StringUtils.isBlank(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }

            JSONObject obj = JSON.parseObject(rspStr);
            if (obj.getInteger("code") != 0) {
                throw new Exception(obj.getString("msg"));
            }
            JSONObject data = obj.getJSONObject("data");
            address = data.getString("country") + "," + data.getString("region") + "," + data.getString("city") + "," + data.getString("isp");
        } catch (Exception e) {
            log.error("获取地理位置异常 ip={}, error={}", ip, e.getMessage());
        }
        return address;
    }

}
