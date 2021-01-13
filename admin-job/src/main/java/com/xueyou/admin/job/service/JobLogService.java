package com.xueyou.admin.job.service;

import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.job.domain.JobLog;

/**
 * 定时任务日志 业务层
 */
public interface JobLogService extends BaseService<JobLog> {

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的日志ID
     */
    int deleteJobLogByIds(Long[] logIds);

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     * @return 结果
     */
    int deleteJobLogById(Long jobId);

    /**
     * 清空任务日志
     */
    void cleanJobLog();

}
