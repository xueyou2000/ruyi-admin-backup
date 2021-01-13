package com.xueyou.admin.common.core.exception.file;

import com.xueyou.admin.common.core.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class FileException extends BaseException {

    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
