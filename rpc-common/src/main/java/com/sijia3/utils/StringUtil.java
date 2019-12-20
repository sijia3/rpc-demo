package com.sijia3.utils;

import org.springframework.util.StringUtils;

/**
 * @author sijia3
 * @date 2019/12/19 14:26
 */
public class StringUtil {
    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static String[] split(String str, String separator){
        return org.apache.commons.lang3.StringUtils.split(str, separator);
    }

}
