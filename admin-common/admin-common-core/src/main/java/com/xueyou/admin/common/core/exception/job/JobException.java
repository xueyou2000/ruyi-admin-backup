package com.xueyou.admin.common.core.exception.job;

import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;

/**
 * 定时任务异常类
 *
 * @author xueyou
 * @date 2020/12/24
 */
public class JobException extends BusinessRuntimeException {

    private static final long serialVersionUID = 1L;

    public JobException(String code, Object[] args) {
        super("job", code, args, null);
    }

}
