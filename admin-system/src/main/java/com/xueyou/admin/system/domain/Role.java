package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.xueyou.admin.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 1:23 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色排序
     */
    private String roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定数据权限）
     */
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private String delFlag;

    // ========= 以下为连表查询的字段

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    @TableField(exist = false)
    private boolean flag = false;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 部门组（数据权限）
     */
    @TableField(exist = false)
    private Long[] deptIds;

}
