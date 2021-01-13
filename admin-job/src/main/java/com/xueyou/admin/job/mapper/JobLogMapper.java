package com.xueyou.admin.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.job.domain.JobLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志 数据访问层
 */
@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {

    /**
     * 根据id删除任务日志
     */
    int deleteJobLogById(Long id);

    /**
     * 批量删除定时任务日志
     *
     * @param ids   id
     */
    int deleteJobLogByIds(Long[] ids);

    /**
     * 清空日志
     */
    void cleanJobLog();

}
