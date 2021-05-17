package com.xueyou.admin.controller;

import com.google.common.collect.Lists;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.AddressUtils;
import com.xueyou.admin.common.core.utils.UserAgentutils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.common.redis.util.RedisUtils;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.domain.UserOnline;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.service.AccessTokenService;
import com.xueyou.admin.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author xueyou
 * @date 2020/12/21
 */
@Slf4j
@RestController
@RequestMapping("/monitor/online")
@Api(value = "onlineController", tags = "在线用户")
public class OnlineController {

    @Resource
    private AccessTokenService accessTokenService;

    @Resource
    private SysLoginService sysLoginService;

    /**
     * 查询在线用户列表
     *
     * @param loginName     登陆账号(查询条件)
     * @param ip            登陆IP(查询条件)
     */
    @ApiOperation(value = "在线用户列表",  httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登陆账号"),
            @ApiImplicitParam(name = "ip", value = "登陆IP")
    })
    @PostMapping("list")
    @HasPermissions("monitor:online:view")
    public Response<List<UserOnline>> list(@RequestParam(required = false) String loginName, @RequestParam(required = false) String ip) {
        Set<String> onlienKeys = RedisUtils.keys(Constants.ACCESS_TOKEN + "*");
        List<UserOnline> userOnlineList = Lists.newArrayList();

        for (String onlienKey : onlienKeys) {
            User user = RedisUtils.get(onlienKey, User.class);
            String userAgent = user.getUserAgent();
            UserOnline userOnline = new UserOnline();
            userOnline.setTokenId(StringUtils.replace(onlienKey, Constants.ACCESS_TOKEN, ""));
            userOnline.setLoginName(user.getLoginName());
            userOnline.setDeptName(user.getDept().getDeptName());
            userOnline.setIpaddr(user.getLoginIp());
            userOnline.setLoginTime(user.getLoginDate());
            userOnline.setLoginLocation(com.xueyou.admin.common.core.utils.StringUtils.isBlank(user.getLoginLocation()) ? AddressUtils.getRealAddressByIP(user.getLoginIp()) : user.getLoginLocation());
            if (StringUtils.isNotBlank(userAgent)) {
                userOnline.setBrowser(UserAgentutils.getBorderName(userAgent) + " " + UserAgentutils.getBrowserVersion(userAgent));
                userOnline.setOs(UserAgentutils.getOsVersion(userAgent));
            }

            // 根据查询条件筛选
            if (StringUtils.isNotEmpty(ip) && StringUtils.isNotEmpty(loginName)) {
                if (StringUtils.equals(ip, user.getLoginIp()) && StringUtils.equals(loginName, user.getLoginName())) {
                    userOnlineList.add(userOnline);
                }
            }
            if (StringUtils.isNotEmpty(ip)) {
                if (StringUtils.equals(ip, user.getLoginIp())) {
                    userOnlineList.add(userOnline);
                }
            }
            if (StringUtils.isNotEmpty(loginName)) {
                if (StringUtils.equals(loginName, user.getLoginName())) {
                    userOnlineList.add(userOnline);
                }
            }
            if (StringUtils.isEmpty(ip) && StringUtils.isEmpty(loginName)) {
                userOnlineList.add(userOnline);
            }
        }
        userOnlineList.sort((a, b) -> b.getLoginTime().compareTo(a.getLoginTime()));
        return Response.ok(userOnlineList);
    }


    /**
     * 强退用户
     *
     * @param tokenId   token
     */
    @ApiOperation(value = "强退用户",  httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tokenId", value = "会话编号", required = true),
    })
    @PostMapping("forceLogout")
    @OperLog(title = "在线用户", businessType = BusinessType.FORCE)
    @HasPermissions("monitor:online:forceLogout")
    public Response<Boolean> forceLogout(@RequestParam String tokenId) {
        User user = accessTokenService.queryByToken(tokenId);
        if (user != null) {
            sysLoginService.logout(user);
            accessTokenService.expireToken(tokenId);
        }
        return Response.ok(true);
    }

}
