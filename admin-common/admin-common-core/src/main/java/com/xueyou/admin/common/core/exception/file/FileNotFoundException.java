package com.xueyou.admin.common.core.exception.file;

/**
 * 文件不存在异常类
 *
 * @author xueyou
 * @date 2020/12/24
 */
public class FileNotFoundException extends FileException {

    private static final long serialVersionUID = 1L;

    public FileNotFoundException(String fileName) {
        super("upload.file.notfound", new Object[]{fileName});
    }

}
