package com.tajiang.leifeng.utils;

import java.io.File;

import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

/**
 * 七牛帮助类
 * @author: lvhua
 *
 * @date:	2015年9月16日
 */

public class QiniuUtils {

	public static void upload(String token, String filePath, UpCompletionHandler handler) {
		UploadManager manager = new UploadManager();
		manager.put(filePath, null, token, handler, null);
	}
	
	public static void upload(String token, File file, UpCompletionHandler handler) {
		UploadManager manager = new UploadManager();
		manager.put(file, null, token, handler, null);
	}
	
	public static void upload(String token, byte[] bytes, UpCompletionHandler handler) {
		UploadManager manager = new UploadManager();
		manager.put(bytes, null, token, handler, null);
	}
}
