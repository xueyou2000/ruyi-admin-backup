package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.domain.LoginInfo;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.model.dto.LoginInfoQuery;
import com.xueyou.admin.system.service.LoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 登陆日志 控制器
 *
 * @author xueyou
 * @date 2020/12/23
 */
@RestController
@RequestMapping("/monitor/loginInfo")
@Api(value = "LoginInfoController", tags = "登陆日志")
public class LoginInfoController {

    @Resource
    private LoginInfoService loginInfoService;

    /**
     * 查询登陆日志
     */
    @ApiOperation(value = "查询登陆日志",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("monitor:loginlog:view")
    public Response<IPage<LoginInfo>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody LoginInfoQuery queryDto) {
        IPage<LoginInfo> page = new Page<>(pageNumber, pageSize);
        return Response.ok(loginInfoService.queryByPage(page, queryDto.getLoginInfo(), queryDto.getQueryBaseDto()));
    }

    /**
     * 删除登陆日志
     */
    @ApiOperation(value = "删除登陆日志",  httpMethod = "POST")
    @OperLog(title = "登陆日志", businessType = BusinessType.DELETE)
    @HasPermissions("monitor:loginlog:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(loginInfoService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 清空登陆日志
     */
    @ApiOperation(value = "清空登陆日志",  httpMethod = "POST")
    @OperLog(title = "登陆日志", businessType = BusinessType.CLEAN)
    @HasPermissions("monitor:loginlog:remove")
    @PostMapping("clean")
    public Response<Boolean> clean() {
        loginInfoService.cleanLoginInfo();
        return Response.ok(true);
    }

}
