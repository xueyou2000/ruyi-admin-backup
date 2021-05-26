package com.xueyou.admin.common.core.enums;

/**
 * 基础枚举
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/25 1:03 下午
 */
public interface BaseEnum {

    /**
     * 获取枚举中文描述
     */
    String getDesc();

    /**
     * 获取枚举国际化key
     */
    String getLangCode();

}
