package com.xueyou.admin.generator.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.generator.utils.VelocityUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 列信息
 *
 * @author xueyou
 * @date 2020/12/25
 */
@Data
public class TableColumn {

    /**
     * 列字段名称
     */
    private String columnName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 列注释
     */
    private String columnComment;

    /**
     * columnKey
     */
    private String columnKey;

    /**
     * extra
     */
    private String extra;

    /**
     * JAVA类型
     */
    @TableField(exist = false)
    private String javaType;

    /**
     * JAVA字段名
     */
    @TableField(exist = false)
    private String javaField;

    /**
     * 是否主键（1=是）
     */
    private String isPk;

    /**
     * 组件类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件）
     */
    private String reactComponentType;

    /**
     * typescript 类型
     */
    private String tsType;

    /**
     * 是否父类字段
     */
    public boolean isSuperColumn() {
        return isSuperColumn(this.javaField);
    }


    public static boolean isSuperColumn(String javaField) {
        return StringUtils.equalsAnyIgnoreCase(javaField, "createBy", "createTime", "updateBy", "updateTime", "params");
    }

}
