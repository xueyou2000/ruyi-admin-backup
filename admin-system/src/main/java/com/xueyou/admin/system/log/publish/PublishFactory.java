package com.xueyou.admin.system.log.publish;

import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.AddressUtils;
import com.xueyou.admin.common.core.utils.IpUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.utils.spring.SpringContextHolder;
import com.xueyou.admin.system.domain.LoginInfo;
import com.xueyou.admin.system.log.event.SysLogininforEvent;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志事件发布工具
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:29 上午
 */
@Slf4j
public class PublishFactory {

    /**
     * 记录登陆信息
     *
     * @param username  登陆用户名称
     * @param status    状态
     * @param message   消息
     */
    public static void recordLogininfor(final String username, final String status, final String message) {
        recordLogininfor(username, status, message, null);
    }


    /**
     * 记录登陆信息
     *
     * @param username  登陆用户名称
     * @param status    状态
     * @param message   消息
     * @param userId  登陆用户ID
     */
    public static void recordLogininfor(final String username, final String status, final String message, String userId) {
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginName(username);
        loginInfo.setUserId(userId);
        loginInfo.setIpaddr(ip);
        loginInfo.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginInfo.setBrowser(browser);
        loginInfo.setOs(os);
        loginInfo.setMsg(message);

        // 日志状态
        if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
            loginInfo.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfo.setStatus(Constants.FAIL);
        }

        // 发布事件
        SpringContextHolder.publishEvent(new SysLogininforEvent(loginInfo));
    }


}
