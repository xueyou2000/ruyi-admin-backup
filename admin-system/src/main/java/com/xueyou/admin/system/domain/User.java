package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.xueyou.admin.common.core.entity.BaseEntity;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 12:48 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户编号
     * 预留业务字段, 后期根据需求扩展, 比如是商户类型: C10001
     */
    private String userCode;

    /**
     * 用户类型
     * 预留字段, 后期根据需求扩展, 比如是商户类型
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（0女性 1男性）
     */
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private LocalDateTime loginDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 超级管理员
     */
    private TrueOrFalse admin = TrueOrFalse.FALSE;

    // ========= 以下为连表查询的字段

    /**
     * 部门对象
     */
    @TableField(exist = false)
    private Dept dept;

    /**
     * 角色信息
     */
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 权限信息
     */
    @TableField(exist = false)
    private Set<String> buttons;

    // ========= 以下为临时字段

    /**
     * 登陆用户代理字符串
     */
    @TableField(exist = false)
    private String userAgent;

    /**
     * 登陆地址
     */
    @TableField(exist = false)
    private String loginLocation;

}
