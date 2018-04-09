package com.tajiang.leifeng.utils;

import android.annotation.SuppressLint;

import com.tajiang.leifeng.application.TJApp;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

	static final String formatPattern = "yyyy-MM-dd";

	static final String formatPattern_Short = "yyyyMMdd";
	
	public static String getYMDHMDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}

	public static String getYMDDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(time));
	}

	public static String getMSDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		return format.format(new Date(time));
	}

	public static String getDate(long time , String timeForm){
		SimpleDateFormat format = new SimpleDateFormat(timeForm);
		return format.format(new Date(time));
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(new Date());
	}

	/**
	 * 获取当前日期
	 *
	 * @return
	 */
	public static String getCurrentDate2() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 获取制定毫秒数之前的日期
	 * 
	 * @param timeDiff
	 * @return
	 */
	public static String getDesignatedDate(long timeDiff) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		long nowTime = System.currentTimeMillis();
		long designTime = nowTime - timeDiff;
		return format.format(designTime);
	}

	/**
	 * 
	 * 获取前几天的日期
	 */
	public static String getPrefixDate(String count) {
		Calendar cal = Calendar.getInstance();
		int day = 0 - Integer.parseInt(count);
		cal.add(Calendar.DATE, day); // int amount 代表天数
		Date datNew = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(datNew);
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}

	/**
	 * 字符串转换日期
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		// str = " 2008-07-10 19:20:00 " 格式
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		if (!str.equals("") && str != null) {
			try {
				return format.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .
	public void timeSubtract() {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = null;
		Date end = null;
		try {
			begin = dfs.parse("2004-01-02 11:30:24");
			end = dfs.parse("2004-03-26 13:31:40");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60;
		System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
	}

	/**
	 * 获取相差的天数  2016-09-07
	 */
	public static long getDayBetween2DateInMills(long currentTimeMills, long lastTimeMills) {
		long delta = Math.abs(currentTimeMills - lastTimeMills);
		long day = delta / (1000*3600*24);
		return day;
	}

	/**
	 * 与当前时间比较早晚
	 *
	 * @param time
	 *            需要比较的时间
	 * @return 输入的时间比现在时间晚则返回true
	 */
	public static boolean compareNowTime(String time) {
		boolean isDayu = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date parse = dateFormat.parse(time);
			Date parse1 = dateFormat.parse(getCurrentDate2());

			long diff = parse1.getTime() - parse.getTime();
			if (diff <= 0) {
				isDayu = true;
			} else {
				isDayu = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isDayu;
	}

	/**
	 * 获取当前时间精确到毫秒
	 *
	 * @return
	 */
	static String strLastTime;
	public static String initDataTime() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 精确到毫秒
		String suffix = fmt.format(new Date());

		strLastTime = TJApp.getIns().sendTime;

		boolean flag = strLastTime.equals(suffix);
		while (flag == true) {

			suffix = fmt.format(new Date());
			strLastTime = TJApp.getIns().sendTime;
			flag = strLastTime.equals(suffix);

			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return suffix;
	}

	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType){
		Date date = null; // String类型转成date类型
		try {
			date = stringToDate(strTime, formatType);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}
}