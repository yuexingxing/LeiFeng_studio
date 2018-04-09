package com.tajiang.leifeng.utils;

/**
 * Created by Admins on 2016/11/2.
 */
public class NumberUtils {

    /**
     * 末尾清零
     * @param object
     * @return
     */
    public static String clearTailZero(Object object) {
        if (object == null) {
            return "";
        }
        String number = object.toString();
        if(number.indexOf(".") > 0){
            //正则表达
            number = number.replaceAll("0+?$", "");//去掉后面无用的零
            number = number.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return number;
    }

}
