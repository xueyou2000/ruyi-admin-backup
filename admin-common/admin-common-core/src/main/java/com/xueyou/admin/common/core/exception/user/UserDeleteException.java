package com.xueyou.admin.common.core.exception.user;

/**
 * 用户账号已被删除
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:07 上午
 */
public class UserDeleteException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserDeleteException() {
        super("user.deleted", null);
    }

}
