package com.taoyuan.framework.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 随机数工具类
 */
@Slf4j
public class TyRandomUtil {

    /**
     * 获取任意位数的随机数
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int randomNum;
        char randomChar;
        Random random = new Random();
        // StringBuffer类型的可以append增加字符
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            // 可生成[0,n)之间的整数，获得随机位置
            randomNum = random.nextInt(base.length());
            // 获得随机位置对应的字符
            randomChar = base.charAt(randomNum);
            // 组成一个随机字符串
            str.append(randomChar);
        }

        log.info("验证码:{}", str.toString());
        return str.toString();
    }

    /**
     * 获取任意位数的数字序列
     * @param unit
     * @return
     */
    public static String getRandomNum(int unit) {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int a = 0; a < unit; a++) {
            sb.append(rand.nextInt(10));
        }

        return sb.toString();
    }
}
