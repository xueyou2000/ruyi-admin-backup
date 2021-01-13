package com.xueyou.admin.common.core.exception.file;

/**
 * 文件名称超长限制异常类
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class FileNameLengthLimitExceededException extends FileException {

    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
