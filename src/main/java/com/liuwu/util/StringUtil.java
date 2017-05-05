package com.liuwu.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: StringUtil
 * @Package com.liuwu.util
 * @Description:字符串工具类
 * @author: liuwu1189@dingtalk.com
 * @date: 2017-05-04 15:40:30
 */
public class StringUtil {

    public static String stackTrace(Throwable t) {
        if (t == null) {
            return null;
        }
        StringWriter errors = new StringWriter();
        t.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }


}
