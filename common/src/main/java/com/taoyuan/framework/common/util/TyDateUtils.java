package com.taoyuan.framework.common.util;

import com.taoyuan.framework.common.constant.DateConsts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
     * 获取amount*unit秒之后的时间
     * @param unit
     * @param amount
     * @return
     */
    public static Date getDate(int unit,int amount){
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(unit, amount);
        return nowTime.getTime();
    }

    /**
     * 获取N秒之后的时间
     * @param amount
     * @return
     */
    public static Date getDateAfterSeconds(int amount){
        return getDate(Calendar.SECOND,amount);
    }

    public static Date getDateAfterMinutes(int amount){
        return getDate(Calendar.MINUTE,amount);
    }

    /**
     * 获取cron格式的时间
     * @param amount
     * @return
     */
    public static String getCronAfterSeconds(int amount){
        return convertDateToCron(getDateAfterSeconds(amount));
    }

    /**
     * 转换日期为cron格式
     * @param amount
     * @return
     */
    public static String getCronAfterMinutes(int amount){
        return convertDateToCron(getDateAfterMinutes(amount));
    }

    /**
     * 转换日期为cron格式
     * @param date
     * @return
     */
    public static String convertDateToCron(Date date) {
        if (null == date) {
            return null;
        }
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static Date getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getYesterdayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYesterdayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static LocalDate getYesterdayDate() {
        return LocalDate.now().minusDays(1L);
    }

    public static void main(String[] args) {
        System.out.println(getYesterdayDate());
        System.out.println(getYesterdayStartTime());
        System.out.println(getYesterdayEndTime());
    }
}
