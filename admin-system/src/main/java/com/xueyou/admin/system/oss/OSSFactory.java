package com.xueyou.admin.system.oss;

import com.alibaba.fastjson.JSON;
import com.xueyou.admin.common.core.exception.file.OssException;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.spring.SpringUtils;
import com.xueyou.admin.system.domain.Config;
import com.xueyou.admin.system.oss.constant.CloudConstant;
import com.xueyou.admin.system.oss.model.CloudStorageConfig;
import com.xueyou.admin.system.oss.service.AliyunCloudStorageService;
import com.xueyou.admin.system.oss.service.CloudStorageService;
import com.xueyou.admin.system.oss.service.QcloudCloudStorageService;
import com.xueyou.admin.system.oss.service.QiniuCloudStorageService;
import com.xueyou.admin.system.service.ConfigService;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传Factory
 *
 * @author xueyou
 * @date 2020/12/28
 */
@Slf4j
public class OSSFactory {

    private static ConfigService configService;

    static {
        OSSFactory.configService = SpringUtils.getBean(ConfigService.class);
    }

    public static CloudStorageService build() {
        Config cloudStorageConfig = configService.selectConfigByKey(CloudConstant.CLOUD_STORAGE_CONFIG_KEY);
        // 获取云存储配置信息
        CloudStorageConfig config = getCloudStorageConfig(cloudStorageConfig);
        if (config.getType() == CloudConstant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == CloudConstant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == CloudConstant.CloudService.QCLOUD.getValue()) {
            return new QcloudCloudStorageService(config);
        }
        throw new OssException("不支持的云存储类型!");
    }

    public static CloudStorageConfig getCloudStorageConfig(Config cloudStorageConfig) {
        if (cloudStorageConfig == null || StringUtils.isBlank(cloudStorageConfig.getConfigValue())) {
            throw new OssException("未找到云存储配置!");
        }
        String jsonConfig = cloudStorageConfig.getConfigValue();
        // 获取云存储配置信息
        return JSON.parseObject(jsonConfig, CloudStorageConfig.class);
    }

}
