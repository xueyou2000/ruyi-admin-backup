package com.xueyou.admin.system.utils;

import com.xueyou.admin.common.core.utils.Md5Utils;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.system.domain.User;

/**
 * 密码加解密
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 5:14 下午
 */
public class PasswordUtils {

    /**
     * 加密密码
     *
     * @param username  登陆账户
     * @param password  明文密码
     * @param salt      加密盐
     */
    public static String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }

    /**
     * 密码匹配
     *
     * @param username  登陆账户
     * @param password  明文密码
     * @param salt      加密盐
     * @param rawPassword  加密密码
     */
    public static boolean matches(String username, String password, String salt, String rawPassword) {
        return StringUtils.pathEquals(rawPassword, encryptPassword(username, password, salt));
    }

    /**
     * 密码匹配
     *
     * @param user      用户信息
     * @param password  明文密码
     */
    public static boolean matches(User user, String password) {
        return matches(user.getLoginName(), password, user.getSalt(), user.getPassword());
    }

}
