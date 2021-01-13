package com.xueyou.admin.config;

import com.xueyou.admin.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:22 下午
 */
@Configuration
public class FilterConfig {

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authFilter);
        registration.setName(authFilter.toString());
        registration.setOrder(1);
        return registration;
    }

}
