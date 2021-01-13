package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.Config;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统配置查询
 *
 * @author xueyou
 * @date 2020/12/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ConfigQuery", description = "系统配置查询")
public class ConfigQuery extends BaseQueryDto implements Serializable {

    /**
     * 系统配置
     */
    @ApiModelProperty(value = "系统配置")
    private Config config;

}
