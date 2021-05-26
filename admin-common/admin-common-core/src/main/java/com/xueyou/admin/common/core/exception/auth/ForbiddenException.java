package com.xueyou.admin.common.core.exception.auth;

import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;

/**
 * 权限不足
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 10:45 上午
 */
public class ForbiddenException extends BusinessRuntimeException {

    private static final long serialVersionUID = -4552488542483342775L;

    public ForbiddenException() {
        super("auth", "user.forbidden", null, null);
    }

}
