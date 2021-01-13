package com.xueyou.admin.common.core.dto.query;

import lombok.Data;

import java.util.List;

/**
 * 多值匹配查询
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/5/14 3:12 下午
 */
@Data
public class MultiMatch {

    /**
     * 列名称(字段名)
     */
    private String columnsField;

    /**
     * 搜索值
     */
    private List<String> value;

}
