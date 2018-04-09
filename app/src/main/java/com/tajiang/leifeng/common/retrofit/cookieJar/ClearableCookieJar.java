package com.tajiang.leifeng.common.retrofit.cookieJar;

import okhttp3.CookieJar;

/**
 * Created by Admins on 2016/12/27.
 */
public interface ClearableCookieJar extends CookieJar {

    void clear();
}

