package com.xueyou.admin.generator.utils;

import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.spring.SpringUtils;
import com.xueyou.admin.generator.config.GenConfig;
import com.xueyou.admin.generator.constant.GenConstants;
import com.xueyou.admin.generator.model.TableColumn;
import com.xueyou.admin.generator.model.TableInfo;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.VelocityContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * @author xueyou
 * @date 2020/12/25
 */
public class VelocityUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /**
     * react基础路径
     */
    private static final String REACT_PATH = "src/pages";

    public static GenConfig getConfig() {
        return SpringUtils.getBean(GenConfig.class);
    }

    /**
     * 设置模板变量信息
     */
    public static VelocityContext setContentParams(TableInfo tableInfo, List<TableColumn> columns) {
        GenConfig config = getConfig();
        //表名转换成Java类名
        String className = columnToJava(tableInfo.getTableName());
        className = className.replace(config.getPrefix(), "");

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", tableInfo.getTableName());
        velocityContext.put("ClassName", className);
        velocityContext.put("className", StringUtils.uncapitalize(className));
        velocityContext.put("comments", tableInfo.getTableComment());
        velocityContext.put("pk", tableInfo.getPk());
        velocityContext.put("columns", columns);
        velocityContext.put("importList", getImportList(columns));
        velocityContext.put("author", config.getAuthor());
        velocityContext.put("packageName", config.getPackageName());
        velocityContext.put("moduleName", config.getModuleName());
        velocityContext.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return velocityContext;
    }

    /**
     * 获取模版文件
     */
    public static List<String> getTemplateList() {
        List<String> templates = new ArrayList<>();

        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/query-model.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");

        templates.add("vm/react/types.d.ts.vm");
        templates.add("vm/react/service.ts.vm");
        templates.add("vm/react/_mock.ts.vm");
        templates.add("vm/react/index.tsx.vm");
        templates.add("vm/react/add.tsx.vm");
        templates.add("vm/react/update.tsx.vm");
        templates.add("vm/react/zh.ts.vm");

        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableInfo tableInfo) {
        GenConfig config = getConfig();
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = config.getPackageName();
        // 大写类名
        String ClassName = columnToJava(tableInfo.getTableName());
        ClassName = ClassName.replace(config.getPrefix(), "");
        // 小写类名
        String className = StringUtils.uncapitalize(ClassName);

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + config.getModuleName();
        String reactPath = REACT_PATH + "/" + config.getModuleName() + "/" + className + "/";

        if (template.contains("domain.java.vm")) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, ClassName);
        } else if (template.contains("query-model.java.vm")) {
            fileName = StringUtils.format("{}/model/dto/{}Query.java", javaPath, ClassName);
        } else if (template.contains("mapper.java.vm")) {
            fileName = StringUtils.format("{}/mapper/{}Mapper.java", javaPath, ClassName);
        } else if (template.contains("service.java.vm")) {
            fileName = StringUtils.format("{}/service/{}Service.java", javaPath, ClassName);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, ClassName);
        } else if (template.contains("controller.java.vm")) {
            fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, ClassName);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StringUtils.format("{}/{}Mapper.xml", mybatisPath, ClassName);
        } else if (template.contains("types.d.ts.vm")) {
            fileName = StringUtils.format("{}/types.d.ts", reactPath);
        } else if (template.contains("service.ts.vm")) {
            fileName = StringUtils.format("{}/service.ts", reactPath);
        } else if (template.contains("_mock.ts.vm")) {
            fileName = StringUtils.format("{}/_mock.ts", reactPath);
        } else if (template.contains("index.tsx.vm")) {
            fileName = StringUtils.format("{}/index.tsx", reactPath);
        } else if (template.contains("add.tsx.vm")) {
            fileName = StringUtils.format("{}/Add{}Modal.tsx", reactPath, ClassName);
        } else if (template.contains("update.tsx.vm")) {
            fileName = StringUtils.format("{}/Update{}Modal.tsx", reactPath, ClassName);
        } else if (template.contains("zh.ts.vm")) {
            fileName = StringUtils.format("{}/locales/zh-CN.ts", reactPath, ClassName);
        }

        return fileName;
    }

    /**
     * 根据列类型获取导入的包
     */
    public static HashSet<String> getImportList(List<TableColumn> columns) {
        HashSet<String> importList = new HashSet<>();

        for (TableColumn column : columns) {
            if ("Date".equals(column.getDataType())) {
                importList.add("java.util.Date");
            } else if ("BigDecimal".equals(column.getDataType())) {
                importList.add("java.math.BigDecimal");
            } else if ("LocalDateTime".equals(column.getDataType())) {
                importList.add("java.time.LocalDateTime");
            } else if ("LocalDate".equals(column.getDataType())) {
                importList.add("java.time.LocalDate");
            } else if ("LocalTime".equals(column.getDataType())) {
                importList.add("java.time.LocalTime");
            }
        }

        return importList;
    }

    public static String columnTypeToJavaType(String columnType) {
        if (arraysContains(GenConstants.COLUMNTYPE_STR, columnType)) {
            return "String";
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, columnType)) {
            return "LocalDateTime";
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, columnType)) {
            // 如果是浮点型
            String[] str = StringUtils.split(StringUtils.substringBetween(columnType, "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                return "Double";
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                return "Integer";
            }
            // 长整形
            else {
                return "Long";
            }
        } else {
            throw new RuntimeException("不支持数据库的类型: " + columnType);
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

}
