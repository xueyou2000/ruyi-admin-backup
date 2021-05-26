package com.xueyou.admin.common.core.enums;

import lombok.Getter;

/**
 * 布尔枚举
 */
@Getter
public enum TrueOrFalse implements BaseEnum {
    TRUE("真"),
    FALSE("假");

    TrueOrFalse(String desc) {
        this.desc = desc;
    }

    /** 枚举描述 */
    private final String desc;

    /** 获取枚举中文描述 */
    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getLangCode() {
        return this.getClass().getName() + "." + name();
    }

}
