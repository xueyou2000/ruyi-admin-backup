package com.xueyou.admin.job.service;

import com.xueyou.admin.common.core.exception.job.JobException;
import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.job.domain.Job;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 定时任务 业务层
 */
public interface JobService extends BaseService<Job> {

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
    void run(Job job) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    int pauseJob(Job job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    int resumeJob(Job job) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     */
    int deleteJob(Job job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param jobIds 需要删除的任务ID
     */
    void deleteJobByIds(Long[] jobIds) throws SchedulerException;

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     */
    int changeStatus(Job job) throws SchedulerException;

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     */
    boolean insertJob(Job job) throws SchedulerException, JobException;

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int updateJob(Job job) throws SchedulerException, JobException;

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    boolean checkCronExpressionIsValid(String cronExpression);

    /**
     * 更新实体
     */
    int update(Job job);

    /**
     * 获取所有定时任务
     */
    List<Job> selectJobAll();

}
