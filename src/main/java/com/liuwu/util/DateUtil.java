package com.liuwu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Title DateUtil.java
 * @Package com.utils
 * @Description 时间工具类【new】
 * @author liuwu_eva@163.com
 * @date 2015-9-6 上午11:14:20
 */
public class DateUtil {
//	public static void main(String[] args) {
//		System.out.println(utc2Local("2015-09-16T00:03:00.99"));
//	}
	
	/**
	 * 从calendar时间格式==>得到年月日时分秒格式的时间
	 * @param timeStr
	 * @return
	 */
	public static String getDateByCalendar(Calendar timeStr) {
		String dateTime = "";
		try {
			if (timeStr == null) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateTime = sdf.format(timeStr.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime; 
	}

	/**
	 * 将带T的数据转为yyyy-MM-dd H:mm:ss 
	 * @param utcTime
	 * @return
	 */
	public static String utc2Local(String utcTime) {
		if (utcTime == null || utcTime.length() <= 0) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = pattern.parse(utcTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}
	
	/**
	 * 将带T的数据转为日期格式
	 * @param utcTime
	 * @return
	 */
	public static Date utc2Date(String utcTime) {
		if (utcTime == null || utcTime.length() <= 0) {
			return null;
		}
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = pattern.parse(utcTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 时间格式化
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String dateFormat(Date date,String format) {
		SimpleDateFormat time = new SimpleDateFormat(format);
		return time.format(date);
	}
	
	/**
	 * 返回当前的时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String nowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 返回当前的日期
	 * patten 格式
	 */
	public static String nowTimeDate(String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(new Date());
	}
	
	/**
	 * 返回当前时间的时间戳
	 * @return 如：1441509773
	 */
	public static Long nowIntTime() {
		// 获取系统当前时间，截取出年月日/时分秒
		return Calendar.getInstance().getTimeInMillis() / 1000;
	}
	
	/**
	 * 时间格式转换为时间戳
	 * 格式类型 yyyy-MM-dd HH:mm:ss
	 */
	public static int getIntTime(String time){
		if ("".equals(time)||null==time) {
			return 0;
		}else{
			SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date dateS = null;
			try {
				dateS = format.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int intTime = new Long(dateS.getTime()/1000).intValue();
			return intTime;
		}
	}
	
	/**
	 * 时间格式转换为时间戳
	 * 格式类型 HH:mm
	 */
	public static int getIntTime2(String time){
		if ("".equals(time)||null==time) {
			return 0;
		}else{
			SimpleDateFormat format =  new SimpleDateFormat( "HH:mm" );
			Date dateS = null;
			try {
				dateS = format.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int intTime = new Long(dateS.getTime()/1000).intValue();
			return intTime;
		}
	}
	
	/**
	 * 将时间戳转换为时间格式[10位]
	 * @param intTime
	 */
	public static String getDateTime(int intTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(new Date(intTime*1000L));
		return dateTime;
	}
	
	/**
	 * 将时间戳转换为时间格式[13位]
	 * @param intTime
	 */
	public static String getDateTime13(long intTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(new Date(intTime));
		return dateTime;
	}
		
	/**
	 * 取得年
	 * @param date
	 */
	public static String getYear(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 取得月
	 * @param date
	 */
	public static String getMonth(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 取得日
	 * @param date
	 */
	public static String getDay(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			return sdf.format(date);
		}
		return "";
	}
	
	/**
	 * 取得指定时间的前n天或后n天
	 * 前n天 -1
	 * 后n天 1
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getNextDay(Date date, int n) {
		String resultDate = "";
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, n);
				date = calendar.getTime();
				resultDate = sdf.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultDate;
	}
	
	/**
	 * 取得指定时间的前n小时或后n小时
	 * 前n小时 -1
	 * 后n小时 1
	 * @param date
	 * @param format hour、minute、second
	 * @param n
	 * @return
	 */
	public static String getNextTime(Date date,String format, int n) {
		String resultDate = "";
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (format.equals("hour")) {//小时
					calendar.add(Calendar.HOUR, n);
				}else if(format.equals("minute")){//分钟
					calendar.add(Calendar.MINUTE, n);
				}else if(format.equals("second")){//秒
					calendar.add(Calendar.SECOND, n);
				}else {
					return "";
				}
				date = calendar.getTime();
				resultDate = sdf.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 把秒转换成对应的 【*天*小时*分钟*秒】的格式
	 * @param 要转换的秒数
	 * @return 该毫\秒数转换为 * days * hours * minutes * seconds 后的格式
	 */
	public static String formatDuring(int s) {
		int days = s / (60 * 60 * 24);
		int hours = (s % (60 * 60 * 24)) / (60 * 60);
		int minutes = (s % (60 * 60)) / 60;
		int seconds = s % 60;
		String time = "";
		if (days != 0) {
			time = days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
		} else if (days == 0 && hours != 0) {
			time = hours + "小时" + minutes + "分钟" + seconds + "秒";
		} else if (days == 0 && hours == 0 && minutes != 0) {
			time = minutes + "分钟" + seconds + "秒";
		} else if (days == 0 && hours == 0 && minutes == 0 && seconds != 0) {
			time = seconds + "秒";
		} else {
			time = "";
		}
		return time;
	}
	
	/**
	 * 简介：用户在线计时
	 * 时间：2010-12-24
	 */
    public static String sumTime(long ms) {
		int ss = 1000;
		long mi = ss * 60;
		long hh = mi * 60;
		long dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
		String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
		String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
		String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : "" + strMilliSecond + " 毫秒";
		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond;
	}
    
    /**
     * 判断时间是否在2个时间范围内
     * 返回true就是在工作时间内，反之不在
     * @param startTime
     * @param entTime
     * @param time
     */
    public static boolean isWork(int startTime,int entTime,int time) {
    	if (startTime==entTime) {
			return true;
		} else if (startTime > entTime) {
			if (time >= startTime || time <= entTime) { //隔夜
				return true;
			}
		}else {
			if (time >= startTime && time <= entTime) { //当天
				return true;
			}
		}
		return false;
	}
	
    /**
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
	 * @author wxy
	 * @param ctime
	 *            时间
	 * @param format
	 *            格式 格式描述:例如:yyyy-MM-dd yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String showTime(Date ctime, String format) {
		String r = "";
		if (ctime == null)
			return r;
		if (format == null)
			format = "MM-dd HH:mm";
		long nowtimelong = System.currentTimeMillis();
		long ctimelong = ctime.getTime();
		long result = Math.abs(nowtimelong - ctimelong);
		if (result < 60000) {// 一分钟内
			long seconds = result / 1000;
			if (seconds == 0) {
				r = "刚刚";
			} else {
				r = seconds + "秒前";
			}
		} else if (result >= 60000 && result < 3600000) {// 一小时内
			long seconds = result / 60000;
			r = seconds + "分钟前";
		} else if (result >= 3600000 && result < 86400000) {// 一天内
			long seconds = result / 3600000;
			r = seconds + "小时前";
		} else if (result >= 86400000 && result < 1702967296) {// 三十天内
			long seconds = result / 86400000;
			r = seconds + "天前";
		} else {// 日期格式
			format = "MM-dd HH:mm";
			SimpleDateFormat df = new SimpleDateFormat(format);
			r = df.format(ctime).toString();
		}
		return r;
	}
	
	/**
	* 获取当前时间是星期几
	* @param date
	* @return 
	* @return String 
	* @throws 
	* Date: Aug 20, 20134:27:43 PM
	 */
	public static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四","星期五", "星期六" };
	public static String getWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){
			w = 0;
		}
		return weekDays[w];
	}
    
	
	
}
