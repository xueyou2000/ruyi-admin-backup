package com.xueyou.admin.common.core.exception.auth;

import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * 授权异常
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 10:44 上午
 */
public class UnauthorizedException extends BusinessRuntimeException {

    private static final long serialVersionUID = 3885400551304383736L;

    public UnauthorizedException() {
        super("auth", "user.unauthorized", null, null);
    }

}
