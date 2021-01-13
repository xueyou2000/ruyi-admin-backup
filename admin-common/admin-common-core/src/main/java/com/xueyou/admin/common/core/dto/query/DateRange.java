package com.xueyou.admin.common.core.dto.query;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日期范围查询
 */
@Data
public class DateRange {

    /**
     * 列名称(字段名)
     */
    private String columnsField;

    /**
     * 起始日期
     */
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    private LocalDateTime endDate;

}
