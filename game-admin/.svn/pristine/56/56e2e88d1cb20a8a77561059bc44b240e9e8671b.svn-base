package com.game.admin.utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.python.google.common.collect.Lists;

import com.game.util.ObjectUtil;
import com.game.util.Timer;

public class TimerUtils {
	public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATTER_ZERO = "yyyy-MM-dd 00:00:00";
	public static final String FORMATTER_yyyMMdd = "yyyyMMdd";
	public static final String FORMATTER_STRICT = "yyyyMMddHHmmss.SSS";
	public static final String FORMATTER_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String FORMATTER_HH_mm_ss = "HH:mm:ss";
	public final static String FORMATTER_yyyyMMddHH = "yyyyMMddHH";
	public final static String FORMATTER_yyyyMMddHH_ZERO = "yyyy-MM-dd HH:00:00";
	/** 日期格式 yyyy-MM-dd HH:mm:ss */
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(FORMATTER);
	/** 日期格式 yyyy-MM-dd 00:00:00*/
	public static final DateTimeFormatter DATE_FORMATTER_ZERO = DateTimeFormat.forPattern(FORMATTER_ZERO);
	/** 日期格式yyyyMMdd*/
	public static final DateTimeFormatter DATE_FORMATTER_yyyMMdd = DateTimeFormat.forPattern(FORMATTER_yyyMMdd);
	/** 日期格式yyyyMMddHHmmss.SSS*/
	public static final DateTimeFormatter DATE_FORMATTER_STRICT = DateTimeFormat.forPattern(FORMATTER_STRICT);
	/** 日期格式FORMATTER_yyyy_MM_dd*/
	public static final DateTimeFormatter DATE_FORMATTER_yyyy_MM_dd = DateTimeFormat.forPattern(FORMATTER_yyyy_MM_dd);
	public static final DateTimeFormatter DATE_FORMATTER_HH_mm_ss = DateTimeFormat.forPattern(FORMATTER_HH_mm_ss);
	/** 日期格式FORMATTER_yyyyMMddHH*/
	public static final DateTimeFormatter DATE_FORMATTER_yyyyMMddHH = DateTimeFormat.forPattern(FORMATTER_yyyyMMddHH);
	/** 日期格式FORMATTER_yyyyMMddHH*/
	public static final DateTimeFormatter DATE_FORMATTER_yyyyMMddHH_ZERO = DateTimeFormat.forPattern(FORMATTER_yyyyMMddHH_ZERO);
	/**
	 * 系统当前时间的修正值，用于动态修改时间方便测试
	 */
	public static long CURRENT_TIME_REVISE = 0;

	//	/**
	//	 * 获得 年 月 日 的时间
	//	 * @return
	//	 */
	//	public static String getDay() {
	//		Date date = new Date();
	//		SimpleDateFormat sf = new SimpleDateFormat(FORMATTER_yyyy_MM_dd);
	//		return sf.format(date);
	//	}
	//
	//	/**
	//	 * 获得小时 分 秒 格式的时间
	//	 * @return
	//	 */
	//	public static String getTime() {
	//		Date date = new Date();
	//		SimpleDateFormat sf = new SimpleDateFormat(FORMATTER_hh_mm_ss);
	//		return sf.format(date);
	//	}

	/**
	 * 当前时间字符串格式
	 * @return
	 */
	public static String getNowStringTime() {
		return getNowDateTime().toString(Timer.DATE_FORMATTER);
	}
	public static String getNowStringTimes() {
		return getNowDateTime().toString(Timer.DATE_FORMATTER_yyyy_MM_dd);
	}
	public static String getNowStringTime(DateTimeFormatter formatter){
		return getNowDateTime().toString(formatter);
	}

	public static DateTime getNowDateTime() {
		return DateTime.now();
	}

	/**
	 * 获取当前时间
	 * <p>
	 * 如果有修正则是修正后的时间
	 * @return
	 */
	public static Date getNowDate() {
		return getNowDateTime().toDate();
	}

	/**
	 * 获取当前时间（毫秒）
	 * <p>
	 * 如果有修正则是修正后的时间
	 * @return
	 */
	public static long getNowTime() {
		//		return DateTime.now().getMillis();
		return DateTimeUtils.currentTimeMillis();
	}

	/**
	 * 获取当前时间（秒）
	 * @return
	 */
	public static int getNowSecond() {
		return Long.valueOf(getNowTime() / DateTimeConstants.MILLIS_PER_SECOND).intValue();
	}

	/**
	 * 初始时间： 1970-01-01 00:00:00
	 * @return
	 */
	public static String getZeroStringTime() {
		return "1970-01-01 08:00:00";
	}

