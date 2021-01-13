package com.xueyou.admin.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 定时任务调度
 *
 * @author xueyou
 * @date 2020/12/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_job")
public class Job extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    private Long jobId;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    @Size(max = 64, message = "任务名称不能超过64个字符")
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @NotBlank(message = "调用目标字符串不能为空")
    @Size(max = 500, message = "调用目标字符串长度不能超过500个字符")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(max = 255, message = "Cron执行表达式不能超过255个字符")
    private String cronExpression;

    /**
     * 计划执行错误策略
     * job_misfire_polic  1立即执行 2执行一次 3放弃执行
     */
    private String misfirePolicy = "3";

    /**
     * 是否并发执行 job_concurrent（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 任务状态 job_status（0正常 1暂停）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
