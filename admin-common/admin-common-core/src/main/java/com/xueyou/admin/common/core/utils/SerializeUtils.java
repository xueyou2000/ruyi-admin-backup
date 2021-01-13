package com.xueyou.admin.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 12:47 下午
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

}
