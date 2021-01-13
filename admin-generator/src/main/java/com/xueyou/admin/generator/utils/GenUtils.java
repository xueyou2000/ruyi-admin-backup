package com.xueyou.admin.generator.utils;

import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.generator.constant.GenConstants;
import com.xueyou.admin.generator.model.TableColumn;
import com.xueyou.admin.generator.model.TableInfo;

import java.util.Arrays;
import java.util.List;

/**
 * 代码生成工具
 *
 * @author xueyou
 * @date 2020/12/25
 */
public class GenUtils {

    /**
     * 初始化字段额外信息
     */
    public static void initColumnField(List<TableColumn> columns) {
        for (TableColumn column : columns) {
            setColumnField(column);
        }
    }

    /**
     * 设置字段额外属性
     */
    public static void setColumnField(TableColumn column) {
        String dataType = getDbType(column.getDataType());
        String columnName = column.getColumnName();
        // 设置java字段名
        column.setJavaField(StringUtils.toCamelCase(columnName));
        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType)) {
            column.setJavaType("String");
            column.setTsType("string");
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getDataType());
            String htmlType = columnLength >= 500 ? "textarea" : "input";
            column.setReactComponentType(htmlType);
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType("LocalDateTime");
            column.setTsType("string");
            column.setReactComponentType("date");
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            // 如果是浮点型
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getDataType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType("Double");
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setJavaType("Integer");
            }
            // 长整形
            else {
                column.setJavaType("Long");
            }
            column.setTsType("number");
            column.setReactComponentType("input-number");
        } else {
            column.setJavaType("String");
            column.setTsType("string");
            column.setReactComponentType("input");
        }
    }


    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

}
