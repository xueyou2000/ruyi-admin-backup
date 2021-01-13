package com.xueyou.admin.job.service.impl;

import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.job.domain.JobLog;
import com.xueyou.admin.job.mapper.JobLogMapper;
import com.xueyou.admin.job.service.JobLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 定时任务日志 业务层处理层
 *
 * @author xueyou
 * @date 2020/12/24
 */
@Service
public class JobLogServiceImpl extends BaseServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    @Resource
    private JobLogMapper jobLogMapper;

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的日志ID
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds) {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     * @return 结果
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }
}
