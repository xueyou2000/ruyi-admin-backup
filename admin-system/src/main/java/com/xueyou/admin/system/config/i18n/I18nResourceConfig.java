package com.xueyou.admin.system.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.ResourceUtils;

/**
 * 国际化资源配置
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:03 上午
 */
@Configuration
public class I18nResourceConfig {

    /**
     * 配置国际化资源文件
     * 配置文件的命名格式一般为${name}_${language}_${region}
     */
    @Bean("messageSource")
    @Primary
    ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setBasenames(ResourceUtils.CLASSPATH_URL_PREFIX + "i18n/message", ResourceUtils.CLASSPATH_URL_PREFIX + "i18n/common-messages", ResourceUtils.CLASSPATH_URL_PREFIX + "i18n/business-message");
        bundleMessageSource.setCacheMillis(10);
        return  bundleMessageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }

}
