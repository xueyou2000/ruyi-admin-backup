package com.xueyou.admin.common.core.exception.user;

/**
 * 用户不存在异常类
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 10:59 上午
 */
public class UserNotExistsException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }

}
