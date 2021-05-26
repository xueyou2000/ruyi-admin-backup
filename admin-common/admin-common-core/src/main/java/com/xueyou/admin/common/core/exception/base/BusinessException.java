package com.xueyou.admin.common.core.exception.base;

import com.xueyou.admin.common.core.utils.MessageUtils;
import com.xueyou.admin.common.core.utils.StringUtils;

/**
 * 业务异常
 * @author xueyou
 * @date 2021/5/17
 */
public class BusinessException extends Exception {

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

    public BusinessException(String module, String code, Object[] args, String defaultMessage) {
        super(formatMessage(code, args, defaultMessage));
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BusinessException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BusinessException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BusinessException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BusinessException(String defaultMessage) {
        this(null, null, null, defaultMessage);
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
