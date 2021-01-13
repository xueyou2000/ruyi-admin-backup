package com.xueyou.admin.common.core.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyou.admin.common.core.dto.query.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 查询工具
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/28 11:19 上午
 */
@Slf4j
public class QueryUtils {

    /**
     * 动态拼接条件
     *
     * @param data  查询实体信息
     * @param queryBaseDto  查询高级信息
     */
    public static <T> QueryWrapper<T> createPredicate(T data, QueryBaseDto queryBaseDto) {
        return createPredicate(new QueryWrapper<>(), data, queryBaseDto);
    }

    /**
     * 动态拼接条件
     *
     * @param data  查询实体信息
     * @param queryBaseDto  查询高级信息
     */
    public static <T> QueryWrapper<T> createPredicate(QueryWrapper<T> queryWrapper, T data, QueryBaseDto queryBaseDto) {
        // 值全等匹配查询
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 排除静态字段
            if ((field.getModifiers() & 0x8) != 0) {
                continue;
            }
            // 设置字段可见
            field.setAccessible(true);
            try {
                TableField tableField = field.getAnnotation(TableField.class);
                Object val = field.get(data);
                if (val == null || org.springframework.util.StringUtils.isEmpty(val) || (tableField != null && !tableField.exist()) || StringUtils.equalsAnyIgnoreCase(field.getName(), "params", "version")) {
                    continue;
                }
                queryWrapper = queryWrapper.eq(StringUtils.camelToUnderline(field.getName()),val);
            } catch (IllegalAccessException e) {
                log.error("createPredicate获取字段值异常, 数据=[{}], 字段=[{}]", data, field.getName());
            }
        }

        // 日期范围查询
        if (queryBaseDto != null && queryBaseDto.getDateRanges() != null) {
            List<DateRange> dateRanges = queryBaseDto.getDateRanges();
            for (DateRange dateRange : dateRanges) {
                String columnsField = StringUtils.camelToUnderline(dateRange.getColumnsField());
                LocalDateTime startDate = dateRange.getStartDate();
                LocalDateTime endDate = dateRange.getEndDate();

                if (!StringUtils.isBlank(columnsField)) {
                    queryWrapper = queryWrapper.between(columnsField, startDate, endDate);
                }
            }
        }

        // 数值范围查询
        if (queryBaseDto != null && queryBaseDto.getNumberRanges() != null) {
            List<NumberRange> numberRanges = queryBaseDto.getNumberRanges();
            for (NumberRange numberRange : numberRanges) {
                String columnsField = StringUtils.camelToUnderline(numberRange.getColumnsField());
                Double min = numberRange.getMin();
                Double max = numberRange.getMax();

                if (min == null) {
                    min = 0D;
                }
                if (max == null) {
                    max = 9999999D;
                }

                if (!StringUtils.isBlank(columnsField)) {
                    queryWrapper = queryWrapper.between(columnsField, min, max);
                }
            }
        }

        // 模糊查询
        if (queryBaseDto != null && queryBaseDto.getFuzzyMatches() != null) {
            List<FuzzyMatch> fuzzyMatches = queryBaseDto.getFuzzyMatches();
            for (FuzzyMatch fuzzyMatch : fuzzyMatches) {
                String columnsField = StringUtils.camelToUnderline(fuzzyMatch.getColumnsField());
                String value = fuzzyMatch.getValue();
                if (!StringUtils.isBlank(columnsField) && !StringUtils.isBlank(value)) {
                    queryWrapper = queryWrapper.like(columnsField, value);
                }
            }
        }

        // 多值查询
        if (queryBaseDto != null && queryBaseDto.getMultiValues() != null) {
            List<MultiMatch> multivalues = queryBaseDto.getMultiValues();
            for (MultiMatch multiMatch : multivalues) {
                String columnsField = StringUtils.camelToUnderline(multiMatch.getColumnsField());
                List<String> values = multiMatch.getValue();
                queryWrapper = queryWrapper.in(columnsField, values);
            }

        }

        return queryWrapper;
    }


}
