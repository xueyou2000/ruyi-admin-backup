package com.xueyou.admin.common.redis.config;

import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * Spring Cache 的 Redis缓存配置
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 11:55 上午
 */
@Slf4j
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory jedisConnectionFactory) {
        // 初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        // 初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
        // 设置白名单---非常重要
        /*
        使用fastjson的时候：序列化时将class信息写入，反解析的时候，
        fastjson默认情况下会开启autoType的检查，相当于一个白名单检查，
        如果序列化信息中的类路径不在autoType中，
        反解析就会报com.alibaba.fastjson.JSONException: autoType is not support的异常
        可参考 https://blog.csdn.net/u012240455/article/details/80538540
         */
        ParserConfig.getGlobalInstance().addAccept("com.xueyou.admin.");
        return cacheManager;
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(MyRedisConfig myRedisConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(myRedisConfig.getHost());
        redisStandaloneConfiguration.setDatabase(myRedisConfig.getDbIndex());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(myRedisConfig.getPassword()));
        redisStandaloneConfiguration.setPort(myRedisConfig.getPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

}
