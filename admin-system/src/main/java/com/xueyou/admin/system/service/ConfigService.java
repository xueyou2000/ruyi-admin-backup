package com.xueyou.admin.system.service;

import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.Config;

import java.util.List;

/**
 * 系统配置 业务层
 */
public interface ConfigService extends BaseService<Config> {

    /**
     * 获取配置根据key
     *
     * @param key   key
     */
    Config selectConfigByKey(String key);

    /**
     * 根据键保存值
     *
     * @param key   键
     * @param value 值
     */
    boolean updateValueByKey(String key, String value);

}
