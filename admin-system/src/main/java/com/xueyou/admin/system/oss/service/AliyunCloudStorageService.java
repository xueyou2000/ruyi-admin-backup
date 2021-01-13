package com.xueyou.admin.system.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xueyou.admin.common.core.exception.file.OssException;
import com.xueyou.admin.system.oss.model.CloudStorageConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author xueyou
 * @date 2020/12/28
 */
public class AliyunCloudStorageService extends CloudStorageService {

    private OSS client;

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        // 初始化
        init();
    }

    private void init() {
        client = new OSSClientBuilder().build(
                config.getAliyunEndPoint(),
                config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @return 返回http地址
     */
    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new OssException("上传文件失败，请检查配置信息");
        }
        return config.getAliyunDomain() + "/" + path;
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @return 返回http地址
     */
    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }
}
