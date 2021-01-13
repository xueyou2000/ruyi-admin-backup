package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 角色查询
 *
 * @author xueyou
 * @date 2020-12-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "RoleQuery", description = "角色查询")
public class RoleQuery extends BaseQueryDto implements Serializable {

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private Role role;

}