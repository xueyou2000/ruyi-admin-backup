package com.xueyou.admin.common.core.exception.base;

import com.xueyou.admin.common.core.utils.MessageUtils;
import com.xueyou.admin.common.core.utils.StringUtils;

/**
 * 基础异常
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class BaseException extends RuntimeException {

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

    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isBlank(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
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

}
