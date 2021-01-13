package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.system.domain.OperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志数据访问层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:57 下午
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {

    /**
     * 晴空系统登陆日志
     */
    void cleanOperLog();

}
