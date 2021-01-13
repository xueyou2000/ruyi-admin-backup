package com.xueyou.admin.system.auth.aspect;

import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import com.xueyou.admin.common.core.exception.auth.ForbiddenException;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.service.MenuService;
import com.xueyou.admin.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 授权
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 1:52 下午
 */
@Aspect
@Component
@Slf4j
public class PreAuthorizeAspect {

    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    @Around("@annotation(com.xueyou.admin.system.auth.annotation.HasPermissions)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        HasPermissions annotation = method.getAnnotation(HasPermissions.class);
        if (annotation == null) {
            return point.proceed();
        }
        String authority = annotation.value();
        if (has(authority)) {
            return point.proceed();
        } else {
            throw new ForbiddenException();
        }
    }

    /**
     * 是否有权限
     *
     * @param authority 权限字符
     */
    private boolean has(String authority) {
        HttpServletRequest request = ServletUtils.getRequest();
        String userId = request.getHeader(Constants.CURRENT_ID);
        if (Optional.ofNullable(userId).isPresent()) {
            // 超级管理员不进行权限拦截
            User user = userService.selectUserById(userId);
            if (user != null && TrueOrFalse.TRUE.equals(user.getAdmin())) {
                return true;
            }
            return menuService.selectPermsByUserId(userId).stream().anyMatch(authority::equals);
        }
        return false;
    }

}
