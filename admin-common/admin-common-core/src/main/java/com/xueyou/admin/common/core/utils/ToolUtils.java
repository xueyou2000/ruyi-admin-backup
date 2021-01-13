package com.xueyou.admin.common.core.utils;

import java.io.File;

/**
 * 高频方法集合类
 *
 * @author xueyou
 * @date 2020/12/23
 */
public class ToolUtils {

    /**
     * 获取临时目录
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取当前项目工作目录
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * 获取临时下载目录
     */
    public static String getDownloadPath() {
        return getTempPath() + File.separator + "download" + File.separator;
    }

}
