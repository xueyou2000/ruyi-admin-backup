package com.xueyou.admin.common.redis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

/**
 * redis工具
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 12:00 下午
 */
@Slf4j
@Component
@ConditionalOnProperty(name="redis.host")
@SuppressWarnings("unchecked")
public class RedisUtils {

    // 默认字符集
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    // jedisPool 实例
    private static JedisPool jedisPool;

    // 默认过期时间为5分钟
    private static final Integer timeOutSecond = 5 * 60;

    @Resource
    public void setJedisPool(JedisPool _jedisPool) {
        jedisPool = _jedisPool;
    }

    /**
     * 执行redis命令
     *
     * @param dbIndex   数据库索引
     * @param action    执行回调方法
     * @param <T>       回调方法的返回值类型
     * @return  回调方法的返回值
     */
    public static <T> T execute(Integer dbIndex, Function<Jedis, T> action) {
        Jedis jedis = jedisPool.getResource();
        if (jedis == null) {
            return null;
        }
        try {
            if (dbIndex != null) {
                jedis.select(dbIndex);
            }
            return action.apply(jedis);
        } catch (JedisConnectionException e) {
            log.error("Redis连接异常: [{" + e.getMessage() +"}]", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 执行redis命令
     *
     * @param action    执行回调方法
     * @param <T>       回调方法的返回值类型
     * @return  回调方法的返回值
     */
    public static <T> T execute(Function<Jedis, T> action) {
        Jedis jedis = jedisPool.getResource();
        if (jedis == null) {
            return null;
        }
        try {
            return action.apply(jedis);
        } catch (JedisConnectionException e) {
            log.error("Redis连接异常: [{" + e.getMessage() +"}]", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    // ======================================= get

    /**
     * 获取字节数据
     */
    public static byte[] get(Integer dbIndex, final byte[] key) {
        if (key != null && key.length != 0) {
            return execute(dbIndex, jedis -> jedis.get(key));
        } else {
            throw new IllegalArgumentException("The Redis key cannot be empty");
        }
    }

    /**
     * 获取对象数据
     */
    public static <T> T get(Integer dbIndex, String key, Class<T> clazz, boolean useJsonSerialize) {
        byte[] bytes = get(dbIndex, SerializeUtils.serialize(key, DEFAULT_CHARSET));
        if (bytes == null) {
            return null;
        }
        return useJsonSerialize
                ? JSON.parseObject(bytes, clazz)
                : (T) SerializeUtils.unserialize(bytes);
    }

    /**
     * 获取对象数据
     */
    public static <T> T getJson(String key, Class<T> clazz) {
        return get(null, key, clazz, true);
    }

    /**
     * 获取对象数据
     */
    public static <T> T get(String key, Class<T> clazz) {
        return get(null, key, clazz, false);
    }

    /**
     * 获取序列化对象
     */
    public static <T> T get(String key, TypeReference<T> typeReference) {
        return get(null, key, typeReference);
    }

    /**
     * 获取序列化对象
     *
     * 例如: get(1, "key", new TypeReference<Map<String, String>>() {}) 获取map类型
     */
    public static <T> T get(Integer dbIndex, String key, TypeReference<T> typeReference) {
        byte[] bytes = get(dbIndex, SerializeUtils.serialize(key, DEFAULT_CHARSET));
        return JSON.parseObject(Arrays.toString(bytes), typeReference);
    }

    // ======================================= set

    /**
     * 设置字节数据
     */
    public static void set(Integer dbIndex, final byte[] key, final byte[] value) {
        if (key != null && key.length != 0) {
            execute(dbIndex, jedis -> jedis.set(key, value));
        } else {
            throw new IllegalArgumentException("The Redis key cannot be empty");
        }
    }

    /**
     * 设置对象数据(指定dbIndex)
     */
    public static void set(Integer dbIndex, String key, Object value, boolean useJsonSerialize) {
        set(dbIndex, SerializeUtils.serialize(key, DEFAULT_CHARSET),
                useJsonSerialize ? JSON.toJSONBytes(value) : SerializeUtils.serialize(value));
    }

    /**
     * 设置对象数据
     */
    public static void set(String key, Object value, boolean useJsonSerialize) {
        set(null, key, value, useJsonSerialize);
    }

    /**
     * 设置对象数据
     */
    public static void setJson(String key, Object value) {
        set(null, key, value, true);
    }

    /**
     * 设置对象数据(不序列化json)
     */
    public static void set(String key, Object value) {
        set(null, key, value, false);
    }


    // ======================================= setEx

    /**
     * 设置可过期对象数据
     */
    public static void setEx(Integer dbIndex, String key, Object value, boolean useJsonSerialize,Integer second) {
        if (second == null) {
            second = timeOutSecond;
        }
        setEx(dbIndex, SerializeUtils.serialize(key, DEFAULT_CHARSET),
                useJsonSerialize ? JSON.toJSONBytes(value) : SerializeUtils.serialize(value),second);
    }

    /**
     * 设置可过期字节数据
     */
    public static void setEx(Integer dbIndex, final byte[] key, final byte[] value, final int seconds) {
        if (key != null && key.length != 0) {
            execute(dbIndex, jedis -> jedis.setex(key, seconds, value));
        } else {
            throw new IllegalArgumentException("The Redis key cannot be empty");
        }
    }

    /**
     * 设置可过期对象数据
     */
    public static void setEx(Integer dbIndex, String key, Object value, int seconds, boolean useJsonSerialize) {
        setEx(dbIndex, SerializeUtils.serialize(key, DEFAULT_CHARSET),
                useJsonSerialize ? JSON.toJSONBytes(value) : SerializeUtils.serialize(value), seconds);
    }

    /**
     * 设置可过期对象数据
     */
    public static void setEx(String key, Object value, int seconds, boolean useJsonSerialize) {
        setEx(null, key, value, seconds, useJsonSerialize);
    }

    /**
     * 设置可过期对象数据
     */
    public static void setExJson(String key, Object value, int seconds) {
        setEx(null, key, value, seconds, true);
    }


    /**
     * 设置可过期对象数据(不序列化json)
     */
    public static void setEx(String key, Object value, int seconds) {
        setEx(null, key, value, seconds, false);
    }

    /**
     * 剩余生存时间(秒)
     *
     * @param dbIndex   数据库索引
     * @param key   键
     * @return  -1则不过期, 如果key不存在或者已过期，返回-2
     */
    public static Long getExSeconds(Integer dbIndex, String key) {
        return execute(dbIndex, jedis -> jedis.ttl(key));
    }

    /**
     * 剩余生存时间(秒)
     *
     * @param key   键
     * @return  -1则不过期, 如果key不存在或者已过期，返回-2
     */
    public static Long getExSeconds(String key) {
        return execute(null, jedis -> jedis.ttl(key));
    }

    // ======================================= incr

    /**
     * 自增数据
     * @param dbIndex   数据库索引
     * @param key   键
     * @param seconds   有效期[可选]
     * @return  自增后的值
     */
    public static Long incr(Integer dbIndex, final String key, final Integer seconds) {
        if (!StringUtils.isEmpty(key)) {
            return execute(dbIndex, jedis -> {
                Long incr = null;
                if (seconds != null) {
                    Transaction transaction = jedis.multi();
                    transaction.incr(key);
                    transaction.expire(key, seconds);
                    List<Object> results = transaction.exec();
                    incr = (Long)results.get(0);
                } else {
                    incr = jedis.incr(key);
                }
                return incr;
            });
        } else {
            throw new IllegalArgumentException("The Redis key cannot be empty");
        }
    }

    /**
     * 自增数据
     */
    public static Long incr(final String key, final Integer seconds) {
        return incr(null, key, seconds);
    }

    /**
     * 自增数据
     */
    public static Long incr(final String key) {
        return incr(null, key, null);
    }

    // ======================================= del

    /**
     * 删除缓存数据
     * @param key   缓存键
     */
    public static Long del(final String key) {
        if (!StringUtils.isEmpty(key)) {
            return jedisPool.getResource().del(key);
        } else {
            throw new IllegalArgumentException("The Redis key cannot be empty");
        }
    }

    public static Set<String> smembers(String key){
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.smembers(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Long sadd(String key,String value){
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // ======================================= other

    /**
     * 是否存在缓存
     *
     * @param key   键
     */
    public static boolean hasKey(String key) {
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取匹配key
     *
     * @param pattern 匹配模式
     */
    public static Set<String> keys(String pattern) {
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

}
