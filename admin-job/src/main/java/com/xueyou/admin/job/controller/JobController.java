package com.xueyou.admin.job.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.job.domain.Job;
import com.xueyou.admin.job.model.dto.JobQuery;
import com.xueyou.admin.job.service.JobService;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务 控制器
 *
 * @author xueyou
 * @date 2020/12/24
 */
@RestController
@RequestMapping("/monitor/job")
@Api(value = "JobController", tags = "定时任务")
public class JobController {

    @Resource
    private JobService jobService;

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
    public Response<IPage<Job>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody JobQuery queryDto) {
        IPage<Job> page = new Page<>(pageNumber, pageSize);
        return Response.ok(jobService.queryByPage(page, queryDto.getJob(), queryDto.getQueryBaseDto()));
    }

    /**
     * 删除定时任务
     */
    @ApiOperation(value = "删除定时任务",  httpMethod = "POST")
    @HasPermissions("monitor:job:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) throws SchedulerException {
        jobService.deleteJobByIds(Convert.toLongArray(ids));
        return Response.ok(true);
    }

    /**
     * 新增定时任务
     */
    @ApiOperation(value = "新增定时任务",  httpMethod = "POST")
    @OperLog(title = "定时任务", businessType = BusinessType.INSERT)
    @HasPermissions("monitor:job:add")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody Job job) throws SchedulerException {
        if (!jobService.checkCronExpressionIsValid(job.getCronExpression())) {
            return Response.error(402, "cron表达式不正确");
        }
        job.setCreateBy(UserUtils.getUserName());
        return Response.ok(jobService.insertJob(job));
    }

    /**
     * 修改定时任务
     */
    @ApiOperation(value = "修改定时任务",  httpMethod = "POST")
    @OperLog(title = "定时任务", businessType = BusinessType.UPDATE)
    @HasPermissions("monitor:job:update")
    @PostMapping("update")
    public Response<Integer> update(@RequestBody Job job) throws SchedulerException {
        if (!jobService.checkCronExpressionIsValid(job.getCronExpression())) {
            return Response.error(402, "cron表达式不正确");
        }
        job.setUpdateBy(UserUtils.getUserName());
        return Response.ok(jobService.updateJob(job));
    }

    /**
     * 定时任务状态修改
     */
    @ApiOperation(value = "定时任务状态修改",  httpMethod = "POST")
    @HasPermissions("monitor:job:changeStatus")
    @OperLog(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public Response<Integer> changeStatus(@RequestBody Job job) throws SchedulerException {
        Job newJob = jobService.getById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return Response.ok(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @ApiOperation(value = "定时任务立即执行一次",  httpMethod = "POST")
    @HasPermissions("monitor:job:changeStatus")
    @OperLog(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/run")
    public Response<Boolean> run(@RequestBody Job job) throws SchedulerException {
        jobService.run(job);
        return Response.ok(true);
    }

}
