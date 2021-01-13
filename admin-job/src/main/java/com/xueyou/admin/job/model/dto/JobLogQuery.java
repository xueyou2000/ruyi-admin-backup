package com.xueyou.admin.job.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.job.domain.JobLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 定时任务日志查询
 *
 * @author xueyou
 * @date 2020/12/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "JobLogQuery", description = "定时任务日志查询")
public class JobLogQuery extends BaseQueryDto implements Serializable {

    /**
     * 定时任务日志
     */
    @ApiModelProperty(value = "定时任务日志")
    private JobLog jobLog = new JobLog();

}
