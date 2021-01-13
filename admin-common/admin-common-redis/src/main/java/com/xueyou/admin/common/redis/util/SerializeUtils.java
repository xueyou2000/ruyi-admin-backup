package com.xueyou.admin.common.redis.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

/**
 * 序列化工具
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 1:17 下午
 */
@Slf4j
public class SerializeUtils {

    /**
     * 序列化
     *
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos;
        ByteArrayOutputStream baos;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("序列化异常: object=[{" + object + "}],  message=[{" + e.getMessage() +"}]", e);
        }
        return null;
    }

    /**
     * 反序列化
     *
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.error("反序列化: message=[{" + e.getMessage() +"}]", e);
        }
        return null;
    }

    /**
     * 字符串转字节数组
     * @param string    字符串
     * @param charset   编码
     */
    public static byte[] serialize(String string, Charset charset) {
        return string == null ? null : string.getBytes(charset);
    }

    /**
     * 字节数组转字符串
     * @param bytes 字节数组
     * @param charset   编码
     */
    public static String deserialize(byte[] bytes, Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }

}
