package com.xueyou.admin.system.log.event;

import com.xueyou.admin.system.domain.LoginInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 系统登陆日志事件
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:54 上午
 */
public class SysLogininforEvent extends ApplicationEvent {

    public SysLogininforEvent(LoginInfo source) {
        super(source);
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    @Override
    public LoginInfo getSource() {
        return (LoginInfo) super.getSource();
    }
}
