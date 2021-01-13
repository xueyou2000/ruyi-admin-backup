package com.xueyou.admin.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 任务日志表
 *
 * @author xueyou
 * @date 2020/12/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_job_log")
public class JobLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long jobLogId;

    /**
     * 任务ID
     */
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 日志信息
     */
    private String jobMessage;

    /**
     * 执行状态 job_run_status（0正常 1失败）
     */
    private String status;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private LocalDateTime startTime;

    /**
     * 停止时间
     */
    @TableField(exist = false)
    private LocalDateTime stopTime;

}
