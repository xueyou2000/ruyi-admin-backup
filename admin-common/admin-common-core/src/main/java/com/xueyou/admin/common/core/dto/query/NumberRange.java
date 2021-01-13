package com.xueyou.admin.common.core.dto.query;

import lombok.Data;

/**
 * 数值范围查询
 */
@Data
public class NumberRange {

    /**
     * 列名称(字段名)
     */
    private String columnsField;

    /**
     * 最小值
     */
    private Double min;

    /**
     * 最大值
     */
    private Double max;

}
