package com.xueyou.admin.system.oss.service;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xueyou.admin.common.core.exception.file.OssException;
import com.xueyou.admin.system.oss.model.CloudStorageConfig;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云存储
 *
 * @author xueyou
 * @date 2020/12/28
 */
public class QiniuCloudStorageService extends CloudStorageService {

    private UploadManager uploadManager;

    private String token;

    public QiniuCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        // 初始化
        init();
    }

    private void init() {
        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).uploadToken(config.getQiniuBucketName());
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
        try {
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            throw new OssException("上传文件失败，请核对七牛配置信息");
        }
        return config.getQiniuDomain() + "/" + path;
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
        return upload(data, getPath(config.getQiniuPrefix(), suffix));
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
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new OssException("上传文件失败");
        }
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
        return upload(inputStream, getPath(config.getQiniuPrefix(), suffix));
    }
}
