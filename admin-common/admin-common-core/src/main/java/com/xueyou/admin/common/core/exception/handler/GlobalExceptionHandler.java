package com.xueyou.admin.common.core.exception.handler;

import com.xueyou.admin.common.core.exception.auth.ForbiddenException;
import com.xueyou.admin.common.core.exception.auth.UnauthorizedException;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:51 上午
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public Response<?> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Response.error(HttpStatus.METHOD_NOT_ALLOWED.value(),"不支持 " + e.getMethod() + " 请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Response<?> notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return Response.error(999, StringUtils.isBlank(e.getMessage()) ? "未知异常" : e.getMessage());
    }

    /**
     *  拦截未知的异常
     */
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e) throws Exception {
        log.error(e.getMessage(), e);
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，请联系管理员");
    }


    /**
     * 拦截参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleArgumentException(IllegalArgumentException e) {
        log.error("参数异常:", e);
        return Response.error(800, "参数错误");
    }

    /**
     *  拦截数据库的异常
     */
    @ExceptionHandler(DataAccessException.class)
    public Response<?> handleException(DataAccessException e) {
        log.error("数据库异常:", e);
        return Response.error(HttpStatus.BAD_GATEWAY.value(), "请求失败，请联系管理员");
    }


    /**
     * 未授权
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Response<?> handleUnauthorizedException(UnauthorizedException e) {
        return Response.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    /**
     * 权限不足
     */
    @ExceptionHandler(ForbiddenException.class)
    public Response<?> handleForbiddenException(ForbiddenException e) {
        return Response.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

}
