package com.xueyou.admin.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.job.domain.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 定时任务 数据访问层
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 批量删除定时任务
     *
     * @param ids   任务id
     */
    int deleteJobByIds(Long[] ids);

    /**
     * 查询所有定时任务
     */
    List<Job> selectJobAll();

}