	/**
	 * 指定日期字符串(格式：yyyy-MM-dd HH:mm:ss )转换为日期格式
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		return getDateTime(date).toDate();
	}
	
	public static DateTime getDateTime(String date){
		return DateTime.parse(date, DATE_FORMATTER);
	}

	/**
	 * 指定日期字符串转(指定格式)换为日期格式
	 * @param date 指定日期
	 * @param datetimeFormtter 指定格式
	 * @return
	 */
	public static Date getDate(String date, DateTimeFormatter datetimeFormtter) {
		return getDateTime(date,datetimeFormtter).toDate();
	}
	
	public static DateTime getDateTime(String date,DateTimeFormatter datetimeFormtter){
		return DateTime.parse(date, datetimeFormtter);
	}

	/**
	 * 两个日期是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		//		Period period = new Period(new DateTime(date1), new DateTime(date2), PeriodType.days());
		//		return period.getDays() == 0;
		DateTime dateTime1 = new DateTime(date1);
		DateTime dateTime2 = new DateTime(date2);
		return isSameDay(dateTime1, dateTime2);
	}

	/**
	 * 是否是同一年同一月的同一天
	 * @param dateTime1
	 * @param dateTime2
	 * @return
	 */
	public static boolean isSameDay(DateTime dateTime1, DateTime dateTime2) {
		return dateTime1.getYear() == dateTime2.getYear() && dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear()
				&& dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth();
	}

	/**
	 * 是否是同一年同一月的同一天
	 * @param datetime
	 * @return
	 */
	public static boolean isSameDay(DateTime datetime) {
		DateTime now = getNowDateTime();
		return now.getYear() == datetime.getYear() && now.getMonthOfYear() == datetime.getMonthOfYear()
				&& now.getDayOfMonth() == datetime.getDayOfMonth();
	}

	/**
	 * 是否是同一年的同一月
	 * @param datetime
	 * @return
	 */
	public static boolean isSameMonth(DateTime datetime) {
		DateTime now = getNowDateTime();
		return now.getYear() == datetime.getYear() && now.getMonthOfYear() == datetime.getMonthOfYear();
	}

	/**
	 * 是否是同一年同一月的同一周
	 * @param dateTime
	 * @return
	 */
	public static boolean isSameWeek(DateTime dateTime) {
		DateTime now = getNowDateTime();
		return now.getYear() == dateTime.getYear() && now.getMonthOfYear() == dateTime.getMonthOfYear()
				&& now.getWeekOfWeekyear() == dateTime.getWeekOfWeekyear();
	}

	/**
	 * 是否是同一年同一月的同一周
	 * @param dateTime1
	 * @param dateTime2
	 * @return
	 */
	public static boolean isSameWeek(DateTime dateTime1, DateTime dateTime2) {
		return dateTime1.getYear() == dateTime2.getYear() && dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear()
				&& dateTime1.getWeekyear() == dateTime2.getWeekyear();
	}

	/**
	 * 是否是同一天的同一小時
	 * @param datetime
	 * @return
	 */
	public static boolean isSameHour(DateTime datetime) {
		DateTime now = getNowDateTime();
		return now.getYear() == datetime.getYear() && now.getHourOfDay() == datetime.getHourOfDay();
	}
	
	/**
	 * 是否是同一年
	 * @param dateTime
	 * @return
	 */
	public static boolean isYear(DateTime dateTime) {
		DateTime now = getNowDateTime();
		return now.getYear() == dateTime.getYear();
	}

	/**
	 * 计算dateTime1 和 dateTime1 天数差 (dateTime2 - dateTime1)(ps. 以24小时计算(即超过24小时才算1天))
	 * @param dateTime1 减数
	 * @param dateTime2 被减数
	 * @return
	 */
	public static int dayMinus(DateTime startTime, DateTime endTime) {
		return Days.daysBetween(startTime, endTime).getDays();
	}

	/**
	 * 计算dateTime1 和 dateTime1 小时差 (dateTime2 - dateTime1)
	 * @param dateTime1 减数
	 * @param dateTime2 被减数
	 * @return
	 */
	public static int hourMinus(DateTime dateTime1, DateTime dateTime2){
		return Hours.hoursBetween(dateTime1, dateTime2).getHours();
	}
	
	/**
	 * 计算dateTime1 和 dateTime1 秒差 (dateTime2 - dateTime1)
	 * @param dateTime1 减数
	 * @param dateTime2 被减数
	 * @return
	 */
	public static int secondsMinus(DateTime dateTime1, DateTime dateTime2) {
		return Seconds.secondsBetween(dateTime1, dateTime2).getSeconds();
	}

