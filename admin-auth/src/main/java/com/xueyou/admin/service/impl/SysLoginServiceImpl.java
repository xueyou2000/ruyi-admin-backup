package com.xueyou.admin.service.impl;

import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.constant.UserConstants;
import com.xueyou.admin.common.core.enums.UserStatus;
import com.xueyou.admin.common.core.exception.user.*;
import com.xueyou.admin.common.core.utils.IpUtils;
import com.xueyou.admin.common.core.utils.MessageUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.system.log.publish.PublishFactory;
import com.xueyou.admin.service.SysLoginService;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.service.UserService;
import com.xueyou.admin.system.utils.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 系统登陆
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 10:32 上午
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {

    @Resource
    private UserService userService;

    /**
     * 用户登陆
     *
     * @param username 登陆账户
     * @param password 密码
     */
    @Override
    public User login(String username, String password) {

        try {
            // 用户名或密码为空 错误
            if (StringUtils.isAnyBlank(username, password)) {
                throw new UserNotExistsException();
            }
            // 密码如果不在指定范围内 错误
            if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                    || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
                throw new UserPasswordNotMatchException();
            }
            // 用户名不在指定范围内 错误
            if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                    || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
                throw new UserPasswordNotMatchException();
            }

            // 检测用户状态
            User user = userService.selectUserByLoginName(username);
            checkUser(user);

            if (!PasswordUtils.matches(user, password)) {
                throw new UserPasswordNotMatchException();
            }

            PublishFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"), user.getUserId());
            recordLoginInfo(user);
            return user;
        } catch (UserException e) {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage());
            throw e;
        }
    }

    /**
     * 用户登陆
     *
     * @param user 用户信息
     */
    @Override
    public User login(User user) {
        try {
            checkUser(user);

            PublishFactory.recordLogininfor(user.getLoginName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"), user.getUserId());
            recordLoginInfo(user);
            return user;
        } catch (UserException e) {
            if (user != null) {
                PublishFactory.recordLogininfor(user.getLoginName(), Constants.LOGIN_FAIL, e.getMessage());
            }
            throw e;
        }
    }

    /**
     * 检测用户状态
     *
     * @param user 用户信息
     */
    private void checkUser(User user) {
        if (user == null) {
            throw new UserNotExistsException();
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            throw new UserDeleteException();
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            throw new UserBlockedException();
        }
    }

    /**
     * 记录登陆信息
     *
     * @param user 用户
     */
    @Override
    public void recordLoginInfo(User user) {
        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLoginDate(LocalDateTime.now());
        userService.updateUser(user);
    }

    /**
     * 退出登陆/注销
     *
     * @param username 登陆用户名
     */
    @Override
    public void logout(String username) {
        PublishFactory.recordLogininfor(username, Constants.LOGOUT, MessageUtils.message("user.logout.success"));
    }

    /**
     * 退出登陆/注销
     *
     * @param user 用户
     */
    @Override
    public void logout(User user) {
        PublishFactory.recordLogininfor(user.getLoginName(), Constants.LOGOUT, MessageUtils.message("user.logout.success"), user.getUserId());
    }


}
