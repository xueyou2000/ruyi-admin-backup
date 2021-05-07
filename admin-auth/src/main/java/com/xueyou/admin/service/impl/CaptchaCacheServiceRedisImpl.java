package com.xueyou.admin.service.impl;

import com.anji.captcha.service.CaptchaCacheService;
import com.xueyou.admin.common.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Redis缓存
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 3:04 下午
 * <p>See: https://gitee.com/anji-plus/captcha/wikis/1.java%E5%90%8E%E7%AB%AF%E6%8E%A5%E5%85%A5-1.2.1%E7%89%88%E6%9C%AC%E5%90%8E?sort_id=2308156</p>
 */
@Slf4j
public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {

    @Override
    public void set(String s, String s1, long l) {
        log.info("CaptchaCacheServiceRedisImpl:set=[{}]", s);
        RedisUtils.setEx(s, s1, (int) l);
    }

    @Override
    public boolean exists(String s) {
        return RedisUtils.hasKey(s);
    }

    @Override
    public void delete(String s) {
        RedisUtils.del(s);
    }

    @Override
    public String get(String s) {
        return RedisUtils.get(s, String.class);
    }

    @Override
    public String type() {
        return "redis";
    }
}
