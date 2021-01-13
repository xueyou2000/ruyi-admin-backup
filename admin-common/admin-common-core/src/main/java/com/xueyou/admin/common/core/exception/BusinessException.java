package com.xueyou.admin.common.core.exception;

/**
 * 业务异常
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
