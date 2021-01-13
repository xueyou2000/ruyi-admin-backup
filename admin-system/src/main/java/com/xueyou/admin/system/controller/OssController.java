package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.exception.file.OssException;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.ValidatorUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.system.domain.Config;
import com.xueyou.admin.system.log.enums.BusinessType;

import com.xueyou.admin.system.domain.Oss;
import com.xueyou.admin.system.model.dto.OssQuery;
import com.xueyou.admin.system.oss.OSSFactory;
import com.xueyou.admin.system.oss.constant.CloudConstant;
import com.xueyou.admin.system.oss.model.AliyunGroup;
import com.xueyou.admin.system.oss.model.CloudStorageConfig;
import com.xueyou.admin.system.oss.model.QcloudGroup;
import com.xueyou.admin.system.oss.model.QiniuGroup;
import com.xueyou.admin.system.oss.service.CloudStorageService;
import com.xueyou.admin.system.service.ConfigService;
import com.xueyou.admin.system.service.OssService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * 文件
 *
 * @author xueyou
 * @date 2020-12-28
 */
@RestController
@RequestMapping("api/system/oss")
@Api(value = "OssController", tags = "文件")
public class OssController {

    @Resource
    private OssService ossService;

    @Resource
    private ConfigService configService;

    /**
     * 查询文件
     */
    @ApiOperation(value = "查询文件",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("system:oss:view")
    public Response<IPage<Oss>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody OssQuery queryDto) {
        IPage<Oss> page = new Page<>(pageNumber, pageSize);
        return Response.ok(ossService.queryByPage(page, queryDto.getOss(), queryDto.getQueryBaseDto()));
    }

    /**
     * 新增文件
     */
    @ApiOperation(value = "新增文件",  httpMethod = "POST")
    @HasPermissions("system:oss:add")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody Oss oss) {
        return Response.ok(ossService.save(oss));
    }

    /**
     * 修改文件
     */
    @ApiOperation(value = "修改文件",  httpMethod = "POST")
    @HasPermissions("system:oss:update")
    @PostMapping("update")
    public Response<Boolean> update(@RequestBody Oss oss) {
        return Response.ok(ossService.updateById(oss));
    }

    /**
     * 删除文件
     */
    @ApiOperation(value = "删除文件",  httpMethod = "POST")
    @HasPermissions("system:oss:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(ossService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 云存储配置信息
     */
    @ApiOperation(value = "云存储配置信息",  httpMethod = "POST")
    @RequestMapping("getCloudStorageConfig")
    @HasPermissions("system:oss:config")
    public Response<CloudStorageConfig> config() {
        Config cloudStorageConfig = configService.selectConfigByKey(CloudConstant.CLOUD_STORAGE_CONFIG_KEY);
        return Response.ok(OSSFactory.getCloudStorageConfig(cloudStorageConfig));
    }

    /**
     * 更新云存储配置
     */
    @ApiOperation(value = "更新云存储配置",  httpMethod = "POST")
    @RequestMapping("updateCloudStorageConfig")
    @HasPermissions("system:oss:config")
    public Response<Boolean> updateCloudStorageConfig(@RequestBody CloudStorageConfig config) {
        // 校验类型
        ValidatorUtils.validateEntity(config);
        if (config.getType() == CloudConstant.CloudService.QINIU.getValue()) {
            // 校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == CloudConstant.CloudService.ALIYUN.getValue()) {
            // 校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == CloudConstant.CloudService.QCLOUD.getValue()) {
            // 校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }
        return Response.ok(configService.updateValueByKey(CloudConstant.CLOUD_STORAGE_CONFIG_KEY, JSON.toJSONString(config)));
    }

    /**
     * 上传文件
     */
    @ApiOperation(value = "上传文件",  httpMethod = "POST")
    @RequestMapping("upload")
    @HasPermissions("system:oss:add")
    public Response<Boolean> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new OssException("上传文件不能为空");
        }
        String loginName = ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
        // 上传文件
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        CloudStorageService storage = OSSFactory.build();
        String url = storage.uploadSuffix(file.getBytes(), suffix);
        // 保存文件信息
        Oss ossEntity = new Oss();
        ossEntity.setUrl(url);
        ossEntity.setFileSuffix(suffix);
        ossEntity.setCreateBy(loginName);
        ossEntity.setFileName(fileName);
        ossEntity.setCreateTime(LocalDateTime.now());
        ossEntity.setService((long) storage.getService());
        return Response.ok(ossService.save(ossEntity));
    }

}