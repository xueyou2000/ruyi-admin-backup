package com.xueyou.admin.system.log;

import com.xueyou.admin.system.log.listen.LogListener;
import com.xueyou.admin.system.service.LoginInfoService;
import com.xueyou.admin.system.service.OperLogService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 操作/登陆日志配置
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:11 下午
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    private final LoginInfoService loginInfoService;

    private final OperLogService operLogService;

    @Bean
    public LogListener sysOperLogListener() {
        return new LogListener(loginInfoService, operLogService);
    }

//    @Bean
//    public OperLogAspect operLogAspect() {
//        return new OperLogAspect();
//    }

}
