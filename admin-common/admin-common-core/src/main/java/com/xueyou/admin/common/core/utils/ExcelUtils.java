package com.xueyou.admin.common.core.utils;

import com.xueyou.admin.common.core.annotation.Excel;
import com.xueyou.admin.common.core.enums.BaseEnum;
import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;
import com.xueyou.admin.common.core.text.Convert;
import com.xueyou.admin.common.core.entity.Dict;
import com.xueyou.admin.common.core.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * Excel相关处理
 *
 * @author xueyou
 * @date 2020/12/23
 */
@Slf4j
public class ExcelUtils<T> {

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Excel.Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Field> fields;

    /**
     * 实体对象
     */
    public Class<T> clazz;

    public ExcelUtils(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, Excel.Type type) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = new ArrayList<>();
        Class<?> tempClass = clazz;
        List<Field> tempFields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        while (tempClass != null) {
            tempClass = tempClass.getSuperclass();
            if (tempClass != null) {
                tempFields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            }
        }
        putToFields(tempFields);
    }

    /**
     * 放到字段集合中
     */
    private void putToFields(List<Field> fields) {
        for (Field field : fields) {
            Excel attr = field.getAnnotation(Excel.class);
            if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type)) {
                this.fields.add(field);
            }
        }
        this.fields.sort(Comparator.comparing(field -> {
            Excel attr = field.getAnnotation(Excel.class);
            return attr.order();
        }));
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * 创建工作表
     *
     * @param sheetNo             sheet数量
     * @param index               序号
     */
    public void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        // 设置工作表的名称.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is) throws Exception {
        return importExcel("", is);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param is     输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is) throws Exception {
        return importExcel(sheetName, is, new HashMap<>());
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param is     输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is, Map<String, List<Dict>> dictData) throws Exception {
        this.type = Excel.Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        Sheet sheet = null;
        if (StringUtils.isNotBlank(sheetName)) {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        } else {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }
        if (sheet == null) {
            throw new IOException("文件sheet不存在");
        }
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows > 0) {
            // 默认序号
            int serialNum = 0;
            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<>();
            for (Field field : allFields) {
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type)) {
                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);
                    fieldsMap.put(++serialNum, field);
                }
            }
            for (int i = 1; i < rows; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                T entity = null;
                for (int column = 0; column < serialNum; column++) {
                    Object val = this.getCellValue(row, column);
                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(column + 1);
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0")) {
                            val = StringUtils.substringBefore(s, ".0");
                        } else {
                            val = Convert.toStr(val);
                        }
                    } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                        val = Convert.toInt(val);
                    } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                        val = Convert.toLong(val);
                    } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                        val = Convert.toDouble(val);
                    } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        val = Convert.toBigDecimal(val);
                    } else if (Date.class == fieldType) {
                        if (val instanceof String) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    } else if (LocalDateTime.class == fieldType) {
                        if (val instanceof String) {
                            val = LocalDateTime.parse((String)val);
                        }
                    } else if (LocalDate.class == fieldType) {
                        if (val instanceof String) {
                            val = LocalDate.parse((String)val);
                        }
                    } else if (LocalTime.class == fieldType) {
                        if (val instanceof String) {
                            val = LocalTime.parse((String)val);
                        }
                    }
                    if (StringUtils.isNotNull(fieldType)) {
                        Excel attr = field.getAnnotation(Excel.class);
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr())) {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        } else if (StringUtils.isNotEmpty(attr.dictType())) {
                            val = getDictValue(dictData, attr.dictType(), String.valueOf(val));
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 临时文件名称
     */
    public String exportExcel(List<T> list, String sheetName) {
        this.init(list, sheetName, Excel.Type.EXPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 临时文件名称
     */
    public String exportExcel(List<T> list, String sheetName, Map<String, List<Dict>> dictData) {
        this.init(list, sheetName, Excel.Type.EXPORT);
        return exportExcel(dictData);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 临时文件名称
     */
    public String exportExcel() {
        return exportExcel(new HashMap<>());
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 临时文件名称
     */
    public String exportExcel(Map<String, List<Dict>> dictData) {
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);
                Cell cell = null; // 产生单元格
                // 产生一行
                Row row = sheet.createRow(0);
                // 写入各个字段的列头名称
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    Excel attr = field.getAnnotation(Excel.class);
                    // 创建列
                    cell = row.createCell(i);
                    CellStyle cellStyle = wb.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    if (attr.name().contains("注：")) {
                        Font font = wb.createFont();
                        font.setColor(HSSFFont.COLOR_RED);
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
                        sheet.setColumnWidth(i, 6000);
                    } else {
                        Font font = wb.createFont();
                        // 粗体显示
                        font.setBold(true);
                        // 选择需要用到的字体格式
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
                        // 设置列宽
                        sheet.setColumnWidth(i, (int) ((attr.width() + 0.72) * 256));
                        row.setHeight((short) (attr.height() * 20));
                    }
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setWrapText(true);
                    cell.setCellStyle(cellStyle);
                    // 写入列名
                    // 存在国际化列名，则常识国际化
                    cell.setCellValue(MessageUtils.message(attr.langCode(), null, attr.name()));

                    // 如果设置了提示信息则鼠标放上去提示.
                    if (StringUtils.isNotEmpty(attr.prompt())) {
                        // 这里默认设了2-101列提示.
                        setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, i, i);
                    }
                    // 如果设置了combo属性则本列只能选择不能输入
                    if (attr.combo().length > 0) {
                        // 这里默认设了2-101列只能选择不能输入.
                        setXSSFValidation(sheet, attr.combo(), 1, 100, i, i);
                    }
                }
                if (Excel.Type.EXPORT.equals(type)) {
                    fillExcelData(index, row, cell, dictData);
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return filename;
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessRuntimeException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row   单元格行
     * @param cell  类型单元格
     */
    public void fillExcelData(int index, Row row, Cell cell, Map<String, List<Dict>> dictData) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        // 写入各条记录,每条记录对应excel表中的一行
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = startNo; i < endNo; i++) {
            row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = (T) list.get(i);
            for (int j = 0; j < fields.size(); j++) {
                // 获得field.
                Field field = fields.get(j);
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                Excel attr = field.getAnnotation(Excel.class);
                try {
                    // 设置行高
                    row.setHeight((short) (attr.height() * 20));
                    // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                    if (attr.isExport()) {
                        // 创建cell
                        cell = row.createCell(j);
                        cell.setCellStyle(cs);
                        if (vo == null) {
                            // 如果数据存在就填入,不存在填入空格.
                            cell.setCellValue("");
                            continue;
                        }
                        // 用于读取对象中的属性
                        Object value = getTargetValue(vo, field, attr);
                        String dateFormat = attr.dateFormat();
                        String dictType = attr.dictType();
                        if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                            if (LocalDateTime.class == field.getType()) {
                                cell.setCellValue(((LocalDateTime)value).format(DateTimeFormatter.ofPattern(dateFormat)));
                            } else if (LocalDate.class == field.getType()) {
                                cell.setCellValue(((LocalDate)value).format(DateTimeFormatter.ofPattern(dateFormat)));
                            } else if (LocalTime.class == field.getType()) {
                                cell.setCellValue(((LocalTime)value).format(DateTimeFormatter.ofPattern(dateFormat)));
                            } else if (Date.class == field.getType()) {
                                cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                            }
                        } else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
                            cell.setCellValue(getDictLabel(dictData, dictType, String.valueOf(value)));
                        } else if (field.getType() == Double.class && StringUtils.isNotNull(value)) {
                            cell.setCellValue(Double.parseDouble(value + ""));
                        }  else if (field.getType().isInstance(BaseEnum.class)) {
                            // BaseEnum枚举， 尝试国际化
                            BaseEnum val = (BaseEnum) value;
                            cell.setCellValue(MessageUtils.message(val.getLangCode(), null, val.getDesc()));
                        } else {
                            // 如果数据存在就填入,不存在填入空格.
                            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
                        }
                    }
                } catch (Exception e) {
                    log.error("导出Excel失败: ", e);
                }
            }
        }
    }

    /**
     * 获取bean中的属性值
     *
     * @param vo    实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }


    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return null;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    val = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel
                        // 日期格式转换
                    } else {
                        if ((Double) val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet         表单
     * @param promptTitle   提示标题
     * @param promptContent 提示内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename) {
        filename = filename + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename) {
        String downloadPath = ToolUtils.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * 获取导出实体所需的字典类型
     */
    public String[] getDictTypes() {
        List<String> types = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // 排除静态字段
            if ((field.getModifiers() & 0x8) != 0) {
                continue;
            }
            // 设置字段可见
            field.setAccessible(true);
            Excel excelField = field.getAnnotation(Excel.class);
            if (excelField != null && StringUtils.isNotBlank(excelField.dictType())) {
                types.add(excelField.dictType());
            }
        }
        return types.toArray(new String[0]);
    }

    /**
     * 获取字典值
     *
     * @param dictData  字典数据map
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     */
    public static String getDictValue(Map<String, List<Dict>> dictData, String dictType, String dictLabel) {
        String result = "";

        if (dictData.containsKey(dictType)) {
            List<Dict> dicts = dictData.get(dictType);
            for (Dict dict : dicts) {
                if (StringUtils.equals(dict.getDictLabel(), dictLabel)) {
                    return dict.getDictValue();
                }
            }
        }

        return result;
    }

    /**
     * 获取字典标签
     *
     * @param dictData  字典数据map
     * @param dictType  字典类型
     * @param dictValue 字典值
     */
    public static String getDictLabel(Map<String, List<Dict>> dictData, String dictType, String dictValue) {
        String result = "";

        if (dictData.containsKey(dictType)) {
            List<Dict> dicts = dictData.get(dictType);
            for (Dict dict : dicts) {
                if (StringUtils.equals(dict.getDictValue(), dictValue)) {
                    return dict.getDictLabel();
                }
            }
        }

        return result;
    }

}