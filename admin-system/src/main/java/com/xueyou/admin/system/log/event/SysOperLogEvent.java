package com.xueyou.admin.system.log.event;

import com.xueyou.admin.system.domain.OperLog;
import org.springframework.context.ApplicationEvent;

/**
 * 操作日志事件
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:51 下午
 */
public class SysOperLogEvent extends ApplicationEvent {

    private static final long serialVersionUID = 8905017895058642111L;

    public SysOperLogEvent(OperLog source) {
        super(source);
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    @Override
    public OperLog getSource() {
        return (OperLog) super.getSource();
    }
}
