package com.xueyou.admin.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 生成配置
 *
 * @author xueyou
 * @date 2020/12/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "gen")
public class GenConfig {

    /**
     * 作者
     */
    public String author;

    /**
     * 生成包路径
     */
    public String packageName;

    /**
     * 模块名称, 比如  system
     */
    public String moduleName;

    /**
     * 前缀, 比如 sys_oss表, 生成的文件不想SysOss, 则可以设置为 Sys
     */
    public String prefix;

}
