package com.xueyou.admin.system.controller;

import com.xueyou.admin.common.core.exception.file.FileNotFoundException;
import com.xueyou.admin.common.core.utils.ToolUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author xueyou
 * @date 2020/12/24
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Api(value = "file", tags = "文件管理")
public class FileController {

    /**
     * 临时文件下载
     * @param fileName 文件名称
     */
    @ApiOperation(value = "临时文件下载",  httpMethod = "GET")
    @RequestMapping(value = "/tmp-download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    void download(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        try {
            // 输出响应正文的输出流
            OutputStream out;
            // 读取本地文件的输入流
            InputStream in;
            // 获得本地输入流
            File file = new File(ToolUtils.getDownloadPath() + fileName);
            in = new FileInputStream(file);
            // 设置响应正文的MIME类型
            response.setContentType("Content-Disposition;charset=GB2312");
            response.setHeader("Content-Disposition", "attachment;" + " filename="+ new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            // 把本地文件发送给客户端
            out = response.getOutputStream();
            int byteRead = 0;
            byte[] buffer = new byte[512];
            while ((byteRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteRead);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("临时文件下载失败: ", e);
            throw new FileNotFoundException(fileName);
        }
    }

}
