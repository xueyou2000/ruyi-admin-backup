package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.system.domain.Config;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.model.dto.ConfigQuery;
import com.xueyou.admin.system.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 系统配置
 *
 * @author xueyou
 * @date 2020/12/25
 */
@RestController
@RequestMapping("/system/config")
@Api(value = "ConfigController", tags = "系统配置")
public class ConfigController {

    @Resource
    private ConfigService configService;

    /**
     * 查询系统配置
     */
    @ApiOperation(value = "查询系统配置",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("system:config:view")
    public Response<IPage<Config>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody ConfigQuery queryDto) {
        IPage<Config> page = new Page<>(pageNumber, pageSize);
        return Response.ok(configService.queryByPage(page, queryDto.getConfig(), queryDto.getQueryBaseDto()));
    }

    /**
     * 新增系统配置
     */
    @ApiOperation(value = "新增系统配置",  httpMethod = "POST")
    @OperLog(title = "系统配置", businessType = BusinessType.INSERT)
    @HasPermissions("system:config:add")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody Config config) {
        return Response.ok(configService.save(config));
    }

    /**
     * 修改系统配置
     */
    @ApiOperation(value = "修改系统配置",  httpMethod = "POST")
    @OperLog(title = "系统配置", businessType = BusinessType.UPDATE)
    @HasPermissions("system:config:update")
    @PostMapping("update")
    public Response<Boolean> update(@RequestBody Config config) {
        return Response.ok(configService.updateById(config));
    }

    /**
     * 删除系统配置
     */
    @ApiOperation(value = "删除系统配置",  httpMethod = "POST")
    @OperLog(title = "系统配置", businessType = BusinessType.DELETE)
    @HasPermissions("system:config:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(configService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 获取配置
     */
    @ApiOperation(value = "获取配置",  httpMethod = "POST")
    @HasPermissions("system:config:list")
    @PostMapping("findConfigByKey")
    public Response<String> findConfigByKey(@RequestParam String key) {
        Config config = configService.selectConfigByKey(key);
        return Response.ok(config != null ? config.getConfigValue() : "");
    }

}
