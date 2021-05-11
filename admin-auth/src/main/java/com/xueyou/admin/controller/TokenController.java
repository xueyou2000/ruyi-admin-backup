package com.xueyou.admin.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.service.CaptchaService;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.CodeBuilder;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.redis.util.RedisUtils;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.model.dto.LoginDto;
import com.xueyou.admin.model.dto.MobileLoginDto;
import com.xueyou.admin.model.vo.TokenInfo;
import com.xueyou.admin.service.AccessTokenService;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.service.SysLoginService;
import com.xueyou.admin.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登陆认证
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 3:24 下午
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(value = "TokenController", tags = "登陆认证")
public class TokenController {

    @Resource
    private CaptchaService captchaService;

    @Resource
    private AccessTokenService accessTokenService;

    @Resource
    private SysLoginService sysLoginService;

    @Resource
    private UserService userService;

    /**
     * 账号密码登陆
     */
    @ApiOperation(value = "账号密码登陆",  httpMethod = "POST")
    @PostMapping("login")
    public Response<TokenInfo> login(@RequestBody LoginDto loginDto) {
        // 用户登陆
        User user = sysLoginService.login(loginDto.getUsername(), loginDto.getPassword());
        // 获取token
        return Response.ok(accessTokenService.createToken(user));
    }

    /**
     * 滑块验证码登陆
     *
     * @param loginDto  登陆参数
     */
    @ApiOperation(value = "滑动验证登陆",  httpMethod = "POST")
    @PostMapping("login/captcha")
    public Response<TokenInfo> loginByCaptcha(@RequestBody LoginDto loginDto) {
        ResponseModel response = captchaService.verification(loginDto.getCaptchaVO());
        if (response.isSuccess()) {
            // 用户登陆
            User user = sysLoginService.login(loginDto.getUsername(), loginDto.getPassword());
            // 获取token
            return Response.ok(accessTokenService.createToken(user));
        }
        return Response.error(-1, response.getRepMsg());
    }

    /**
     * 退出登陆
     */
    @PostMapping("logout")
    @ApiOperation(value = "退出登陆",  httpMethod = "POST")
    public Response<Boolean> logout(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION_HEAD);
        User user = accessTokenService.queryByToken(token);
        if (user != null) {
            sysLoginService.logout(user);
            accessTokenService.expireToken(token);
        }
        return Response.ok(true);
    }

    /**
     * 获取手机短信验证码
     *
     * @param mobile    手机号
     */
    @PostMapping("mobile-captch")
    @ApiOperation(value = "获取手机短信验证码",  httpMethod = "POST")
    public Response<Boolean> getMobileCaptcha(@RequestParam String mobile) {
        User user = userService.selectUserByMobile(mobile);
        if (user != null) {
            String captch = CodeBuilder.buildNumber(6);
            RedisUtils.setEx("mobile-captch:" + mobile, captch, 120);
            log.info("获取手机短信验证码: mobile=[{}], captch=[{}]", mobile, captch);
            return  Response.ok(true);
        } else {
            return Response.error(-1, "手机号错误");
        }
    }

    /**
     * 手机验证码登陆
     *
     */
    @PostMapping("login/mobile")
    @ApiOperation(value = "手机验证码登陆",  httpMethod = "POST")
    public Response<TokenInfo> loginByMobile(@RequestBody MobileLoginDto mobileLoginDto) {
        String captch = RedisUtils.get("mobile-captch:" + mobileLoginDto.getMobile(), String.class);
        if (StringUtils.isNotBlank(captch) && StringUtils.pathEquals(captch, mobileLoginDto.getCaptcha())) {
            User user = userService.selectUserByMobile(mobileLoginDto.getMobile());
            // 用户登陆
            sysLoginService.login(user);
            // 获取token
            return Response.ok(accessTokenService.createToken(user));
        } else {
            return Response.error(-1, "验证码错误");
        }
    }

}
