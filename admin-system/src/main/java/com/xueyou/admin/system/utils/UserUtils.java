package com.xueyou.admin.system.utils;

import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.redis.util.RedisUtils;
import com.xueyou.admin.system.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户工具
 * @author xueyou
 * @date 2021/5/10
 */
public class UserUtils {

    /**
     * 获取当前登陆用户
     */
    public static User getCurrentUser() {
        // 获取当前的用户
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("token");
        return RedisUtils.get(Constants.ACCESS_TOKEN + token, User.class);
    }

    /**
     * 获取当前登陆用户账户
     */
    public static String getUserName() {
        return ServletUtils.getRequest().getHeader(Constants.CURRENT_USERNAME);
    }

}
