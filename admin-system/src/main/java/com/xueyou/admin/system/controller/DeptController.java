package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.auth.annotation.HasPermissions;

import com.xueyou.admin.system.domain.Dept;
import com.xueyou.admin.system.domain.Role;
import com.xueyou.admin.system.service.DeptService;

import com.xueyou.admin.system.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 部门
 *
 * @author xueyou
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/dept")
@Api(value = "DeptController", tags = "部门")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 查询部门
     */
    @ApiOperation(value = "查询部门",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("system:dept:view")
    public Response<IPage<Dept>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody Dept dept) {
        Page<Dept> page = new Page<>(pageNumber, pageSize);
        return Response.ok(deptService.selectDeptByPage(page, dept));
    }

    /**
     * 查询所有部门
     */
    @ApiOperation(value = "查询所有部门",  httpMethod = "POST")
    @PostMapping("all")
    @HasPermissions("system:dept:view")
    public Response<List<Dept>> all() {
        return Response.ok(deptService.selectDeptAll());
    }

    /**
     * 新增部门
     */
    @ApiOperation(value = "新增部门",  httpMethod = "POST")
    @HasPermissions("system:dept:add")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody Dept dept) {
        dept.setCreateBy(UserUtils.getUserName());
        return Response.ok(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @ApiOperation(value = "修改部门",  httpMethod = "POST")
    @HasPermissions("system:dept:update")
    @PostMapping("update")
    public Response<Boolean> update(@RequestBody Dept dept) {
        dept.setUpdateBy(UserUtils.getUserName());
        dept.setUpdateTime(LocalDateTime.now());
        return Response.ok(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @ApiOperation(value = "删除部门",  httpMethod = "POST")
    @HasPermissions("system:dept:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(deptService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 获取选择部门(数据权限) 用于选择
     */
    @ApiOperation(value = "获取角色部门",  httpMethod = "POST")
    @PostMapping("role/{roleId}")
    public Response<Set<Long>> getDeptIdsByRole(@PathVariable("roleId") Long roleId) {
        return Response.ok(deptService.roleDeptIds(roleId));
    }

    /**
     * 查询可用部门
     */
    @ApiOperation(value = "查询可用部门",  httpMethod = "POST")
    @PostMapping("findEnableDepts")
    public Response<List<Dept>> findEnableDepts(@RequestBody Dept dept) {
        dept.setStatus("0");
        return Response.ok(deptService.selectRelationDeptList(dept));
    }

}