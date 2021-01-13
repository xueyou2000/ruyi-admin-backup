package com.xueyou.admin.common.core.dto.query;

import com.xueyou.admin.common.core.enums.SortDirection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询对象
 */
@Data
public class QueryBaseDto {

    /**
     * 排序方向
     */
    private SortDirection direction = SortDirection.DESC;

    /**
     * 排序字段
     */
    private List<String> sortNames = new ArrayList<>();

    /**
     * 日期范围查询
     */
    private List<DateRange> dateRanges;

    /**
     * 数值范围查询
     */
    private List<NumberRange> numberRanges;

    /**
     * 模糊查询
     */
    private List<FuzzyMatch> fuzzyMatches;

    /**
     * 多值搜索
     */
    private List<MultiMatch> multiValues;

}
