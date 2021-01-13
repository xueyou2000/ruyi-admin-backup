package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.common.redis.util.RedisUtils;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;

import com.xueyou.admin.system.domain.Role;
import com.xueyou.admin.system.model.dto.RoleQuery;
import com.xueyou.admin.system.service.RoleService;

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
 * 角色
 *
 * @author xueyou
 * @date 2020-12-30
 */
@RestController
@RequestMapping("api/system/role")
@Api(value = "RoleController", tags = "角色")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询角色
     */
    @ApiOperation(value = "查询角色",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("system:role:view")
    public Response<IPage<Role>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody Role role) {
        IPage<Role> page = new Page<>(pageNumber, pageSize);
        return Response.ok(roleService.selectRoleByPage(page, role));
    }

    /**
     * 查询所有角色
     */
    @ApiOperation(value = "查询所有角色",  httpMethod = "POST")
    @PostMapping("all")
    @HasPermissions("system:role:view")
    public Response<List<Role>> all() {
        return Response.ok(roleService.selectRoleAll());
    }

    /**
     * 新增角色
     */
    @ApiOperation(value = "新增角色",  httpMethod = "POST")
    @HasPermissions("system:role:add")
    @OperLog(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public Response<Integer> add(@RequestBody Role role) {
        String loginName = ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
        role.setCreateBy(loginName);
        return Response.ok(roleService.insertRole(role));
    }

    /**
     * 修改角色
     */
    @ApiOperation(value = "修改角色",  httpMethod = "POST")
    @HasPermissions("system:role:update")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public Response<Integer> update(@RequestBody Role role) {
        String loginName = ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
        role.setUpdateBy(loginName);
        role.setUpdateTime(LocalDateTime.now());
        // 删除redis缓存权限
        Set<String> keys = RedisUtils.keys("user_perms:*");
        for (String key : keys) {
            RedisUtils.del(key);
        }
        return Response.ok(roleService.updateRole(role));
    }

    /**
     * 删除角色
     */
    @ApiOperation(value = "删除角色",  httpMethod = "POST")
    @HasPermissions("system:role:remove")
    @OperLog(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(roleService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 保存角色分配数据权限
     */
    @HasPermissions("system:role:update")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    public Response<Boolean> authDataScopeSave(@RequestBody Role role) {
        String loginName = ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
        role.setUpdateBy(loginName);
        role.setUpdateTime(LocalDateTime.now());
        if (roleService.authDataScope(role) > 0) {
            return Response.ok(true);
        }
        return Response.error(410, "修改角色数据权限失败");
    }

}