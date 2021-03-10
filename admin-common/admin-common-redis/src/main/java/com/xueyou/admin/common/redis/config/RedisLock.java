package com.xueyou.admin.common.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * redis锁
 *
 * @author xueyou
 * @date 2021/3/10
 */
@Slf4j
@Component
public class RedisLock {

    private static final long DEFAULT_EXPIRE_UNUSED = 30000L;

    @Resource
    private RedisLockRegistry redisLockRegistry;

    public void lock(String lockKey) {
        Lock lock = obtainLock(lockKey);
        lock.lock();
    }

    public boolean tryLock(String lockKey) {
        Lock lock = obtainLock(lockKey);
        return lock.tryLock();
    }

    public boolean tryLock(String lockKey, long seconds) {
        Lock lock = obtainLock(lockKey);
        try {
            return lock.tryLock(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock(String lockKey) {
        try {
            Lock lock = obtainLock(lockKey);
            lock.unlock();
            redisLockRegistry.expireUnusedOlderThan(DEFAULT_EXPIRE_UNUSED);
        } catch (Exception e) {
            log.error("分布式锁 [{}] 释放异常", lockKey, e);
        }
    }

    private Lock obtainLock(String lockKey) {
        return redisLockRegistry.obtain(lockKey);
    }

}
