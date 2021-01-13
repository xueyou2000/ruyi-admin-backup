package com.xueyou.admin.common.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CodeBuilder(编码生成)
 *
 * @author 央联支付有限公司
 * @version V1.0
 * @since 2016年8月5日
 */
public class CodeBuilder {
    /**初始值*/
    private static final Integer MIN_VAR = 10000000;
    /**初始值*/
    private static final Integer MAX_VAR = 100000000;

    private static AtomicInteger atomicInteger = new AtomicInteger(MIN_VAR);

    public synchronized static Integer getAtomicInteger() {
        if (atomicInteger.compareAndSet(MAX_VAR, MIN_VAR)) {
            return atomicInteger.get();
        } else {
            return atomicInteger.getAndIncrement();
        }
    }

    /**
     * 生成指定长度的编号
     * @param nums 长度
     * @return 生成编号
     */
    public static String build(int nums) {
        if (nums > 0) {
            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nums; i++) {
                int number = random.nextInt(62);
                sb.append(str.charAt(number));
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 生成纯数字编号
     * @param nums 长度
     * @return 生成编号
     */
    public static String buildNumber(int nums) {
        if (nums > 0) {
            String str = "0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nums; i++) {
                int number = random.nextInt(10);
                sb.append(str.charAt(number));
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 生成短信验证码
     * @return 验证码
     */
    public static String buildVerifyCode() {
        return  buildNumber(6);
    }

    /**
     * 生成指定前缀的时间戳编号
     * @param start 前缀字符串
     * @param dateFormat 日期格式
     * @param nums 长度
     * @return 生成编号
     */
    public static String build(String start, String dateFormat, int nums) {
        if (StringUtils.isBlank(dateFormat)) {
            return start + getOrderIdByUUId();
        }
        return start + (new SimpleDateFormat(dateFormat).format(new Date())) + getOrderIdByUUId();
    }

    /**
     * 生成指定前缀的编号
     * @param start 前缀字符串
     * @return 生成编号
     */
    public static String build(String start) {
        return start + getOrderIdByUUId();
    }

    /**
     * 生成指定前缀的时间戳编号加UUID
     * @param start 前缀字符串
     * @param dateFormat 日期格式
     * @return 生成编号
     */
    public static String build(String start, String dateFormat) {
        if (StringUtils.isBlank(dateFormat)) {
            return start + getOrderIdByUUId();
        }
        return start + (new SimpleDateFormat(dateFormat).format(new Date())) + getOrderIdByUUId();
    }

    /**
     * 生成日期格式+UUID
     * @param dateFormat 日期格式
     * @param nums 长度
     * @return 生成编号
     */
    public static String build(String dateFormat, int nums) {
        if (StringUtils.isBlank(dateFormat)) {
            return (getOrderIdByUUId());
        }
        return new SimpleDateFormat(dateFormat).format(new Date()) + getOrderIdByUUId();
    }

    /**
     * 生成 UUID
     * @return uuid
     */
    public static String getOrderIdByUUId() {
        int machineId = 1;
        //最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%011d", hashCodeV);
    }

    /**
     * 生成UUID编号
     * @return uuid编号
     */
    public static String build() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 生成效验码
     * @return 效验码
     */
    public static char[] generateCheckCode() {
        String chars = "0123456789qwertyuiopasdfghjklzxcvbnm";
        char[] rands = new char[4];
        for (int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * 36);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }

}
