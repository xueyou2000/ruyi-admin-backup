package com.xueyou.admin.common.core.exception.file;

import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;

/**
 * OSS信息异常类
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class OssException extends BusinessRuntimeException {

    private static final long serialVersionUID = 2146840966262730160L;

    public OssException(String msg) {
        super(msg);
    }
}