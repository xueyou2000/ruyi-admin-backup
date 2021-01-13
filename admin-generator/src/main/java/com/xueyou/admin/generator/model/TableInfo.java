package com.xueyou.admin.generator.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author xueyou
 * @date 2020/12/25
 */
@Data
public class TableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * engine
     */
    private String engine;

    /**
     * 注释
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主键信息
     */
    @TableField(exist = false)
    private TableColumn pk;

}
