package com.xueyou.admin.common.core.exception.auth;

/**
 * 权限不足
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 10:45 上午
 */
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -4552488542483342775L;

    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException() {
        super("权限不足");
    }

}
