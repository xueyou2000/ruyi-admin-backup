package com.xueyou.admin.common.redis.annotation;

import java.lang.annotation.*;

/**
 * Redis缓存
 * 不用SpringCache是因为不好用，不支持自定义过期时间
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:56 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * 键名
     *
     */
    String key() default "";

    /**
     * 主键
     *
     */
    String fieldKey();

    /**
     * 过期时间
     */
    long expired() default 3600;

}
