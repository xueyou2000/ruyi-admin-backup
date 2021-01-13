package com.xueyou.admin.common.core.exception.user;

import com.xueyou.admin.common.core.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 10:57 上午
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
