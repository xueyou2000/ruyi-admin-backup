package com.xueyou.admin.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.system.domain.Config;
import com.xueyou.admin.system.mapper.ConfigMapper;
import com.xueyou.admin.system.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 系统配置
 *
 * @author xueyou
 * @date 2020/12/25
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Resource
    private ConfigMapper configMapper;


    /**
     * 根据key获取配置
     *
     * @param key key
     */
    @Override
    public Config selectConfigByKey(String key) {
        return lambdaQuery()
            .eq(Config::getConfigKey, key).one();
    }

    /**
     * 根据键保存值
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public boolean updateValueByKey(String key, String value) {
        Config config = selectConfigByKey(key);
        if (config != null) {
            config.setConfigValue(value);
            return updateById(config);
        }
        return false;
    }
}
