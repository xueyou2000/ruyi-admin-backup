package com.xueyou.admin.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登陆用户信息
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 5:55 下午
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
