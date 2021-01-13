package com.xueyou.admin.system.service;

import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.OperLog;

/**
 * 操作日志业务层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:55 下午
 */
public interface OperLogService extends BaseService<OperLog> {

    /**
     * 晴空系统登陆日志
     */
    void cleanOperLog();

}
