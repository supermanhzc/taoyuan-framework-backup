package com.taoyuan.framework.common.util;

import com.taoyuan.framework.common.constant.DateConsts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TyDateUtils {

    public static String convertDateToString(Date date){
        return convertDateToString(date, DateConsts.DEFAULT_DATE_FORMAT);
    }

    public static String convertDateToString(Date date, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date convertStringToDate(String date) throws ParseException {
        return convertStringToDate(date, DateConsts.DEFAULT_DATE_FORMAT);
    }

    public static Date convertStringToDate(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
