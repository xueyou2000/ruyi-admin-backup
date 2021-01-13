package com.xueyou.admin.common.core.controller;

import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 通用控制器层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:29 下午
 */
public class BaseController {

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前登陆账号
     */
    public String getLoginName() {
        return getRequest().getHeader(Constants.CURRENT_USERNAME);
    }

}
