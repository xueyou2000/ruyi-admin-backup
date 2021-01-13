package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 1:15 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单URL
     * 同级唯一, 前端路由
     */
    private String menuKey;

    /**
     * 组件
     * views文件夹下的路径
     */
    private String component;

    /**
     * 打开方式 (_blank新窗口)
     */
    private String target;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 类型: (M目录,C菜单,F按钮)
     */
    private String menuType;


    /**
     * 菜单状态:0显示,1隐藏
     */
    private String visible;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 链接地址
     * 打开方式_blank时候, 将打开此地址
     */
    private String path;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 隐藏子菜单 TRUE=隐藏, FALSE=不隐藏
     */
    private TrueOrFalse hiddenChildren;

    /**
     * 备注
     */
    private String remark;

    // ========= 以下为连表查询的字段

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();

}
