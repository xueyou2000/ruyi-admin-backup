package com.xueyou.admin.system.config.fasjjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * fasjjson LocalDateTime转换
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/28 10:11 上午
 */
public class LocalDateTimeSerializer implements ObjectSerializer {

    public static final LocalDateTimeSerializer instance = new LocalDateTimeSerializer();
    private static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else {
            LocalDateTime result = (LocalDateTime) object;
            out.writeString(result.format(DateTimeFormatter.ofPattern(defaultPattern)));
        }
    }
}
