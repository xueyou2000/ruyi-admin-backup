package com.xueyou.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.annotation.LoginUser;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.domain.Menu;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.model.dto.MenuQueryDto;
import com.xueyou.admin.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/21 4:37 下午
 */
@RestController
@RequestMapping("/system/menu")
@Api(value = "MenuController", tags = "系统菜单")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 获取当前登陆用户菜单
     */
    @ApiOperation(value = "获取当前登陆用户菜单",  httpMethod = "POST")
    @PostMapping("user")
    public Response<List<Menu>> user(@LoginUser User user) {
        return Response.ok(menuService.selectMenusByUser(user));
    }

    /**
     * 获取当前用户菜单和权限
     */
    @ApiOperation(value = "获取当前用户菜单和权限",  httpMethod = "POST")
    @PostMapping("permissions")
    public Response<List<Menu>> permissions(@LoginUser User user) {
        return Response.ok(menuService.selectMenuPermissionsList(user));
    }

    /**
     * 查询菜单列表
     */
    @ApiOperation(value = "查询菜单列表",  httpMethod = "POST")
    @PostMapping("list")
    public Response<List<Menu>> list(Menu menu) {
        return Response.ok(menuService.selectMenuList(menu));
    }

    /**
     * 查询菜单列表
     */
    @ApiOperation(value = "查询菜单列表",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    public Response<IPage<Menu>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody MenuQueryDto queryDto) {
        IPage<Menu> page = new Page<>(pageNumber, pageSize);
        return Response.ok(menuService.queryByPage(page,queryDto.getMenu(), queryDto.getQueryBaseDto()));
    }

    /**
     * 新增菜单
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增菜单",  httpMethod = "POST")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody Menu menu) {
        return Response.ok(menuService.save(menu));
    }

    /**
     * 修改菜单
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "修改菜单",  httpMethod = "POST")
    @PostMapping("update")
    public Response<Boolean> update(@RequestBody Menu menu) {
        return Response.ok(menuService.updateById(menu));
    }

    /**
     * 删除菜单
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除菜单",  httpMethod = "POST")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam Long menuId) {
        return  Response.ok(menuService.deleteMenuById(menuId));
    }

    /**
     * 根据角色编号查询所选菜单（用于勾选）
     */
    @PostMapping("role/{roleId}")
    public Response<List<Menu>> role(@PathVariable("roleId") Long roleId) {
        if (null == roleId || roleId <= 0) return Response.ok(new ArrayList<>());
        return Response.ok(menuService.selectMenuIdsByRoleId(roleId));
    }

}
