package com.xueyou.admin.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * 项目配置
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 11:18 上午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "admin-config")
public class AdminConfig {

    /**
     * 请求白名单地址
     */
    private String[] whiteList;

}
