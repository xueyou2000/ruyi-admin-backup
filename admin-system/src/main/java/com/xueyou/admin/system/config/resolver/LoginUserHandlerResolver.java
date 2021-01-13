package com.xueyou.admin.system.config.resolver;

import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.annotation.LoginUser;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 为@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 5:56 下午
 */
@Configuration
public class LoginUserHandlerResolver implements HandlerMethodArgumentResolver {

    @Resource
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(User.class) && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest nativeWebRequest, WebDataBinderFactory factory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        // 获取用户ID
        assert request != null;
        String userId = String.valueOf(request.getHeader(Constants.CURRENT_ID));
        return userService.selectUserById(userId);
    }
}
