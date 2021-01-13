package com.xueyou.admin.common.core.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:06 上午
 */
public class UserPasswordNotMatchException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }

}
