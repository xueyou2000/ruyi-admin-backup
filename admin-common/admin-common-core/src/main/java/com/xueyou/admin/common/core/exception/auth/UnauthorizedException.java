package com.xueyou.admin.common.core.exception.auth;

import org.springframework.http.HttpStatus;

/**
 * 授权异常
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 10:44 上午
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 3885400551304383736L;

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

}
