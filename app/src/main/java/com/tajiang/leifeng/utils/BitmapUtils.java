package com.tajiang.leifeng.utils;

import java.io.IOException;

import com.sina.weibo.sdk.utils.LogUtil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class BitmapUtils {

	/**
	 * 压缩资源文件中的图片
	 * 
	 * @param res
	 *            一般直接使用getResource();
	 * @param resId
	 *            对应的资源Id
	 * @param reqWidth
	 *            最低要求的宽度，加载后会大于等于这个值
	 * @param reqHeight
	 *            最低要求的高度，加载后会大于等于这个值
	 * @return 压缩后的Bitmap
	 */
	public static Bitmap decodeBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 压缩对应路径的图片
	 * 
	 * @param pathName
	 *            对应文件路径的值
	 * @param reqWidth
	 *            最低要求的宽度，加载后会大于等于这个值
	 * @param reqHeight
	 *            最低要求的高度，加载后会大于等于这个值
	 * @return 压缩后的Bitmap
	 */
	public static Bitmap decodeBitmapFromPath(String pathName, int reqWidth,
			int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		return BitmapFactory.decodeFile(pathName, options);
	}

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
     * 读取图片的旋转的角度
     * 
     * @param path 图片绝对路径
     * @return 图片的旋转角度:0 or 90 or 180 or 270
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     * 
     * @param bm 需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
    	if(bm == null){
    		return null;
    	}
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        
        
        
        try {
        	if(!bm.isRecycled()){
	            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
	            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        	}
        } catch (Throwable th) {
        	LogUtil.d("BitmapUtils", "rotateBitmapByDegree rotate bitmap errors.");
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
}
