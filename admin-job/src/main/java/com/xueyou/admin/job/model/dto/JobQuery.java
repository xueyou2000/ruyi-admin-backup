package com.xueyou.admin.job.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.job.domain.Job;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 定时任务查询
 *
 * @author xueyou
 * @date 2020/12/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "JobQuery", description = "定时任务查询")
public class JobQuery extends BaseQueryDto implements Serializable {

    /**
     * 定时任务
     */
    @ApiModelProperty(value = "定时任务")
    private Job job = new Job();

}
