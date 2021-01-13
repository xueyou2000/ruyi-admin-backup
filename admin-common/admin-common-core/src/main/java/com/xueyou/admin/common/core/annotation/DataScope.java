package com.xueyou.admin.common.core.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表的别名
     */
    String deptAlias() default "";

    /**
     * 用户表的别名
     */
    String userAlias() default "";

    /**
     * 实体参数位置
     */
    int paramsIndex() default 0;

}
