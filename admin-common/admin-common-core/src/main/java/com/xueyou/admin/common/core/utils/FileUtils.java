package com.xueyou.admin.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

/**
 * @author xueyou
 * @date 2020/12/24
 */
@Slf4j
public class FileUtils {

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 读取文件字节
     * @param file 文件
     * @return 字节
     */
    public static byte[] readFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    /**
     * 读取文件字节
     * @param filePath 文件路径
     * @return 字节
     */
    public static byte[] readFile(String filePath) throws IOException {
        return readFile(new File(filePath));
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    public static void removeFile(String filePath) throws IOException {
        org.apache.commons.io.FileUtils.forceDelete(new File(filePath));
    }

    /**
     * 压缩到指定宽度和高度
     * @param imageBytes 源图片字节数组
     * @param width 宽度
     * @param height 高度
     * @return 压缩后的字节
     */
    public static byte[] compressToSize(byte[] imageBytes, Integer width, Integer height) {
        if (imageBytes == null || imageBytes.length <= 0) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
            Thumbnails.Builder<? extends InputStream> buiuld = Thumbnails.of(inputStream);
            if (width != null) {

                buiuld.width(width);
            }
            if (height != null) {
                buiuld.height(height);
            }
            buiuld.toOutputStream(outputStream);
            imageBytes = outputStream.toByteArray();
            log.info("【图片压缩】图片原大小={}kb | 压缩后大小={}kb",
                    srcSize / 1024, imageBytes.length / 1024);
            return imageBytes;
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
            return imageBytes;
        }
    }


    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @param imageId     影像编号
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize, String imageId) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            log.info("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
                    imageId, srcSize / 1024, imageBytes.length / 1024);
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

}
