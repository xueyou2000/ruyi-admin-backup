package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.utils.ExcelUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.system.domain.OperLog;
import com.xueyou.admin.system.model.dto.OperLogQuery;
import com.xueyou.admin.system.service.DictDataService;
import com.xueyou.admin.system.service.OperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 操作日志 控制器
 *
 * @author xueyou
 * @date 2020/12/23
 */
@RestController
@RequestMapping("api/monitor/operLog")
@Api(value = "OperLogController", tags = "操作日志")
public class OperLogController {

    @Resource
    private OperLogService operLogService;

    @Resource
    private DictDataService dictDataService;

    /**
     * 查询登陆日志
     */
    @ApiOperation(value = "查询登陆日志",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("monitor:operlog:view")
    public Response<IPage<OperLog>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody OperLogQuery queryDto) {
        IPage<OperLog> page = new Page<>(pageNumber, pageSize);
        return Response.ok(operLogService.queryByPage(page, queryDto.getOperLog(), queryDto.getQueryBaseDto()));
    }

    /**
     * 删除登陆日志
     */
    @ApiOperation(value = "删除登陆日志",  httpMethod = "POST")
    @HasPermissions("monitor:operlog:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(operLogService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 清空登陆日志
     */
    @ApiOperation(value = "清空登陆日志",  httpMethod = "POST")
    @HasPermissions("monitor:operlog:remove")
    @PostMapping("clean")
    public Response<Boolean> clean() {
        operLogService.cleanOperLog();
        return Response.ok(true);
    }

    /**
     * 导出操作日志
     */
    @ApiOperation(value = "导出操作日志",  httpMethod = "POST")
    @PostMapping("export")
    @HasPermissions("monitor:operlog:export")
    public Response<String> export(@RequestBody OperLogQuery queryDto) {
        List<OperLog> data = operLogService.queryAll(queryDto.getOperLog(), queryDto.getQueryBaseDto());
        ExcelUtils<OperLog> util = new ExcelUtils<>(OperLog.class);
        return Response.ok(util.exportExcel(data, "操作日志", dictDataService.findDictByTypes(util.getDictTypes())));

    }

}