	/**
	 * 计算 compareTime 是否在start 和 end 之间 (三个时间参数均不能为空)
	 * @param start 起始时间 
	 * @param end 结束时间
	 * @param compareTime 比较的时间
	 * @return
	 */
	public static boolean isInInterval(DateTime start, DateTime end, DateTime compareTime) {
		Interval interval = new Interval(start, end);
		return interval.contains(compareTime);
	}

	/**
	 * 当前时间是否在指定起止时间内(起止时间均可为null)
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	public static boolean isValidNow(DateTime startDateTime, DateTime endDateTime) {
		if (ObjectUtil.isEmptyObjects(endDateTime) && ObjectUtil.isEmptyObject(startDateTime)) {
			return true;
		} else if (ObjectUtil.isEmptyObject(startDateTime)) {
			return endDateTime.isAfter(Timer.getNowTime());
		} else if (ObjectUtil.isEmptyObject(endDateTime)) {
			return startDateTime.isBefore(Timer.getNowTime());
		} else {
			return Timer.isInInterval(startDateTime, endDateTime, Timer.getNowDateTime());
		}
	}
	
	/**
	 * 获取day天之后
	 * @param startDateTime
	 * @param dayNum
	 * @return day天之后日期
	 */
	public static DateTime getAfterDay(DateTime startDateTime,int dayNum){
		if(dayNum > 0){
			startDateTime = startDateTime.plusDays(dayNum);
		}else if(dayNum < 0){
			startDateTime = startDateTime.minusDays(dayNum);
		}
		return startDateTime;
	}
	
	/**
	 * @param startDateTime
	 * @param hourNum
	 * @returnf
	 */
	public static DateTime getAfterHour(DateTime startDateTime,int hourNum){
		if(hourNum > 0){
			startDateTime = startDateTime.plusHours(hourNum);
		}else if(hourNum < 0){
			startDateTime = startDateTime.minusHours(hourNum);
		}
		return startDateTime;
	}
	
	/**
	 * 获取day天之后的日期字符串
	 * @param startDateTime
	 * @param dayNum
	 * @param dtf 为null时 使用DATE_FORMATTER
	 * @return
	 */
	public static String getAfterDayString(DateTime startDateTime,int dayNum,DateTimeFormatter dtf){
		DateTime dt = getAfterDay(startDateTime, dayNum);
		if(dtf == null){
			return dt.toString(DATE_FORMATTER);
		}
		return dt.toString(dtf);
	}
	
	
	/**
	 * 获取一个指定的过期时间
	 * @param day
	 * @param hour
	 * @return
	 */
	
	public static String getExplicitDateTime (int hour) {
		
	
		
		DateTime expire = Timer.getDateTime(Timer.getNowStringTime(Timer
				.DATE_FORMATTER_ZERO), Timer.DATE_FORMATTER_ZERO);
		
		int nowHour = DateTime.now().getHourOfDay();
		if (nowHour >= hour) {
			expire = Timer.getDateTime(Timer.getNowStringTime(Timer
					.DATE_FORMATTER_ZERO), Timer.DATE_FORMATTER_ZERO).plusDays(1);
		}
		
		return expire.plusHours(hour).toString(Timer.DATE_FORMATTER); 
	}
	
	/**
	 * 获取两个日期之间的日期   yyyyMMdd (包含开始日期和结束日期)
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return
	 */
	public static List<String> getAmongTime(String startTime,String endTime){
		Date startDate = getDate(startTime, TimerUtils.DATE_FORMATTER);
		Date endDate = getDate(endTime, TimerUtils.DATE_FORMATTER);
		if(isSameDay(startDate, endDate)){
			return Collections.emptyList();
		}
		List<String> timeLists = Lists.newArrayList();
		
		Calendar tempStart = Calendar.getInstance();  
		tempStart.setTime(startDate);
		tempStart.add(Calendar.DAY_OF_YEAR, 1);
		
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(endDate);
		
		DateTime startTime1 = new DateTime(startDate);
		String startTimeStr = startTime1.toString(TimerUtils.DATE_FORMATTER_yyyMMdd);
		timeLists.add(startTimeStr);
		while (tempStart.before(tempEnd)) {
			DateTime dateTime1 = new DateTime(tempStart.getTime());
			String passTimeStr = dateTime1.toString(TimerUtils.DATE_FORMATTER_yyyMMdd);
			timeLists.add(passTimeStr);
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
		return timeLists;
	}

	
	
	public static void main(String[] args) {
//		System.out.println(TimerUtils.getAfterDayString(Timer.getNowDateTime(),4,null));
//		System.out.println(isSameDay(new Date(), new Date()));
//		System.out.println(getNowTime());
//		System.out.println(getNowDate().getTime());
//		System.out.println(getNowDateTime().getDayOfWeek());
//		System.out.println(getNowDateTime().getHourOfDay());
//		getAmongTime("20180420", "20180423");
	}
}
