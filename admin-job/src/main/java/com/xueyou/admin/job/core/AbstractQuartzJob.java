package com.xueyou.admin.job.core;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.xueyou.admin.common.core.constant.Constants;
import com.xueyou.admin.common.core.constant.ScheduleConstants;
import com.xueyou.admin.common.core.utils.LocalDateTimeUtils;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.utils.spring.SpringUtils;
import com.xueyou.admin.job.domain.Job;
import com.xueyou.admin.job.domain.JobLog;
import com.xueyou.admin.job.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author xueyou
 * @date 2020/12/24
 */
@Slf4j
public abstract class AbstractQuartzJob implements org.quartz.Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        Job sysJob = new Job();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
        try {
            before(context, sysJob);
            doExecute(context, sysJob);
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("定时任务执行异常 ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, Job sysJob) {
        threadLocal.set(LocalDateTime.now());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void after(JobExecutionContext context, Job sysJob, Exception e) {
        LocalDateTime startTime = threadLocal.get();
        threadLocal.remove();

        final JobLog jobLog = new JobLog();
        jobLog.setJobName(sysJob.getJobName());
        jobLog.setJobId(sysJob.getJobId());
        jobLog.setJobGroup(sysJob.getJobGroup());
        jobLog.setInvokeTarget(sysJob.getInvokeTarget());
        jobLog.setStartTime(startTime);
        jobLog.setStopTime(LocalDateTime.now());
        jobLog.setCreateTime(startTime);

        long runMs = LocalDateTimeUtils.getTime(jobLog.getStopTime()) - LocalDateTimeUtils.getTime(jobLog.getStartTime());
        jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLog.setStatus(Constants.FAIL);
            String errorMsg = StringUtils.substring(ExceptionUtil.getMessage(e), 0, 2000);
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus(Constants.SUCCESS);
        }

        // 写入数据库当中
        SpringUtils.getBean(JobLogService.class).save(jobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, Job sysJob) throws Exception;

}
