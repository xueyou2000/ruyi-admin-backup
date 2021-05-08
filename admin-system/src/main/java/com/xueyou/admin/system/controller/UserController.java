package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.annotation.LoginUser;
import com.xueyou.admin.common.core.constant.UserConstants;
import com.xueyou.admin.common.core.controller.BaseController;
import com.xueyou.admin.common.core.utils.RandomUtils;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.service.MenuService;
import com.xueyou.admin.system.service.UserService;
import com.xueyou.admin.system.utils.PasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 用户信息控制器
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:28 下午
 */
@RestController
@RequestMapping("/system/user")
@Api(value = "UserController", tags = "系统用户")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "获取用户信息",  httpMethod = "POST")
    @PostMapping("info")
    public Response<User> info(@LoginUser User user) {
        user.setButtons(menuService.selectPermsByUserId(user.getUserId()));
        user.setSalt(null);
        user.setPassword(null);
        return Response.ok(user);
    }

    /**
     * 获取用户信息根据id
     */
    @HasPermissions("system:user:update")
    @ApiOperation(value = "获取用户信息根据id",  httpMethod = "POST")
    @PostMapping("get/{userId}")
    public Response<User> get(@PathVariable("userId") String userId) {
        return Response.ok(userService.selectUserById(userId));
    }

    /**
     * 查询用户
     */
    @ApiOperation(value = "查询用户",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("system:user:view")
    public Response<IPage<User>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody User user) {
        Page<User> page = new Page<>(pageNumber, pageSize);
        return Response.ok(userService.selectUserByPage(page, user));
    }

    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户",  httpMethod = "POST")
    @HasPermissions("system:user:add")
    @PostMapping("add")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public Response<Boolean> add(@RequestBody User user) {
        if (!userService.checkLoginNameUnique(user.getLoginName())) {
            return Response.error(402, "登陆账号已存在!");
        } else if (!userService.checkPhonenUnique(user.getPhonenumber())) {
            return Response.error(402, "手机号已存在!");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(UserConstants.DEFAULT_PASSWORD);
        }
        user.setSalt(RandomUtils.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        String loginName = ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
        user.setCreateBy(loginName);
        return Response.ok(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户",  httpMethod = "POST")
    @HasPermissions("system:user:update")
    @PostMapping("update")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public Response<Boolean> update(@RequestBody User user) {
        User oldUser = userService.selectUserById(user.getUserId());
        if (!user.getPhonenumber().equals(oldUser.getPhonenumber()) && !userService.checkPhonenUnique(user.getPhonenumber())) {
            return Response.error(402, "手机号已存在!");
        }
        String loginName = ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
        user.setUpdateBy(loginName);
        user.setUpdateTime(LocalDateTime.now());
        return Response.ok(userService.updateUser(user));
    }

    /**
     * 修改状态
     */
    @ApiOperation(value = "修改状态",  httpMethod = "POST")
    @HasPermissions("system:user:update")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public Response<Boolean> changeStatus(@RequestParam String userId, @RequestParam String status) {
        return Response.ok(userService.changeStatus(userId, status));
    }

    /**
     * 重置密码
     */
    @ApiOperation(value = "重置密码",  httpMethod = "POST")
    @HasPermissions("system:user:resetPwd")
    @OperLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public Response<Boolean> resetPwdSave(@RequestParam String userId, @RequestParam String newPassword) {
        return Response.ok(userService.restPassword(userId, newPassword));
    }

    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户",  httpMethod = "POST")
    @HasPermissions("system:user:remove")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(userService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }


}
