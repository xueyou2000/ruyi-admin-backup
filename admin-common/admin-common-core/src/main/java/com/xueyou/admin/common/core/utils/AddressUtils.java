package com.xueyou.admin.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xueyou.admin.common.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
@Slf4j
public class AddressUtils {

    public static final String BAIDU_IP_URL = "https://api.map.baidu.com/location/ip?ak=%s&ip=%s&coor=bd09ll";
    public static final String TAOBAO_IP_URL = "https://ip.taobao.com/outGetIpInfo?ip=%s";

    private static String findIpAddressByTaobao(String ip) {
        String address = "";
        try {
            String rspStr = HttpUtils.requestJson(String.format(TAOBAO_IP_URL, ip),  null,"GET");
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

    private static String findIpAddressByBaidu(String ip) {
        String address = "";
        try {
            String rspStr = HttpUtils.requestJson(String.format(BAIDU_IP_URL, "IvM9nZQf4l16i5sbFLhghGd7yN5SWudM", ip),  null,"GET");
            log.info("ip={} 地理位置={}", ip, rspStr);
            if (StringUtils.isBlank(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }

            JSONObject obj = JSON.parseObject(rspStr);
            if (obj.getInteger("status") != 0) {
                // https://lbsyun.baidu.com/index.php?title=webapi/ip-api 查看状态码
                throw new Exception("状态码异常:" + obj.getInteger("status"));
            }
            JSONObject data = obj.getJSONObject("content");
            address = data.getString("address");
        } catch (Exception e) {
            log.error("获取地理位置异常 ip={}, error={}", ip, e.getMessage());
        }
        return address;
    }

    /**
     * 根据ip获取真实地址
     *
     * @param ip    ip
     */
    public static String getRealAddressByIP(String ip) {
        String cacheAddress = RedisUtils.get("ip-cache:" + ip, String.class);
        if (StringUtils.isNotBlank(cacheAddress)) {
            return cacheAddress;
        }

        String address = "-";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }

        try {
            String result = findIpAddressByTaobao(ip);
            if (StringUtils.isBlank(result)) {
                result = findIpAddressByBaidu(ip);
            }

            if (StringUtils.isNotBlank(result)) {
                address = result;
                RedisUtils.setEx("ip-cache:" + ip, address, 86400);
            }
        } catch (Exception e) {
            log.error("获取地理位置异常 ip={}, error={}", ip, e.getMessage());
        }
        return address;
    }


}
