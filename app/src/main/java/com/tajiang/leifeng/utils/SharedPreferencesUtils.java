package com.tajiang.leifeng.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.tajiang.leifeng.model.Apartment;

/**
 * 对SharedPreference的使用做了建议的封装，对外公布出put，get，remove，clear等等方法；
 * 注意一点，里面所有的commit操作使用了SharedPreferencesCompat
 * .apply进行了替代，目的是尽可能的使用apply代替commit
 * 首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
 * 所以我们使用apply进行替代，apply异步的进行写入； 但是apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配；
 * SharedPreferencesCompat也可以给大家创建兼容类提供了一定的参考~~
 */
public class SharedPreferencesUtils {

	/** 测试用URL服务器地址 */
	public static final String HOST_TEST_ROOT_URL = "host_test_root_url";

	/** 记录当前CID上传的系统时间 */
	public static final String LAST_CID_UPLOAD_TIME = "last_cid_upload_time";

	/** 是否已成功收集用户ClientID及相关数据 */
	public static final String BOOLEAN_CID_COLLECTED = "boolean_cid_collected";

	/**  用户bindChannelId */
	public static final String CHANNEL_ID = "CHANNEL_ID";

	/** 保存在手机里面的文件名 */
	public static final String FILE_NAME = "share_data";
	
	/** 用户 - 信息 */
	public static final String USER_LOGIN_INFOR = "USER_LOGIN_INFOR";
	/** 用户 - 食堂 */
	public static final String USER_STORE = "USER_STORE";

	/** 用户 - 已选食堂ID */
	public static final String USER_STORE_ID = "USER_STORE_ID";
	/** 用户 - 已选楼栋名字 */
	public static final String USER_APARTMENT_NAME = "USER_APARTMENT_NAME";
	/** 用户 - 已选楼栋ID */
	public static final String USER_APARTMENT_ID = "USER_APARTMENT_ID";
	/** 用户 - 已选楼栋 */
	public static final String USER_APARTMENT = "USER_APARTMENT";

	//保存用户登录token
	public static final String USER_TOKENINFO = "USER_TOKENINFO";

	//保存登录用户名
	public static final String LOGIN_USER_NAME = "USER_LOGIN_NAME";

	//保存用户登录密码
	public static final String LOGIN_USER_PSW = "USER_LOGIN_PSW";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}