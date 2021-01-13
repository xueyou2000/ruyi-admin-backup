package com.xueyou.admin.common.core.dto.query;

import lombok.Data;

/**
 * 模糊匹配查询
 */
@Data
public class FuzzyMatch {

    /**
     * 列名称(字段名)
     */
    private String columnsField;

    /**
     * 模糊值
     */
    private String value;

}
