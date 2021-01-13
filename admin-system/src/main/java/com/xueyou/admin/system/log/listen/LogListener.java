package com.xueyou.admin.system.log.listen;

import com.xueyou.admin.system.domain.LoginInfo;
import com.xueyou.admin.system.domain.OperLog;
import com.xueyou.admin.system.log.event.SysLogininforEvent;
import com.xueyou.admin.system.log.event.SysOperLogEvent;
import com.xueyou.admin.system.service.LoginInfoService;
import com.xueyou.admin.system.service.OperLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

/**
 * 日志监听事件
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 12:01 下午
 */
@Slf4j
@AllArgsConstructor
public class LogListener {

    @Autowired
    private final LoginInfoService loginInfoService;

    @Autowired
    private final OperLogService operLogService;

    @Async
    @Order
    @EventListener(SysOperLogEvent.class)
    public void listenOperLog(SysOperLogEvent event) {
        OperLog operLog = (OperLog) event.getSource();
        operLogService.save(operLog);
        log.info("操作日志记录：{}", operLog);
    }


    @Async
    @Order
    @EventListener(SysLogininforEvent.class)
    public void  listenLoginInfo(SysLogininforEvent event) {
        LoginInfo loginInfo = (LoginInfo) event.getSource();
        loginInfo.setLoginTime(LocalDateTime.now());

        loginInfoService.save(loginInfo);
        log.info("登陆日志记录: [{}]", loginInfo);
    }


}
