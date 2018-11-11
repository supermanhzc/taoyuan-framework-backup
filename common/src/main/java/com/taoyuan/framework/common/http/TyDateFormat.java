package com.taoyuan.framework.common.http;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期格式转换
 */
@Slf4j
public class TyDateFormat extends DateFormat {

    private DateFormat dateFormat;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TyDateFormat(DateFormat dateFormat) {
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        this.dateFormat = dateFormat;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        log.info("TyDateFormat format,input {}",date);
        return new StringBuffer(format.format(date));
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        log.info("TyDateFormat parse(String source, ParsePosition pos),input {}",source);
        Date date = null;
        try {
            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            date = format.parse(source, pos);
        } catch (Exception e) {
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    // 主要还是装饰这个方法
    @Override
    public Date parse(String source) throws ParseException {
        log.info("TyDateFormat parse(String source),input {}",source);
        Date date = null;
        try {
            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            date = format.parse(source);
        } catch (Exception e) {
            date = dateFormat.parse(source);
        }
        return date;
    }

    // 这里装饰clone方法的原因是因为clone方法在jackson中也有用到
    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new TyDateFormat((DateFormat) format);
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse("2018-11-06 15:27:43");
            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            System.out.println(format.format(date));
            System.out.println(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
