package com.tx.platform.utils;

/**
 *  @ClassName SLLUtils
 *  @Description SLL防注入工具类
 *  @Author Hardy
 *  @Date 2018年12月11日 21:32
 *  @Version 1.0.0
 *  
 **/
public class SLLUtils {

    /**
     * 处理字符转义
     *
     * @param value
     * @return
     */
    public static String clearXss(String value) {
        if (value == null || "".equals(value)) {
            return value;
        }
        value = value.replaceAll("<", "<").replaceAll(">", ">");
        value = value.replaceAll("\\(", "(").replace("\\)", ")");
        value = value.replaceAll("'", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
                "\"\"");
        value = value.replace("script", "");
        return value;
    }

}
