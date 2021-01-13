package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色和菜单关联表
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 1:29 下午
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}
