package com.xueyou.admin.job.core;

import com.xueyou.admin.job.domain.Job;
import com.xueyou.admin.job.utils.JobInvokeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author xueyou
 * @date 2020/12/24
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    @Override
    protected void doExecute(JobExecutionContext context, Job sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
