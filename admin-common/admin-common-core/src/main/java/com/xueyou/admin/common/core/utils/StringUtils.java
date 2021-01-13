package com.xueyou.admin.common.core.utils;

import com.xueyou.admin.common.core.text.StrFormatter;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 字符串工具
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 12:35 下午
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char UNDERLINE = '_';

    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 替换参数
     *
     * @param text 源文本
     * @param params 参数
     * @return 替换后的文本
     */
    public static String replaceParams(String text, Map<String, Object> params) {
        if (params == null) {
            return  text;
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            text = text.replaceAll("#" + entry.getKey() + "#", entry.getValue().toString());
        }
        return text;
    }

    /**
     * 自增编号
     *
     * 比如传入: "C000001", 将自增为 "C000002"
     * @param prefix 编号前缀
     * @param maxCode 最大编号
     * @return 自增的编号
     */
    public static String increaseCode(String prefix, String maxCode) {
        if (org.springframework.util.StringUtils.isEmpty(maxCode)) {
            return prefix + "000001";
        }

        // 祛除标志
        String numStr = maxCode.replace(prefix, "");
        int numStrLen = numStr.length();
        int num = Integer.parseInt(numStr.replaceAll("[a-zA-Z]", "")) + 1;
        return prefix + padStart(String.valueOf(num), "0", numStrLen);
    }

    /**
     * 头部补齐
     *
     * padStart("x", 'ab', 5) => "ababx"
     * padStart("x", 'ab', 4) => "abax"
     * @param src   源字符串
     * @param fill  填充字符串
     * @param maxLen    最大补全长度
     */
    public static String padStart(String src, String fill, int maxLen) {
        if (src.length() > maxLen) {
            return src.substring(0, maxLen);
        }
        StringBuilder sb = new StringBuilder();

        while (sb.length() < maxLen - src.length()) {
            // 剩余补齐长度
            int fillLen = maxLen - (src.length() + sb.length());
            if (fill.length() <= fillLen) {
                sb.append(fill);
            } else {
                sb.append(fill, 0, fillLen);
            }
        }

        sb.append(src);
        return sb.toString();
    }

    /**
     * 生成随机用户名，数字和字母组成
     *
     * @param length 长度
     */
    public static String randomName(int length) {

        StringBuilder val = new StringBuilder();
        Random random = new Random();

        // 参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    /**
     * 字符串是否不为空
     *
     * @param str   字符串
     * @return true=不为空
     */
    public static boolean isNotBlank(String str) {
        return str != null && !org.springframework.util.StringUtils.isEmpty(str);
    }

    /**
     * 字符串是否为空
     *
     * @param str 字符串
     * @return true=为空
     */
    public static boolean isBlank(String str) { return org.springframework.util.StringUtils.isEmpty(str); }

    /**
     * 字符串是否包含
     *
     * @param fullStr   全字符串
     * @param partStr   被包含字符串
     */
    public static boolean contains(String fullStr, String partStr) {
        if (isBlank(fullStr) || isBlank(partStr)) {
            return false;
        }
        return fullStr.contains(partStr);
    }

    /**
     * 字符串是否包含
     *
     * @param fullStr   全字符串
     * @param partStr   被包含字符串
     */
    public static boolean contains(String fullStr, String partStr, boolean ignoreCase) {
        if (isBlank(fullStr) || isBlank(partStr)) {
            return false;
        }
        return ignoreCase ? fullStr.toLowerCase().contains(partStr.toLowerCase()) : fullStr.contains(partStr);
    }

    /**
     * 字符串是否相等
     *
     * @param path1 字符串a
     * @param path2 字符串b
     * @return  true=相等
     */
    public static boolean pathEquals(String path1, String path2) {
        return org.springframework.util.StringUtils.pathEquals(path1, path2);
    }

    /**
     * 查询字符串转map
     *
     * @param queryParams 查询字符串, 比如: successcode=-1&Ref=&
     */
    public static Map<String, String> queryParamsToMap(String queryParams) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(queryParams)) {
            String[] segments = queryParams.split("&");
            for (String segment : segments) {
                // 分割 successcode=-1 这种
                String[] fieldInfo = segment.split("=");
                if (fieldInfo.length == 2) {
                    map.put(fieldInfo[0], fieldInfo[1]);
                }
            }
        }
        return map;
    }

    /**
     * 字符串转字节数组
     * @param string    字符串
     * @param charset   编码
     */
    public static byte[] serialize(String string, Charset charset) {
        return string == null ? null : string.getBytes(charset);
    }

    /**
     * 字节数组转字符串
     * @param bytes 字节数组
     * @param charset   编码
     */
    public static String deserialize(byte[] bytes, Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }

    /**
     * 生成随机数字
     *
     * @param num   数字长度
     */
    public static String randomNum(int num){
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for(int i=0;i<num;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param param 字符串
     * @param charType 转换类型, 2=转大写, 否则转小写
     */
    public static String camelToUnderline(String param, Integer charType) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
            }
            if (charType == 2) {
                sb.append(Character.toUpperCase(c));  //统一都转大写
            } else {
                sb.append(Character.toLowerCase(c));  //统一都转小写
            }


        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String camelToUnderline(String param) {
        return camelToUnderline(param, 1);
    }


    /**
     * 下划线转驼峰
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                flag = true;
                //标志设置为true,跳过
            } else {
                if (flag) {
                    //表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(param.charAt(i)));
                    flag = false;  //重置标识
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        return sb.toString();
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }


    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
