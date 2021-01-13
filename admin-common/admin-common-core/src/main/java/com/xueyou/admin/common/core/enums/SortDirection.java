package com.xueyou.admin.common.core.enums;

/**
 * 排序方向
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/28 11:09 上午
 */
public enum SortDirection implements BaseEnum {

    DESC("降序"),
    ASC("升序");

    SortDirection(String desc) {
        this.desc = desc;
    }

    /** 枚举描述 */
    private String desc;

    /** 获取枚举中文描述 */
    @Override
    public String getDesc() {
        return desc;
    }

}
