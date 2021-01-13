package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色和部门关联表
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 1:28 下午
 */
@Data
@TableName("sys_role_dept")
public class RoleDept {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;

}
