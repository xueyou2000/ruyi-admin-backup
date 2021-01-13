package com.xueyou.admin.common.core.enums;

/**
 * 用户状态
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:25 上午
 */
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
