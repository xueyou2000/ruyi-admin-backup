package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户和角色关联表
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 1:33 下午
 */
@Data
@TableName("sys_user_role")
public class UserRole {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
