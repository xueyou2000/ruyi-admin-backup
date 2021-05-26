package com.xueyou.admin.common.core.exception.base;

import com.xueyou.admin.common.core.utils.MessageUtils;
import com.xueyou.admin.common.core.utils.StringUtils;

/**
 * 业务运行时异常
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class BusinessRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BusinessRuntimeException(String module, String code, Object[] args, String defaultMessage) {
        super(formatMessage(code, args, defaultMessage));
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BusinessRuntimeException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BusinessRuntimeException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BusinessRuntimeException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BusinessRuntimeException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    public BusinessRuntimeException() {
        this(null, "commom.error", null, "系统异常");
    }

    public BusinessRuntimeException(Exception e) {
        super(StringUtils.isBlank(e.getMessage()) ? "系统异常" : e.getMessage());
        if (e instanceof BusinessException || e instanceof BusinessRuntimeException) {
            this.defaultMessage = e.getMessage();
        } else {
            this.code = "commom.error";
            this.defaultMessage = "系统异常";
        }
    }

    @Override
    public String getMessage() {
        return formatMessage(code, args, defaultMessage);
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    private static String formatMessage(String code, Object[] args, String defaultMessage) {
        String message = null;
        if (StringUtils.isNotBlank(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }
}
