package com.xueyou.admin.job.service.impl;

import com.xueyou.admin.common.core.constant.ScheduleConstants;
import com.xueyou.admin.common.core.exception.job.JobException;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.job.domain.Job;
import com.xueyou.admin.job.mapper.JobMapper;
import com.xueyou.admin.job.service.JobService;
import com.xueyou.admin.job.utils.ScheduleUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务 业务处理层
 *
 * @author xueyou
 * @date 2020/12/24
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<JobMapper, Job> implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 初始化定时器
     * 防止手动修改数据库导致未同步的任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<Job> jobList = jobMapper.selectJobAll();
        for (Job job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
    @Override
    public void run(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        Job properties = getById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    public int pauseJob(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.STATUS_PAUSE);
        int rows = update(job);
        if (rows > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    public int resumeJob(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.STATUS_NORMAL);
        int rows = this.update(job);
        if (rows > 0) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     */
    @Override
    public int deleteJob(Job job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteById(jobId);
        if (rows > 0) {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     *
     * @param jobIds 需要删除的任务ID
     */
    @Override
    public void deleteJobByIds(Long[] jobIds) throws SchedulerException {
        for (Long jobId : jobIds) {
            Job job = getById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     */
    @Override
    public int changeStatus(Job job) throws SchedulerException {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.STATUS_NORMAL.equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.STATUS_PAUSE.equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     */
    @Override
    public boolean insertJob(Job job) throws SchedulerException, JobException {
        job.setStatus(ScheduleConstants.STATUS_PAUSE);
        boolean result = save(job);

        if (result) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return result;
    }

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     */
    @Override
    public int updateJob(Job job) throws SchedulerException, JobException {
        Job properties = getById(job.getJobId());
        int rows = update(job);
        if (rows > 0) {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 更新实体
     */
    @Override
    public int update(Job job) {
        job.setUpdateTime(LocalDateTime.now());
        return jobMapper.updateById(job);
    }

    /**
     * 获取所有定时任务
     */
    @Override
    public List<Job> selectJobAll() {
        return jobMapper.selectJobAll();
    }

    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(Job job, String jobGroup) throws SchedulerException {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }
}
