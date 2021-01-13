package com.xueyou.admin.common.core;

/**
 * 通用常亮
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 11:27 上午
 */
public class Constants {

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * token-请求头
     */
    public static final String AUTHORIZATION_HEAD = "token";

    /**
     * 语言-请求头
     */
    public static final String ACCEPT_LANGUAGE_HEAD = "Accept-Language";

    /**
     * 授权token, redis键前缀
     */
    public final static String ACCESS_TOKEN = "access_token_";

    /**
     * 用户id, redis键前缀
     */
    public final static String ACCESS_USERID = "access_userid_";

    /**
     * 当前http用户id
     */
    public static final String CURRENT_ID = "current_id";

    /**
     * 当前http用户登陆账户
     */
    public static final String CURRENT_USERNAME = "current_username";


    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";


}
