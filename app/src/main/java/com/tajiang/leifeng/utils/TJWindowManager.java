package com.tajiang.leifeng.utils;

import android.content.Context;

import com.tajiang.leifeng.application.TJApp;

/**
 * Created by Administrator on 2016/8/19.
 */
public class TJWindowManager {

    public static int getWindowWidth() {
        android.view.WindowManager wm = (android.view.WindowManager) TJApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

}
