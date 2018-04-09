package com.tajiang.leifeng.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

/** 
 * 文件相关操作
 * 
 * @author yxx
 *
 * @date 2015-12-17 下午5:03:10
 * 
 */
public class FileUtils {

	public static final String SDPATH = Environment.getExternalStorageDirectory() + "/XuLan/";//图片存储路径

	/**
	 * 判断手机是否有SD卡。
	 * 
	 * @return 有SD卡返回true，没有返回false。
	 */
	public static boolean existSDCard() {

		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public boolean delAllFile(String path) {

		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}else{
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
				delFolder(path+"/"+ tempList[i]);//再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 * @param folderPath 文件夹完整绝对路径
	 * @return
	 */
	public void delFolder(String folderPath) {

		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		}catch (Exception e) {
			Log.v("zd", "删除文件夹操作出错");
		}
	}

	public static String saveBitmap2(Bitmap bm, String picName) {

		File file = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			String date =  sdf.format(new Date(System.currentTimeMillis()));
			String strFolder = SDPATH + date;

			File folder = new File(strFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			file = new File(folder.getAbsolutePath(), picName + ".JPEG");

			FileOutputStream out = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {

		File file = new File(Environment.getExternalStorageDirectory(), "file.txt");//根目录下
		//		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {

			System.out.println("以行为单位读取文件内容，一次读一整行：");

			//中文乱码处理，UTF-8与GB2312
			//reader = new BufferedReader(new FileReader(file));
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);

			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				sb.append(tempString);
				line++;
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return sb.toString();
	}

}
