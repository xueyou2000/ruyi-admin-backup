package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.xueyou.admin.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 1:07 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dept")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId(type = IdType.AUTO)
    private Long deptId;


    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     * 按照顺序的部门id字符串, 比如 0,100,102, 0=xx科技,100=深圳总公司,102=研发部门, 顺序按照层级关系递进
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 负责人编号
     */
    private Long leaderId;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态 (0正常, 1停用)
     */
    private String status;

    /**
     * 删除标志（0代表存在, 1代表删除）
     */
    @TableLogic
    private String delFlag;

    // ========= 以下为连表查询的字段

    /**
     * 父部门名称
     */
    @TableField(exist = false)
    private String parentName;

}
