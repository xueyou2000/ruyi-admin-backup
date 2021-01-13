package com.xueyou.admin.system.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 1:51 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermissions {
    String value();
}
