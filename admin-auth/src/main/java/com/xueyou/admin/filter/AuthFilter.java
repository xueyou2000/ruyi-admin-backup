package com.xueyou.admin.filter;

import com.alibaba.fastjson.JSON;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.common.redis.util.RedisUtils;
import com.xueyou.admin.system.config.AdminConfig;
import com.xueyou.admin.system.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤器
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 11:20 上午
 */
@Slf4j
public class AuthFilter implements Filter {

    @Resource
    private AdminConfig adminConfig;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);

        String url = req.getRequestURI();
        log.debug("url:{}", url);

        // 白名单请求跳过验证
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pattern : adminConfig.getWhiteList()) {
            if (antPathMatcher.match(pattern, url)) {
                chain.doFilter(mutableRequest, response);
                return;
            }
        }

        // token为空
        String token = req.getHeader(Constants.AUTHORIZATION_HEAD);
        if (StringUtils.isBlank(token)) {
            setUnauthorizedResponse(response, "token can't null or empty string");
            return;
        }

        User user = RedisUtils.get(Constants.ACCESS_TOKEN + token, User.class);
        if (user == null || user.getUserId() == null) {
            setUnauthorizedResponse(response, "token verify error");
            return;
        }

        mutableRequest.putHeader(Constants.CURRENT_ID, String.valueOf(user.getUserId()));
        mutableRequest.putHeader(Constants.CURRENT_USERNAME, user.getLoginName());
        chain.doFilter(mutableRequest, response);
    }

    /**
     * 回写未授权响应
     */
    private void setUnauthorizedResponse(ServletResponse servletResponse, String msg) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(Response.error(HttpStatus.UNAUTHORIZED.value(), msg)));
        response.getWriter().flush();
    }

}
