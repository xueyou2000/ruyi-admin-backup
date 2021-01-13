package com.xueyou.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 程序入口
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 10:38 上午
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AdminAuthApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(AdminAuthApplication.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");

        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running!\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "Swagger注解: \thttp://localhost" + ":" + port + "/swagger-ui.html\n" +
                "----------------------------------------------------------");
    }

}
