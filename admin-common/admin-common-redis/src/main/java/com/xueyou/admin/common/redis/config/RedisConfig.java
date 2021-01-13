package com.xueyou.admin.common.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * redis连接池配置
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 11:55 上午
 */
@Configuration
public class RedisConfig {

    @Resource
    private MyRedisConfig myRedisConfig;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        MyRedisConfig.RedisPool config = myRedisConfig.getPool();

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(config.getMaxTotal());
        jedisPoolConfig.setMinIdle(config.getMinIdle());
        jedisPoolConfig.setMaxIdle(config.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
        jedisPoolConfig.setBlockWhenExhausted(config.isBlockWhenExhausted());
        jedisPoolConfig.setTestOnBorrow(config.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(config.isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(config.isTestWhileIdle());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
        jedisPoolConfig.setNumTestsPerEvictionRun(config.getNumTestsPerEvictionRun());
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(config.getSoftMinEvictableIdleTimeMillis());
        jedisPoolConfig.setLifo(config.isLifo());

        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool() {
        // 有需要可以使用JedisShardInfo, 做redis集群
        return new JedisPool(
                this.jedisPoolConfig(),
                myRedisConfig.getHost(),
                myRedisConfig.getPort(),
                myRedisConfig.getConnTimeout(),
                myRedisConfig.getPassword(),
                myRedisConfig.getDbIndex());
    }

}
