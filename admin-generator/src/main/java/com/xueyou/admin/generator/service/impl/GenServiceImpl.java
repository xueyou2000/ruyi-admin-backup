package com.xueyou.admin.generator.service.impl;

import com.xueyou.admin.generator.config.GenConfig;
import com.xueyou.admin.generator.mapper.MySqlMapper;
import com.xueyou.admin.generator.model.TableColumn;
import com.xueyou.admin.generator.model.TableInfo;
import com.xueyou.admin.generator.service.GenService;
import com.xueyou.admin.generator.utils.GenUtils;
import com.xueyou.admin.generator.utils.VelocityInitializer;
import com.xueyou.admin.generator.utils.VelocityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author xueyou
 * @date 2020/12/25
 */
@Slf4j
@Service
public class GenServiceImpl implements GenService {

    @Resource
    private MySqlMapper mySqlMapper;

    /**
     * 生成代码
     *
     * @param tableName 表名
     */
    @Override
    public byte[] generatorCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        return outputStream.toByteArray();
    }

    @Override
    public List<TableInfo> queryTable() {
        return mySqlMapper.queryTableList();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        TableInfo table = mySqlMapper.queryTableInfo(tableName);
        // 查询列信息
        List<TableColumn> columns = mySqlMapper.queryTableColumns(tableName);
        setPkColumn(table, columns);
        GenUtils.initColumnField(columns);

        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.setContentParams(table, columns);

        List<String> templates = VelocityUtils.getTemplateList();
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
//                log.info("template: {}, content: {}", template, sw.toString());
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }

    }

    /**
     * 设置主键列信息
     */
    private void setPkColumn(TableInfo tableInfo, List<TableColumn> columns) {
        for (TableColumn column : columns) {
            if ("PRI".equalsIgnoreCase(column.getColumnKey())) {
                tableInfo.setPk(column);
                column.setIsPk("1");
                break;
            }
        }
        if (tableInfo.getPk() == null) {
            tableInfo.setPk(columns.get(0));
        }
    }

}
