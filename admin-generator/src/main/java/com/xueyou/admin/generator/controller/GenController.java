package com.xueyou.admin.generator.controller;

import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.generator.config.GenConfig;
import com.xueyou.admin.generator.model.TableColumn;
import com.xueyou.admin.generator.model.TableInfo;
import com.xueyou.admin.generator.service.GenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xueyou
 * @date 2020/12/28
 */
@Slf4j
@RestController
@RequestMapping("gen")
public class GenController {

    @Resource
    private GenService genService;

    /**
     * 获取表
     */
    @GetMapping("/tables")
    public Response<List<TableInfo>> tables() {
        return Response.ok(genService.queryTable());
    }

    /**
     * 生成代码
     */
    @GetMapping("/genCode/{tableName}")
    public String genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genService.generatorCode(tableName);
        genCode(response, data, tableName);
        return "ok";
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data, String tableName) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + tableName + ".zip");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

}
