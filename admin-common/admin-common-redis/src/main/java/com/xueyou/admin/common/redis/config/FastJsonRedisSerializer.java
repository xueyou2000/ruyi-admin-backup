package com.xueyou.admin.common.redis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Redis使用FastJson序列化
 * 必须重写序列化器，否则@Cacheable注解的key会报类型转换错误
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 11:55 上午
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }


}
