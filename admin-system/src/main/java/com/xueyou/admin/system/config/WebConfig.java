package com.xueyou.admin.system.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xueyou.admin.system.config.fasjjson.LocalDateSerializer;
import com.xueyou.admin.system.config.fasjjson.LocalDateTimeSerializer;
import com.xueyou.admin.system.config.resolver.LoginUserHandlerResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Web服务配置
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/25 1:38 下午
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginUserHandlerResolver loginUserHandlerResolver;

    /**
     * 静态资源处理
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");
        registry.addResourceHandler("/public").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static");
    }

    /**
     * 配置数据转换
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(2, createFastConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserHandlerResolver);
    }

    /**
     * 创建 json 转换器
     */
    private FastJsonHttpMessageConverter createFastConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 全局指定了日期格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        SerializerFeature[] serializerFeatures = new SerializerFeature[] {
                // 输出key是包含双引号
                SerializerFeature.QuoteFieldNames,
                // 是否输出为null的字段,若为null 则显示该字段
                // SerializerFeature.WriteMapNullValue,
                // 数值字段如果为null，则输出为0
                SerializerFeature.WriteNullNumberAsZero,
                // List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                // 字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteNullStringAsEmpty,
                // Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                // Date的日期转换器
                SerializerFeature.WriteDateUseDateFormat,
                // 保留空字段Ø
                SerializerFeature.WriteMapNullValue,
                // 循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                // 枚举
                SerializerFeature.WriteEnumUsingToString,
        };

        // 转换 LocalDateTime 类型
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(LocalDateTime.class, LocalDateTimeSerializer.instance);
        serializeConfig.put(LocalDate.class, LocalDateSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
