package com.sdkkit.gameplatform.statistic.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: TimeUtil
 * @Description: 日期时间处理类
 * @author <xingyong>xingyong@cy2009.com
 * @date 2012-7-12 下午2:48:01
 */
public class TimeUtil {
	
	/**
	 * 取得当前时间戳(单位：秒)
	 * @return
	 */
	public static long getTimeStamp(){
		return new Date().getTime()/1000;
	}
	/**
	 * 取得当前时间戳(单位：秒)
	 * @return
	 */
	public static long getFullTime(){
		return new Date().getTime();
	}
	
	/**
	 * 获取年月日时分秒格式时间，YMDHM为Year,Month,Day,Hour,Minute缩写
	 * @param time 必须是秒数，如果是毫秒请先除以1000
	 * @return
	 */
	public static String getYMDHMTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date(time * 1000L));
	}
	
	/**
	 * 获取月日时分格式时间，YMDHM为Year,Month,Day,Hour,Minute缩写
	 * @param time 必须是秒数，如果是毫秒请先除以1000
	 * @return
	 */
	public static String getMDHMTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return sdf.format(new Date(time * 1000L));
	}
	
	/**
	 * 获取年月日格式时间，YMD为Year,Month,Day缩写
	 * @param time 必须是秒数，如果是毫秒请先除以1000
	 * @return
	 */
	public static String getYMDTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(time * 1000L));
	}
	
	/**
	 * 判断时间是否是今年的时间
	 * @param time 必须是秒数，如果是毫秒请先除以1000
	 * @return
	 */
	public static boolean isThisYear(long time) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.setTimeInMillis(time * 1000L);
		int year2 = cal.get(Calendar.YEAR);
		if (year2 >= year && year2 < year + 1) {
			return true;
		}
		return false;
	}

	public static String getStrTime(int cc_time, String Type) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat(Type);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	// 0为月 1为日
	public static int getDayorMonth(int time, int dayormonth) {
		String mmdd = getStrTime(time, "MM-dd");
		String[] str = mmdd.split("-");
		String day = str[dayormonth];
		if (day.startsWith("0")) {
			return Integer.parseInt(String.valueOf(day.charAt(1)));
		} else {
			return Integer.parseInt(day);
		}
	}
}
