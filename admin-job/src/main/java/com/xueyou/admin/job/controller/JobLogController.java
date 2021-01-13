package com.xueyou.admin.job.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.job.domain.JobLog;
import com.xueyou.admin.job.model.dto.JobLogQuery;
import com.xueyou.admin.job.service.JobLogService;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 定时任务日志 控制器
 *
 * @author xueyou
 * @date 2020/12/24
 */
@RestController
@RequestMapping("api/monitor/jobLog")
@Api(value = "JobLogController", tags = "定时任务日志")
public class JobLogController {

    @Resource
    private JobLogService jobLogService;

    /**
     * 查询定时任务日志
     */
    @HasPermissions("monitor:job:view")
    @ApiOperation(value = "查询定时任务日志",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    public Response<IPage<JobLog>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody JobLogQuery queryDto) {
        IPage<JobLog> page = new Page<>(pageNumber, pageSize);
        return Response.ok(jobLogService.queryByPage(page, queryDto.getJobLog(), queryDto.getQueryBaseDto()));
    }

    /**
     * 删除定时任务日志
     */
    @ApiOperation(value = "删除定时任务日志",  httpMethod = "POST")
    @HasPermissions("monitor:job:remove")
    @PostMapping("remove")
    public Response<Integer> remove(@RequestParam String ids) {
        return Response.ok(jobLogService.deleteJobLogByIds(Convert.toLongArray(ids)));
    }

    /**
     * 清空定时任务日志
     */
    @ApiOperation(value = "清空定时任务日志",  httpMethod = "POST")
    @HasPermissions("monitor:job:remove")
    @PostMapping("clean")
    public Response<Boolean> clean() {
        jobLogService.cleanJobLog();
        return Response.ok(true);
    }

}
