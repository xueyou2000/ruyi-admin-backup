package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.system.domain.Config;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置 数据访问层
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}
