package com.xueyou.admin.common.core.exception.file;

/**
 * 文件名大小限制异常类
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 9:21 上午
 */
public class FileSizeLimitExceededException extends FileException {

    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }

}
