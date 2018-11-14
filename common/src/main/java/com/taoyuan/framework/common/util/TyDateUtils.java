package com.taoyuan.framework.common.util;

import com.taoyuan.framework.common.constant.DateConsts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TyDateUtils {

    public static String convertDateToString(Date date) {
        return convertDateToString(date, DateConsts.DEFAULT_DATE_FORMAT);
    }

    public static String convertDateToString(Date date, String format) {
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

    /**
     * 获取N秒之后的时间
     * @param amount
     * @return
     */
    public static Date getDate(int amount){
        Date now = new Date();

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, amount);
        return nowTime.getTime();
    }

    /**
     * 获取N秒之后的时间
     * @param amount
     * @return
     */
    public static String getDateAfterSeconds(int amount){
        SimpleDateFormat sdf = new SimpleDateFormat(DateConsts.DEFAULT_DATE_FORMAT);
        return sdf.format(getDate(amount));
    }

    /**
     * 获取cron格式的时间
     * @param interval
     * @return
     */
    public static String getCronAfterSeconds(int interval){
        SimpleDateFormat sdf = new SimpleDateFormat(DateConsts.DEFAULT_DATE_FORMAT);
        Date now = new Date();

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, 5);
        return sdf.format(nowTime.getTime());
    }

    public static String convertDateToCron(Date date) {
        if (null == date) {
            return null;
        }
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

}
