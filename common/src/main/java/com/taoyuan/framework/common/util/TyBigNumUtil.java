package com.taoyuan.framework.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 大数处理
 */
public class TyBigNumUtil {

    /**
     * 转化为金融数据格式
     *
     * @param value
     * @return
     */
    public static String cvrtNum2String(Object value) {
        if(null== value){
            return null;
        }

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        return decimalFormat.format(value);
    }

    public static void main(String[] args) {
        System.out.println(cvrtNum2String(1353513413.2341234d));
        System.out.println(cvrtNum2String(1353513413d));
    }
}
