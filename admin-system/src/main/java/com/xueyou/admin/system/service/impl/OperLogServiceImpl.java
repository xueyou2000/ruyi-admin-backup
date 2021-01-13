package com.xueyou.admin.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.system.domain.OperLog;
import com.xueyou.admin.system.mapper.OperLogMapper;
import com.xueyou.admin.system.service.OperLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 操作日志业务层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:59 下午
 */
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements OperLogService {

    @Resource
    private OperLogMapper operLogMapper;

    /**
     * 晴空系统登陆日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }

}
