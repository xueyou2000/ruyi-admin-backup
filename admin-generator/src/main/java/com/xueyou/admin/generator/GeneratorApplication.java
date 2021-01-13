package com.xueyou.admin.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成入口
 *
 * @author xueyou
 * @date 2020/12/25
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.xueyou.admin")
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }

}
