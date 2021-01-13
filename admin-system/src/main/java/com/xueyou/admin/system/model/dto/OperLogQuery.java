package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.OperLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 操作日志查询
 *
 * @author xueyou
 * @date 2020/12/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OperLogQuery", description = "操作日志查询")
public class OperLogQuery extends BaseQueryDto implements Serializable {

    /**
     * 操作日志
     */
    @ApiModelProperty(value = "操作日志")
    private OperLog operLog = new OperLog();

}
