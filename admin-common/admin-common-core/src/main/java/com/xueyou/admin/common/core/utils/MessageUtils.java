package com.xueyou.admin.common.core.utils;

import com.xueyou.admin.common.core.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * 获取I18n资源消息
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 8:56 上午
 */
public class MessageUtils {

    /**
     * 获取国际化消息
     *
     * @param propertyKey   消息键
     * @param locale        语言
     * @param fallback      备用消息
     * @param args          参数
     */
    public static String message(String propertyKey, Locale locale, String fallback, Object... args) {
        if (StringUtils.isBlank(propertyKey)) {
            return fallback;
        }
        MessageSource messageSource = SpringUtils.getBean(ReloadableResourceBundleMessageSource.class);
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }
        try {
            if (args == null) {
                args = new Object[0];
            }
            return messageSource.getMessage(propertyKey, args, locale);
        } catch (NoSuchMessageException e) {
            return fallback;
        }
    }

    /**
     * 获取国际化消息
     *
     * @param propertyKey   消息键
     * @param locale        语言
     * @param fallback      备用消息
     */
    public static String message(String propertyKey, Locale locale, String fallback) {
        return message(propertyKey, locale, fallback, (Object) null);
    }

    /**
     * 获取国际化消息
     *
     * @param propertyKey  消息键
     * @param locale        语言
     */
    public static String messageByLocal(String propertyKey, Locale locale) {
        return message(propertyKey, locale, null);
    }

    /**
     * 获取国际化消息
     *
     * @param propertyKey  消息键
     * @param args        参数
     */
    public static String message(String propertyKey, Object... args) {
        return message(propertyKey, null, null, args);
    }

    /**
     * 获取国际化消息
     *
     * @param propertyKey   消息键
     */
    public static String message(String propertyKey) {
        return message(propertyKey, (Object) null);
    }

}