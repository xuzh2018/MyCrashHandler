package com.test.xzh.mylibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xzh on 2017/5/15
 */

public class TimeUtil {
    public static String getNowDateTime() {
        String format = "yyyy-MM-dd_hh-mm-ss";
        SimpleDateFormat sFormat = new SimpleDateFormat(format, Locale.CHINA);
        Date date = new Date();
        String nTime = sFormat.format(date);
        return nTime;

    }
}
