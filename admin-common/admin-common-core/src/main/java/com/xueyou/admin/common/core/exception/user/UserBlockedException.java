package com.xueyou.admin.common.core.exception.user;

/**
 * 用户锁定异常类
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:09 上午
 */
public class UserBlockedException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserBlockedException() {
        super("user.blocked", null);
    }

}
