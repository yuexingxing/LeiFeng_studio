package com.tajiang.leifeng.utils;

/**
 * Created by Admins on 2016/11/22.
 */
public class TJTextUtil {

    /**
     * 过滤回车和空格符
     * @return
     */
    public static String replaceSpaceAndReturn(String sequence) {
        sequence = sequence.replaceAll("(\r\n|\r|\n|\n\r)", "");
        return sequence;
    }

}


