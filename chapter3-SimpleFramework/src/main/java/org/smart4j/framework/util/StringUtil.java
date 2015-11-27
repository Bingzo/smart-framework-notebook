package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by bing on 11/25/15.
 */
public final class StringUtil {
    public static boolean isEmpty(String str){
        if(str != null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
