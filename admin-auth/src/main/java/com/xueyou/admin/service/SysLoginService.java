package com.xueyou.admin.service;

import com.xueyou.admin.system.domain.User;

/**
 * 系统登陆
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 10:28 上午
 */
public interface SysLoginService {

    /**
     * 用户登陆
     *
     * @param username  登陆账户
     * @param password  密码
     */
    User login(String username, String password);

    /**
     * 用户登陆
     *
     * @param user  用户信息
     */
    User login(User user);

    /**
     * 记录登陆信息
     *
     * @param user  用户
     */
    void recordLoginInfo(User user);

    /**
     * 退出登陆/注销
     *
     * @param username 登陆用户名
     */
    void logout(String username);

    /**
     * 退出登陆/注销
     *
     * @param user  用户
     */
    void logout(User user);

}
