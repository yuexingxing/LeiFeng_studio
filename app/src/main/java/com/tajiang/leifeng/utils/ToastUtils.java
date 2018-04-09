package com.tajiang.leifeng.utils;

import com.tajiang.leifeng.application.TJApp;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/** 
 * Toast统一管理类 
 *  
 */ 
public class ToastUtils {
	
	private static Context context = TJApp.getContext();
	
	private ToastUtils()  
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  
  
    public static boolean isShow = true;  
  
    /** 
     * 短时间显示Toast 
     *  
     * @param message
     */  
    public static void showShort(CharSequence message)  
    {  
        if (isShow)  
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     * .setGravity(Gravity.CENTER, 0, 0)
     * @param message
     */
    public static void showShortInCenter(CharSequence message)
    {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /** 
     * 短时间显示Toast 
     *  
     * @param message
     */  
    public static void showShort( int message)  
    {  
        if (isShow)  
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();  
    }  
  
    /** 
     * 长时间显示Toast 
     *  
     * @param message
     */  
    public static void showLong(CharSequence message)  
    {  
        if (isShow)  
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();  
    }  
  
    /** 
     * 长时间显示Toast 
     *  
     * @param message
     */  
    public static void showLong(int message)  
    {  
        if (isShow)  
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();  
    }  
  
    /** 
     * 自定义显示Toast时间 
     *  
     * @param message
     * @param duration 
     */  
    public static void show(CharSequence message, int duration)  
    {  
        if (isShow)  
            Toast.makeText(context, message, duration).show();  
    }  
  
    /** 
     * 自定义显示Toast时间 
     *  
     * @param message
     * @param duration 
     */  
    public static void show( int message, int duration)  
    {  
        if (isShow)  
            Toast.makeText(context, message, duration).show();  
    }  

}
