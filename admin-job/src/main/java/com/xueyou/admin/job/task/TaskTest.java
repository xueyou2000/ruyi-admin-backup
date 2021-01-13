package com.xueyou.admin.job.task;

import com.xueyou.admin.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时任务测试
 *
 * @author xueyou
 * @date 2020/12/25
 */
@Slf4j
@Component("taskTest")
public class TaskTest {

    public void multipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void params(String params) {
        log.info("执行有参方法：" + params);
    }

    public void noParams() {
        log.info("执行无参方法");
    }

}
