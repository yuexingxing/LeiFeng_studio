package com.tajiang.leifeng.utils;

import com.google.gson.Gson;

/**
 * gson对象采用单例模式
 * */
public class GsonObjUtil {
	private static Gson gson = null;

	private GsonObjUtil() {}

	public static Gson getGsonInstance() {
		if (gson == null) {
			synchronized (GsonObjUtil.class) {
				gson = new Gson();
			}
		}
		return gson;
	}
}
