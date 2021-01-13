package com.xueyou.admin.system.log.annotation;

import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 操作日志记录
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:01 下午
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

}
