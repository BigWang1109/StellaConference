/**
 * @Title: DateTimeUtil.java
 * @Package net.openfree.common.util
 * @date 2015-10-16 下午13:13:13
 */
package com.wxx.conference.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: DateTimeUtil
 * @Description: 日期工具
 * @Author:wxx
 * @Company:泛海控股
 */
public class DateTimeUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

	public static final String DATEPARTITION = "-";
	
	/**几种日期格式*/
	public static final String DATE_TIME_FORMART_ALL = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_TIME_FORMART_HIGH = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMART_HIGH_M = "yyyy-MM-dd HH:mm";
	public static final String DATE_TIME_FORMART_HIGH_H = "yyyy-MM-dd HH";
	public static final String DATE_TIME_FORMART_SHORT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMART_SHORT_M = "yyyy-MM";
	public static final String DATE_TIME_FORMART_SHORT_Y = "yyyy";
	public static final String DATE_TIME_FORMART_TIME = "HH:mm:ss";
	public static final String DATE_TIME_FORMART_TIMESTAMP = "HHmmss";
	
	/*_____________________________获取日期字符串操作开始______________________________*/
	
	/**
	 * 
	 * @Title: getCurDateFALL
	 * @Description: 返回yyyy-MM-dd HH:mm:ss.SSS格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFALL() {
//		return getCurDateFormat(DATE_TIME_FORMART_ALL);
		return formatDateToString(new Date(),DATE_TIME_FORMART_ALL);
	}
	
	/**
	 * 
	 * @Title: getDateFALL
	 * @Description: 返回yyyy-MM-dd HH:mm:ss.SSS格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFALL(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_ALL);
		return formatDateToString(date,DATE_TIME_FORMART_ALL);
	}
	
	/**
	 * 
	 * @Title: getCurDateFHigh
	 * @Description: 返回yyyy-MM-dd HH:mm:ss格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFHigh() {
//		return getCurDateFormat(DATE_TIME_FORMART_HIGH);
		return formatDateToString(new Date(),DATE_TIME_FORMART_HIGH);
	}
	
	/**
	 * 
	 * @Title: getDateFHigh
	 * @Description: 返回yyyy-MM-dd HH:mm:ss格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFHigh(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_HIGH);
		return formatDateToString(date,DATE_TIME_FORMART_HIGH);
	}
	
	/**
	 * 
	 * @Title: getCurDateFHigh_M
	 * @Description: 返回yyyy-MM-dd HH:mm格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFHigh_M() {
//		return getCurDateFormat(DATE_TIME_FORMART_HIGH_M);
		return formatDateToString(new Date(),DATE_TIME_FORMART_HIGH_M);
	}
	
	/**
	 * 
	 * @Title: getDateFHigh_M
	 * @Description: 返回yyyy-MM-dd HH:mm格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFHigh_M(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_HIGH_M);
		return formatDateToString(date,DATE_TIME_FORMART_HIGH_M);
	}
	
	/**
	 * 
	 * @Title: getCurDateFHigh_H
	 * @Description: 返回yyyy-MM-dd HH格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFHigh_H() {
//		return getCurDateFormat(DATE_TIME_FORMART_HIGH_H);
		return formatDateToString(new Date(),DATE_TIME_FORMART_HIGH_H);
	}
	
	/**
	 * 
	 * @Title: getDateFHigh_H
	 * @Description: 返回yyyy-MM-dd HH格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFHigh_H(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_HIGH_H);
		return formatDateToString(date,DATE_TIME_FORMART_HIGH_H);
	}
	
	/**
	 * 
	 * @Title: getCurDateFShort
	 * @Description: 返回yyyy-MM-dd格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFShort() {
//		return getCurDateFormat(DATE_TIME_FORMART_SHORT);
		return formatDateToString(new Date(),DATE_TIME_FORMART_SHORT);
	}
	
	/**
	 * 
	 * @Title: getDateFShort
	 * @Description: 返回yyyy-MM-dd格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFShort(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_SHORT);
		return formatDateToString(date,DATE_TIME_FORMART_SHORT);
	}
	
	/**
	 * 
	 * @Title: getCurDateFShort_M
	 * @Description: 返回yyyy-MM格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFShort_M() {
//		return getCurDateFormat(DATE_TIME_FORMART_SHORT_M);
		return formatDateToString(new Date(),DATE_TIME_FORMART_SHORT_M);
	}
	
	/**
	 * 
	 * @Title: getDateFShort_M
	 * @Description: 返回yyyy-MM格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFShort_M(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_SHORT_M);
		return formatDateToString(date,DATE_TIME_FORMART_SHORT_M);
	}
	
	/**
	 * 
	 * @Title: getCurDateFShort_RM
	 * @Description: 返回yyyy-MM格式日期String
	 * @param mon
	 * @return
	 *
	 */
	public static String getCurDateFShort_RM(int mon) {
//		return getDateFShort_RM(new Date(),mon);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, mon);
		return formatDateToString(calendar.getTime(),DATE_TIME_FORMART_SHORT_M);
		
	}
	
	/**
	 * 
	 * @Title: getDateFShort_RM
	 * @Description: 返回yyyy-MM格式日期String
	 * @param date
	 * @param mon
	 * @return
	 *
	 */
	public static String getDateFShort_RM(Date date,int mon) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, mon);
		return formatDateToString(calendar.getTime(),DATE_TIME_FORMART_SHORT_M);
	}
	
	/**
	 * 
	 * @Title: getCurDateFShort_Y
	 * @Description: 返回yyyy格式日期String
	 * @return
	 * 
	 */
	public static String getCurDateFShort_Y() {
//		return getCurDateFormat(DATE_TIME_FORMART_SHORT_Y);
		return formatDateToString(new Date(),DATE_TIME_FORMART_SHORT_Y);
	}
	
	/**
	 * 
	 * @Title: getDateFShort_Y
	 * @Description: 返回yyyy格式日期String
	 * @param date
	 * @return
	 *
	 */
	public static String getDateFShort_Y(Date date) {
//		return getDateFormat(date,DATE_TIME_FORMART_SHORT_Y);
		return formatDateToString(date,DATE_TIME_FORMART_SHORT_Y);
	}
	
	/**
	 * 
	 * @Title: getCurDateFormat
	 * @Description: 得到相应格式的当前日期字符串
	 * @param dateFormat(时间格式)
	 * @return 相应格式的当前日期
	 * 
	 */
	public static String getCurDateFormat(String dateFormat){
//		return getDateFormat(new Date(),dateFormat);
		return formatDateToString(new Date(),dateFormat);
	}
	
	/**
	 * 
	 * @Title: getDateFormat
	 * @Description: 得到相应格式的日期字符串
	 * @param date(时间格式)
	 * @param dateFormat
	 * @return 相应格式的日期
	 *
	 */
	public static String getDateFormat(Date date,String dateFormat){
		return formatDateToString(date,dateFormat);
	}
	
	/**
	 * @Title: formatDateToString
	 * @Description: 将Date类型按照格式再转换成String
	 * @param date(需要转换的Date)
	 * @param dateFormat(时间格式)
	 * @return 转换后的String类型的日期
	 */
	public static String formatDateToString(Date date,String dateFormat){
		return new SimpleDateFormat(dateFormat).format(date);
	}
	
	/**
	 * @Title: formatCNYear
	 * @Description: 返回yyyy年格式日期String
	 * @param date
	 * @return
	 */
	public static String formatCNYear(Date date){
		return formatDateToString(date, "yyyy年");
	}
	
	/**
	 * @Title: formatCNMonth
	 * @Description: 返回yyyy年MM月格式日期String
	 * @param date
	 * @return
	 */
	public static String formatCNMonth(Date date){
		return formatDateToString(date,"yyyy年MM月");
	}
	
	/**
	 * @Title: formatCNDate
	 * @Description: 返回yyyy年MM月dd日格式日期String
	 * @param date
	 * @return
	 */
	public static String formatCNDate(Date date){
		return formatDateToString(date,"yyyy年MM月dd日");
	}
	
	/**
	 * @Title: formatCNTime
	 * @Description: 返回yyyy年MM月dd日 HH时mm分ss秒格式日期String
	 * @param date
	 * @return
	 */
	public static String formatCNTime(Date date){
		return formatDateToString(date,"yyyy年MM月dd日 HH时mm分ss秒");
	}
	public static String formatCNTimeWithoutSeconds(Date date){
		return formatDateToString(date,"yyyy年MM月dd日 HH时mm分");
	}
	/*_____________________________获取日期字符串操作结束______________________________*/
	
	/*_____________________________获取日期类实例操作开始______________________________*/
	
	/**
	 * @Title: getCurDate
	 * @Description: 当前日期java.util.Date
	 * @return 当前日期Date
	 */
	public static Date getCurDate(){
		return new Date();
	}
	
	/**
	 * @Title: getDateFALL
	 * @Description: 将（yyyy-MM-dd HH:mm:ss.SSS）格式的字符串转换为Date实例
	 * @param date（要转换的字符串）
	 * @return 转换好的Date实例，不成功为null
	 */
	public static Date getDateFALL(String date) {
		return formatStringToDate(date, DATE_TIME_FORMART_ALL, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date getDateFALL(String date,Locale locale) {
		return formatStringToDate(date, DATE_TIME_FORMART_ALL, locale);
	}
	
	/**
	 * @Title: getDateFHigh
	 * @Description: 将（yyyy-MM-dd HH:mm:ss）格式的字符串转换为Date实例
	 * @param date（要转换的字符串）
	 * @return 转换好的Date实例，不成功为null
	 */
	public static Date getDateFHigh(String date) {
		return formatStringToDate(date, DATE_TIME_FORMART_HIGH, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date getDateFHigh(String date,Locale locale) {
		return formatStringToDate(date, DATE_TIME_FORMART_HIGH, locale);
	}
	
	/**
	 * @Title: getDateFHigh_M
	 * @Description: 将（yyyy-MM-dd HH:mm）格式的字符串转换为Date实例
	 * @param date（要转换的字符串）
	 * @return 转换好的Date实例，不成功为null
	 */
	public static Date getDateFHigh_M(String date) {
		return formatStringToDate(date, DATE_TIME_FORMART_HIGH_M, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date getDateFHigh_M(String date,Locale locale) {
		return formatStringToDate(date, DATE_TIME_FORMART_HIGH_M, locale);
	}
	
	/**
	 * @Title: getDateFHigh_H
	 * @Description: 将（yyyy-MM-dd HH）格式的字符串转换为Date实例
	 * @param date（要转换的字符串）
	 * @return 转换好的Date实例，不成功为null
	 */
	public static Date getDateFHigh_H(String date) {
		return formatStringToDate(date, DATE_TIME_FORMART_HIGH_H, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date getDateFHigh_H(String date,Locale locale) {
		return formatStringToDate(date, DATE_TIME_FORMART_HIGH_H, locale);
	}
	
	/**
	 * @Title: getDateFShort
	 * @Description: 将（yyyy-MM-dd）格式的字符串转换为Date实例
	 * @param date（要转换的字符串）
	 * @return 转换好的Date实例，不成功为null
	 */
	public static Date getDateFShort(String date) {
		return formatStringToDate(date, DATE_TIME_FORMART_SHORT, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date getDateFShort(String date,Locale locale) {
		return formatStringToDate(date, DATE_TIME_FORMART_SHORT, locale);
	}
	
	/**
	 * @Title: getDateFShort_M
	 * @Description: 将（yyyy-MM）格式的字符串转换为Date实例
	 * @param date（要转换的字符串）
	 * @return 转换好的Date实例，不成功为null
	 */
	public static Date getDateFShort_M(String date) {
		return formatStringToDate(date, DATE_TIME_FORMART_SHORT_M, Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date getDateFShort_M(String date,Locale locale) {
		return formatStringToDate(date, DATE_TIME_FORMART_SHORT_M, locale);
	}
	
	/**
	 * @Title: formatStringToDate
	 * @Description: 将String类型的日期按照格式转换成Date类型
	 * @param date 需要转换的日期字符串
	 * @param dateFormat
	 * @return
	 */
	public static Date formatStringToDate(String date,String dateFormat) {
		return formatStringToDate(date,dateFormat,Locale.SIMPLIFIED_CHINESE);
	}
	
	public static Date formatStringToDate(String date,String dateFormat,Locale locale) {
		Date _date = null;
		ParsePosition pos = new ParsePosition(0);
		try {
			if (date != null || !"".equals(date))
				_date = new SimpleDateFormat(dateFormat,locale).parse(date, pos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _date;
	}
	
//	public static void main(String[] args) {
//		System.out.println("getCurDateFALL---"+getCurDateFALL());
//		System.out.println("getDateFALL---"+getDateFALL(new Date()));
//		System.out.println("getCurDateFHigh---"+getCurDateFHigh());
//		System.out.println("getDateFHigh---"+getDateFHigh(new Date()));
//		System.out.println("getCurDateFHigh_M---"+getCurDateFHigh_M());
//		System.out.println("getDateFHigh_M---"+getDateFHigh_M(new Date()));
//		System.out.println("getCurDateFHigh_H---"+getCurDateFHigh_H());
//		System.out.println("getDateFHigh_H---"+getDateFHigh_H(new Date()));
//		System.out.println("getCurDateFShort---"+getCurDateFShort());
//		System.out.println("getDateFShort---"+getDateFShort(new Date()));
//		System.out.println("getCurDateFShort_M---"+getCurDateFShort_M());
//		System.out.println("getDateFShort_M---"+getDateFShort_M(new Date()));
//		System.out.println("getCurDateFShort_RM---"+getCurDateFShort_RM(1));
//		System.out.println("getDateFShort_RM---"+getDateFShort_RM(new Date(),1));
//		System.out.println("getCurDateFShort_Y---"+getCurDateFShort_Y());
//		System.out.println("getDateFShort_Y---"+getDateFShort_Y(new Date()));
//		System.out.println("getCurDateFormat---"+getCurDateFormat(DATE_TIME_FORMART_ALL));
//		System.out.println("getDateFormat---"+getDateFormat(new Date(),DATE_TIME_FORMART_ALL));
//		System.out.println("formatDateToString---"+formatDateToString(new Date(),DATE_TIME_FORMART_ALL));
//		System.out.println("formatCNYear---"+formatCNYear(new Date()));
//		System.out.println("formatCNMonth---"+formatCNMonth(new Date()));
//		System.out.println("formatCNDate---"+formatCNDate(new Date()));
//		System.out.println("formatCNTime---"+formatCNTime(new Date()));
//		
//		System.out.println("getCurDate---"+getCurDate());
//		System.out.println("getDateFALL---"+getDateFALL("2015-05-29 14:53:04.407"));
//		System.out.println("getDateFALL---"+getDateFALL("2015-05-29 14:53:04.407",Locale.SIMPLIFIED_CHINESE));
//		System.out.println("getDateFHigh---"+getDateFHigh("2015-05-29 14:53:04"));
//		System.out.println("getDateFHigh---"+getDateFHigh("2015-05-29 14:53:04",Locale.SIMPLIFIED_CHINESE));
//		System.out.println("getDateFHigh_M---"+getDateFHigh_M("2015-05-29 14:53"));
//		System.out.println("getDateFHigh_M---"+getDateFHigh_M("2015-05-29 14:53",Locale.SIMPLIFIED_CHINESE));
//		System.out.println("getDateFHigh_H---"+getDateFHigh_H("2015-05-29 14"));
//		System.out.println("getDateFHigh_H---"+getDateFHigh_H("2015-05-29 14",Locale.SIMPLIFIED_CHINESE));
//		System.out.println("getDateFShort---"+getDateFShort("2015-05-29"));
//		System.out.println("getDateFShort---"+getDateFShort("2015-05-29",Locale.SIMPLIFIED_CHINESE));
//		System.out.println("getDateFShort_M---"+getDateFShort_M("2015-05"));
//		System.out.println("getDateFShort_M---"+getDateFShort_M("2015-05",Locale.SIMPLIFIED_CHINESE));
//		System.out.println("formatStringToDate---"+formatStringToDate("2015-05-29 14:53:04.410",DATE_TIME_FORMART_ALL));
//		System.out.println("formatStringToDate---"+formatStringToDate("2015-05-29 14:53:04.410",DATE_TIME_FORMART_ALL,Locale.SIMPLIFIED_CHINESE));
//	}
	/*_____________________________获取日期类实例操作开始______________________________*/
	
	/**
	 * @Title: convertDateToCalendar
	 * @Description: java.util.Date转换为java.util.Calendar
	 * @param date(要转换的Date)
	 * @return 转换后的Calendar
	 */
	public static Calendar convertDateToCalendar(Date date) {
		Calendar calendar = null;
		if (date != null) {
			calendar = Calendar.getInstance();
			calendar.clear();
			calendar.setTime(date);
		}
		return calendar;
	}
	
	/**
	 * 
	 * @Title: getWeekdayOfChinese
	 * @Description: 得到星期1-7对应周一-周日
	 * @param date
	 * @param local CN|EN
	 * @return
	 *
	 */
	public static String getWeekdayOfChinese(Date date,String local) {
		int week=getWeekdayOfDate(date);
		switch (week) {
		case 0:
			return "CN".equals(local)?"星期日":"Sunday";
		case 1:
			return "CN".equals(local)?"星期一":"Monday";
		case 2:
			return "CN".equals(local)?"星期二":"Tuesday";
		case 3:
			return "CN".equals(local)?"星期三":"Wednesday";
		case 4:
			return "CN".equals(local)?"星期四":"Thursday";
		case 5:
			return "CN".equals(local)?"星期五":"Friday";
		case 6:
			return "CN".equals(local)?"星期六":"Saturday";
		default:
			return "";
		}
	}
	
	/**
	 * 
	 * @Title: getWeekdayOfDate
	 * @Description: 获取日期是星期几
	 * @param date
	 * @return
	 */
	public static int getWeekdayOfDate(Date date){
		Calendar c = Calendar.getInstance();
		int weekday = -1;
		try {
			c.setTime(date);
			//星期日为一周的第一天 	SUN 	MON 	TUE 	WED 	THU 	FRI 	SAT
			//DAY_OF_WEEK返回值 	1 		2 		3 		4 		5 		6 		7
			weekday = c.get(Calendar.DAY_OF_WEEK)-1;
			//星期日为一周的第一天 	SUN 	MON 	TUE 	WED 	THU 	FRI 	SAT
			//DAY_OF_WEEK返回值 	0 		1 		2 		3 		4 		5 		6
		}catch (Exception e) {
			e.printStackTrace();
		}
		return weekday;
	}
	
	/**
	 * 
	 * @Title: calculateDaysBetweenDate
	 * @Description: 计算两个日期之间有几天
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calculateDaysBetweenDate(String startDate, String endDate){
		int intervalDay = 0;
		try {
			intervalDay = calculateDaysBetweenDate(formatStringToDate(startDate, DATE_TIME_FORMART_SHORT), formatStringToDate(endDate, DATE_TIME_FORMART_SHORT));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return intervalDay;
	}
	
	/**
	 * 
	 * @Title: calculateDaysBetweenDate
	 * @Description: 计算两个日期之间有几天
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calculateDaysBetweenDate(Date startDate, Date endDate){
		int intervalDay = 0;
		//获取两个日期相隔天数
		long time = startDate.getTime() - endDate.getTime();
		long day = time / 3600000 / 24;
		intervalDay = Integer.parseInt(String.valueOf(day));
		return intervalDay;
	}
	
	/**
	 * 
	 * @Title: calculateMonthsBetweenDate
	 * @Description: 判断两个日期相差多少个月
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calculateMonthsBetweenDate(String startDate,String endDate) {
		int months = 0;
		try {
			months = calculateMonthsBetweenDate(formatStringToDate(startDate, DATE_TIME_FORMART_SHORT), formatStringToDate(endDate, DATE_TIME_FORMART_SHORT));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return months;
	}
	
	/**
	 * 
	 * @Title: calculateMonthsBetweenDate
	 * @Description: 判断两个日期相差多少个月
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calculateMonthsBetweenDate(Date startDate,Date endDate) {
		int months = 0;
		Calendar starCal = Calendar.getInstance();
		starCal.clear();
		starCal.setTime(startDate);
		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);
		
		Calendar endCal = Calendar.getInstance();
		endCal.clear();
		endCal.setTime(endDate);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH);
		months = (eYear - sYear) * 12 + (eMonth - sMonth);
		return months;
	}
	
	/**
	 * 
	 * @Title: getDaysBetweenDateList
	 * @Description: 获取两个日期之间日期列表
	 * @param startDate
	 * @param endDate
	 * @param order DESC ASC
	 * @return
	 */
	public static List<String> getDaysBetweenDateList(Date startDate,Date endDate,String order){
		List<String> list = new LinkedList<String>();
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		if("ASC".equals(order)){
			while(starCal.compareTo(endCal)<=0){
				list.add(formatDateToString(starCal.getTime(),DATE_TIME_FORMART_SHORT));
				starCal.add(Calendar.DAY_OF_MONTH,1);
			}
		}else{
			while(endCal.compareTo(starCal)>=0){
				list.add(formatDateToString(endCal.getTime(),DATE_TIME_FORMART_SHORT));
				endCal.add(Calendar.DAY_OF_MONTH,-1);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @Title: getDaysOfMonthList
	 * @Description: 获取某年某月日期的列表
	 * @param year
	 * @param month
	 * @return
	 *
	 */
	public static List<String> getDaysOfMonthList(int year,int month){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month-1);
		return getDaysOfMonthList(cal.getTime());
//		List<String> list =new LinkedList<String>();
//		int dayNum=getDaysOfMonth(year, mon);
//		String mon_str=""+mon;
//		if(mon<10){
//			mon_str="0"+mon;
//		}
//		for(int i=1;i<=dayNum;i++){
//			 if(i<10){
//				 list.addOrgTree(year+"-"+mon_str+"-0"+i);
//			 }else {
//				 list.addOrgTree(year+"-"+mon_str+"-"+i);
//			 }
//		}
//		return list;
	}
	
	/**
	 * 
	 * @Title: getDaysOfMonthList
	 * @Description: 获取某年某月日期的列表
	 * @param date
	 * @return
	 *
	 */
	public static List<String> getDaysOfMonthList(Date date){
		Date startDate = getFirstDayOfMonth(date);
		Date endDate = getLastDayOfMonth(date);
		return getDaysBetweenDateList(startDate,endDate,"ASC");
	}
	
	/**
	 * 
	 * @Title: getMonthsBetweenDateList
	 * @Description: 获取两个日期之间月份列表
	 * @param startDate
	 * @param endDate
	 * @param order DESC|ASC
	 * @return
	 */
	public static List<String> getMonthsBetweenDateList(Date startDate,Date endDate,String order){
		List<String> list = new LinkedList<String>();
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		if("ASC".equals(order)){
			while(starCal.compareTo(endCal)<=0){
				list.add(formatDateToString(starCal.getTime(),DATE_TIME_FORMART_SHORT_M));
				starCal.add(Calendar.MONTH,1);
			}
		}else{
			while(endCal.compareTo(starCal)>=0){
				list.add(formatDateToString(endCal.getTime(),DATE_TIME_FORMART_SHORT_M));
				endCal.add(Calendar.MONTH,-1);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @Title: getDateMoveNDays
	 * @Description: 获取某日期移动N天后日期，整数往后推,负数往前移动。
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDateMoveNDays(Date date,int n){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH,n);
		date=calendar.getTime();
		return date;
	}
	
	/**
	 * 
	 * @Title: getDateMoveNMonths
	 * @Description: 获取某日期移动N月后日期，整数往后推,负数往前移动。
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDateMoveNMonths(Date date,int n){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,n);
		date=calendar.getTime();
		return date;
	}
	
	/**
	 * 
	 * @Title: getDateMoveNYears
	 * @Description: 获取某日期移动年后日期，整数往后推,负数往前移动。
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDateMoveNYears(Date date,int n){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR,n);
		date=calendar.getTime();
		return date;
	}
	
	/**
	 * 
	 * @Title: getFirstDayOfMonth
	 * @Description: 获取日期月的第一天
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @Title: getLastDayOfMonth
	 * @Description: 获取日期月的最后一天
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @Title: getDaysOfMonth
	 * @Description: 获取某年某月有多少天
	 * @param year
	 * @param month
	 * @return
	 *
	 */
	public static int getDaysOfMonth(int year,int month){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0 
		return getDaysOfMonth(cal.getTime());
		
//		java.util.GregorianCalendar d1= new java.util.GregorianCalendar(year,month-1,1);
//		java.util.GregorianCalendar d2= (java.util.GregorianCalendar)d1.clone();
//		d2.addOrgTree(Calendar.MONTH,1);
//		return (int)((d2.getTimeInMillis()-d1.getTimeInMillis())/3600/1000/24);
	}

	/**
	 * 
	 * @Title: getDaysOfMonth
	 * @Description: 获取某年某月有多少天
	 * @param date
	 * @return
	 *
	 */
	public static int getDaysOfMonth(Date date){
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		int day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数 
		return day;
	}
	
	/**
	 * 
	 * @Title: minuteTransferHours
	 * @Description: 分钟转换成小时
	 * @param minute
	 * @return
	 *
	 */
	public static String minuteTransferHours(String minute){
		if(minute==null || "".equals(minute)){
			return null;
		}
		try {
			Double min=Double.parseDouble(minute);
			String hours= (long)(min/60)<10?"0"+(long)(min/60):""+(long)(min/60);
			String minutes=(long)(min % 60)<10?"0"+(long)(min % 60):""+(long)(min % 60);
			return hours + ":" + minutes;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: compareDateForName
	 * @Description: 将所给日期与当前日期作比较，判断出是今天、昨天还是前天
	 * @param date(所要比较的日期)
	 * @param local CN|EN
	 * @return
	 *
	 */
	public static String compareDateForName(Date date,String local){
//		String balance = String.valueOf((new Date().getTime()-date.getTime())/(3600*24*1000));
		int balance = calculateDaysBetweenDate(new Date(),date);
		//N天后
		if(balance<-2){
			return "CN".equals(local)?balance+"天后":"After "+balance+" Days";
		}
		//后天
		else if(balance==-2){
			return "CN".equals(local)?"后天":"After Tomorrow";
		}
		//明天
		else if(balance==-1){
			return "CN".equals(local)?"明天":"Tomorrow";
		}
		//今天
		else if(balance==0){
			return "CN".equals(local)?"今天":"Today";
		}
		//昨天
		else if(balance==1){
			return "CN".equals(local)?"昨天":"Yesterday";
		}
		//前天
		else if(balance==2){
			return "CN".equals(local)?"前天":"Before Yesterday";
		}
		//N天前
		else if(balance>-2){
			return "CN".equals(local)?balance+"天前":"Before "+balance+" Days";
		}
		return formatDateToString(date,DATE_TIME_FORMART_SHORT);
	}

//	public static void main(String[] args) {
//		System.out.println("convertDateToCalendar---"+convertDateToCalendar(getCurDate()).getTime());
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getCurDate(),"CN"));
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getDateMoveNDays(getCurDate(),1),"CN"));
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getDateMoveNDays(getCurDate(),2),"CN"));
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getDateMoveNDays(getCurDate(),3),"CN"));
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getDateMoveNDays(getCurDate(),4),"CN"));
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getDateMoveNDays(getCurDate(),5),"CN"));
//		System.out.println("getWeekdayOfChinese---"+getWeekdayOfChinese(getDateMoveNDays(getCurDate(),6),"CN"));
//		System.out.println("calculateDaysBetweenDate---"+calculateDaysBetweenDate("2013-04-01","2013-05-31"));
//		System.out.println("calculateMonthsBetweenDate---"+calculateMonthsBetweenDate(formatStringToDate("2015-05-19",DATE_TIME_FORMART_SHORT),formatStringToDate("2015-08-29",DATE_TIME_FORMART_SHORT)));
//		System.out.println("getDaysBetweenDateList---"+getDaysBetweenDateList(formatStringToDate("2015-05-19",DATE_TIME_FORMART_SHORT),formatStringToDate("2015-05-29",DATE_TIME_FORMART_SHORT),"ASC"));
//		System.out.println("getMonthsBetweenDateList---"+getMonthsBetweenDateList(formatStringToDate("2015-05-19",DATE_TIME_FORMART_SHORT),formatStringToDate("2015-08-29",DATE_TIME_FORMART_SHORT),"ASC"));
//		System.out.println("getDateMoveNDays---"+getDateMoveNDays(getCurDate(),1));
//		System.out.println("getDateMoveNMonths---"+getDateMoveNMonths(getCurDate(),1));
//		System.out.println("getDateMoveNYears---"+getDateMoveNYears(getCurDate(),1));
//		System.out.println("getFirstDayOfMonth---"+getFirstDayOfMonth(getCurDate()));
//		System.out.println("getLastDayOfMonth---"+getLastDayOfMonth(getCurDate()));
//		System.out.println("getDaysOfMonth---"+getDaysOfMonth(2014,5));
//		System.out.println("getDaysOfMonthList---"+getDaysOfMonthList(2014,5));
//		System.out.println("getDaysOfMonthList---"+getDaysOfMonthList(new Date()));
//		System.out.println("minuteTransferHours---"+minuteTransferHours("83"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),-3),"CN"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),-2),"CN"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),-1),"CN"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),0),"CN"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),1),"CN"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),2),"CN"));
//		System.out.println("compareDateForName---"+compareDateForName(getDateMoveNDays(getCurDate(),3),"CN"));
//	}
	
	/**
	 * @Title: getCurTimestamp
	 * @Description: 返回java.sql.Timestamp
	 * @return
	 */
	public static Timestamp getCurTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 
	 * @Title: getCurSqlTime
	 * @Description: 返回java.sql.Time
	 * @return
	 *
	 */
	public static java.sql.Time getCurSqlTime() {
		return new java.sql.Time(getCurDate().getTime());
	}
	
	/**
	 * @Title: getCurSqlDate
	 * @Description: 返回当前日期 java.sql.Date
	 * @return
	 */
	public static java.sql.Date getCurSqlDate() {
		return new java.sql.Date(getCurDate().getTime());
	}
	
	/**
	 * 
	 * @Title: convertTimeStampToDate
	 * @Description: 将timestamp转换成date
	 * @param timestamp
	 * @return
	 *
	 */ 
	public static Date convertTimeStampToDate(Timestamp timestamp){
		if (timestamp != null) {
			return new Date(timestamp.getTime());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: convertSqlTimeToDate
	 * @Description: 将sql.Time转换成date
	 * @param time
	 * @return
	 *
	 */
	public static Date convertSqlTimeToDate(java.sql.Time time) {
		if (time != null) {
			return new Date(time.getTime());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: convertSqlDateToDate
	 * @Description: 将sql.Date转换成date
	 * @param date
	 * @return
	 *
	 */
	public static Date convertSqlDateToDate(java.sql.Date date) {
		if (date != null) {
			return new Date(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: convertDateToTimeStamp
	 * @Description: 将date转换成sql.Timestamp
	 * @param date
	 * @return
	 *
	 */ 
	public static Date convertDateToTimeStamp(Date date){
		if (date != null) {
			return new Timestamp(date.getTime());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: convertDateToSqlTime
	 * @Description: 将date转换成sql.Time
	 * @param date
	 * @return
	 *
	 */
	public static java.sql.Time convertDateToSqlTime(Date date) {
		if (date != null) {
			return new java.sql.Time(date.getTime());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: convertDateToSqlDate
	 * @Description: 将date转换成sql.Date
	 * @param date
	 * @return
	 *
	 */
	public static java.sql.Date convertDateToSqlDate(Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getCurrentGMTDate
	 * @Description: 当前系统时间date减去时区差值
	 * @return
	 *
	 */
	public static Date getCurrentGMTDate() {
		TimeZone defaultTimeZone = TimeZone.getDefault();
		Date date = Calendar.getInstance().getTime();
		date.setTime(date.getTime() - defaultTimeZone.getRawOffset());
		return date;
	}
	
	/**
	 * 
	 * @Title: getDayStart
	 * @Description: 获取stamp的日期，该日开始时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getDayStart(Timestamp stamp) {
		return getDayStart(stamp, 0);
	}

	/**
	 * 
	 * @Title: getDayStart
	 * @Description: 获取stamp时间daysLater天的日期，该日开始时间
	 * @param stamp
	 * @param daysLater
	 * @return
	 *
	 */
	public static Timestamp getDayStart(Timestamp stamp, int daysLater) {
		Date date = new Date(stamp.getTime());
		date = getDateFShort(getDateFShort(date));
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getPrevDayStart
	 * @Description: 获取stamp时间前一天的开始时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getPrevDayStart(Timestamp stamp) {
		return getDayStart(stamp, -1);
	}
	
	/**
	 * 
	 * @Title: getNextDayStart
	 * @Description: 获取stamp时间后一天的开始时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getNextDayStart(Timestamp stamp) {
		return getDayStart(stamp, 1);
	}
	
	/**
	 * 
	 * @Title: getDayEnd
	 * @Description: 获取stamp的日期，该日截止时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getDayEnd(Timestamp stamp) {
		return getDayEnd(stamp, 0);
	}
	
	/**
	 * 
	 * @Title: getDayEnd
	 * @Description: 获取stamp时间daysLater天的日期，该日截止时间
	 * @param stamp
	 * @param daysLater
	 * @return
	 *
	 */
	public static Timestamp getDayEnd(Timestamp stamp, int daysLater) {
		Date date = new Date(stamp.getTime());
		date = getDateFALL(getDateFShort(date)+" 23:59:59.999");
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getPrevDayEnd
	 * @Description: 获取stamp时间前一天的日截止时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getPrevDayEnd(Timestamp stamp) {
		return getDayEnd(stamp, -1);
	}
	
	/**
	 * 
	 * @Title: getNextDayEnd
	 * @Description: 获取stamp时间后一天的截止时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getNextDayEnd(Timestamp stamp) {
		return getDayEnd(stamp, 1);
	}
	
	/**
	 * 
	 * @Title: getDayLater
	 * @Description: 时间daysLater天的时间
	 * @param stamp
	 * @param daysLater
	 * @return
	 *
	 */
	public static Timestamp getDayLater(Timestamp stamp,int daysLater){
		Date date = new Date(stamp.getTime());
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		return new Timestamp(tempCal.getTime().getTime());
	}
	
	/**
	 * 
	 * @Title: getMonthStart
	 * @Description: 获取stamp的日期，该月开始时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getMonthStart(Timestamp stamp) {
		return getMonthStart(stamp, 0);
	}

	/**
	 * 
	 * @Title: getMonthStart
	 * @Description: 获取stamp时间monthsLater月的日期，该月开始时间
	 * @param stamp
	 * @param monthsLater
	 * @return
	 *
	 */
	public static Timestamp getMonthStart(Timestamp stamp, int monthsLater) {
		Date date = new Date(stamp.getTime());
		date = getDateFShort(getDateFShort(date));
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.MONTH, monthsLater);
		tempCal.set(Calendar.DAY_OF_MONTH, tempCal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getPrevMonthStart
	 * @Description: 获取stamp时间前一月的开始时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getPrevMonthStart(Timestamp stamp) {
		return getMonthStart(stamp, -1);
	}
	
	/**
	 * 
	 * @Title: getNextMonthStart
	 * @Description: 获取stamp时间后一月的开始时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getNextMonthStart(Timestamp stamp) {
		return getMonthStart(stamp, 1);
	}
	
	/**
	 * 
	 * @Title: getMonthEnd
	 * @Description: 获取stamp的日期，该月截止时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getMonthEnd(Timestamp stamp) {
		return getMonthEnd(stamp, 0);
	}
	
	/**
	 * 
	 * @Title: getMonthEnd
	 * @Description: 获取stamp时间monthsLater月的日期，该月截止时间
	 * @param stamp
	 * @param monthsLater
	 * @return
	 *
	 */
	public static Timestamp getMonthEnd(Timestamp stamp, int monthsLater) {
		Date date = new Date(stamp.getTime());
		date = getDateFALL(getDateFShort(date)+" 23:59:59.999");
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.MONTH, monthsLater);
		tempCal.set(Calendar.DAY_OF_MONTH, tempCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getPrevMonthEnd
	 * @Description: 获取stamp时间前一月的日截止时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getPrevMonthEnd(Timestamp stamp) {
		return getMonthEnd(stamp, -1);
	}
	
	/**
	 * 
	 * @Title: getNextMonthEnd
	 * @Description: 获取stamp时间后一月的截止时间
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getNextMonthEnd(Timestamp stamp) {
		return getMonthEnd(stamp, 1);
	}
	
	/**
	 * 
	 * @Title: getMonthLater
	 * @Description: 时间monthsLater月的时间
	 * @param stamp
	 * @param monthsLater
	 * @return
	 *
	 */
	public static Timestamp getMonthLater(Timestamp stamp,int monthsLater){
		Date date = new Date(stamp.getTime());
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.MONTH, monthsLater);
		return new Timestamp(tempCal.getTime().getTime());
	}
	
//	public static void main(String[] args) {
//		System.out.println("Date---"+new Date());
//		System.out.println("getCurTimestamp"+getCurTimestamp());
//		System.out.println("getCurSqlTime"+getCurSqlTime());//*注意16:42:41
//		System.out.println("convertDateToSqlTime"+convertSqlTimeToDate(getCurSqlTime()));
//		System.out.println("getCurSqlDate"+getCurSqlDate());//*注意2015-06-02
//		System.out.println("convertDateToSqlDate"+convertSqlDateToDate(getCurSqlDate()));
//		System.out.println("convertTimeStampToDate"+convertTimeStampToDate(getCurTimestamp()));
//		System.out.println("convertSqlTimeToDate"+convertSqlTimeToDate(convertDateToSqlTime(new Date())));
//		System.out.println("convertSqlDateToDate"+convertSqlDateToDate(convertDateToSqlDate(new Date())));
//		System.out.println("convertDateToTimeStamp"+convertDateToTimeStamp(new Date()));
//		System.out.println("convertDateToSqlTime"+convertDateToSqlTime(new Date()));//*注意16:42:41
//		System.out.println("convertDateToSqlTime"+convertSqlTimeToDate(convertDateToSqlTime(new Date())));
//		System.out.println("convertDateToSqlDate"+convertDateToSqlDate(new Date()));//*注意2015-06-02
//		System.out.println("convertDateToSqlDate"+convertSqlDateToDate(convertDateToSqlDate(new Date())));
//		System.out.println("getCurrentGMTDate---"+getCurrentGMTDate());
//		System.out.println("getDayStart---"+getDayStart(getCurTimestamp()));
//		System.out.println("getDayStart---"+getDayStart(getCurTimestamp(),2));
//		System.out.println("getNextDayStart---"+getNextDayStart(getCurTimestamp()));
//		System.out.println("getPrevDayStart---"+getPrevDayStart(getCurTimestamp()));
//		System.out.println("getDayEnd---"+getDayEnd(getCurTimestamp()));
//		System.out.println("getDayEnd---"+getDayEnd(getCurTimestamp(),2));
//		System.out.println("getNextDayEnd---"+getNextDayEnd(getCurTimestamp()));
//		System.out.println("getPrevDayEnd---"+getPrevDayEnd(getCurTimestamp()));
//		System.out.println("getDayLater---"+getDayLater(getCurTimestamp(),0));
//		System.out.println("getMonthStart---"+getMonthStart(getCurTimestamp()));
//		System.out.println("getMonthStart---"+getMonthStart(getCurTimestamp(),2));
//		System.out.println("getNextMonthStart---"+getNextMonthStart(getCurTimestamp()));
//		System.out.println("getPrevMonthStart---"+getPrevMonthStart(getCurTimestamp()));
//		System.out.println("getMonthEnd---"+getMonthEnd(getCurTimestamp()));
//		System.out.println("getMonthEnd---"+getMonthEnd(getCurTimestamp(),2));
//		System.out.println("getNextMonthEnd---"+getNextMonthEnd(getCurTimestamp()));
//		System.out.println("getPrevMonthEnd---"+getPrevMonthEnd(getCurTimestamp()));
//		System.out.println("getMonthLater---"+getMonthLater(getCurTimestamp(),1));
//	}

	/**
	 * 
	 * @Title: toDate
	 * @Description: String to date 
	 * @param dateTime yyyy-MM-dd HH:mm:ss
	 * @return
	 *
	 */
	public static Date toDate(String dateTime) {
		try {
			return new Date(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.SIMPLIFIED_CHINESE)
					.parse(dateTime).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
 
	/**
	 * 
	 * @Title: toDate
	 * @Description: 转换日期
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 *
	 */
	public static Date toDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.set(year, month - 1, day, hour, minute, second);
			return new Date(calendar.getTime().getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: toDateString
	 * @Description: yyyy-MM-dd
	 * @param date
	 * @return
	 *
	 */
	public static String toDateString(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		String monthStr;
		String dayStr;
		String yearStr;

		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		if (day < 10) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		yearStr = "" + year;
		// return monthStr + "/" + dayStr + "/" + yearStr;
		return yearStr + "-" + monthStr + "-" + dayStr;
	}
 
	/**
	 * 
	 * @Title: toTimeString
	 * @Description: HH:mm:ss
	 * @param date
	 * @return
	 *
	 */
	public static String toTimeString(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return toTimeString(
			calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.SECOND));
	}
	
	/**
	 * 
	 * @Title: toTimeString
	 * @Description: HH:mm:ss
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 *
	 */
	public static String toTimeString(int hour, int minute, int second) {
		String hourStr;
		String minuteStr;
		String secondStr;

		if (hour < 10) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}
		if (minute < 10) {
			minuteStr = "0" + minute;
		} else {
			minuteStr = "" + minute;
		}
		if (second < 10) {
			secondStr = "0" + second;
		} else {
			secondStr = "" + second;
		}
		if (second == 0) {
			return hourStr + ":" + minuteStr;
		} else {
			return hourStr + ":" + minuteStr + ":" + secondStr;
		}
	}
 
	/**
	 * 
	 * @Title: toDateTimeString
	 * @Description: yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 *
	 */
	public static String toDateTimeString(Date date) {
		if (date == null) {
			return null;
		}
		String dateString = toDateString(date);
		String timeString = toTimeString(date);

		if ((dateString != null) && (timeString != null)) {
			return dateString + " " + timeString;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: toDateTimeString
	 * @Description: yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 *
	 */
	public static String toDateTimeString(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		return toDateTimeString(new Date(date.getTime()));
	}

	public static String getDay(Date date) {
		String str = toDateTimeString(date);
		return getDay(str);
	}
	
	public static String getDay(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getDay(str);
	}
	
	public static String getDay(String date) {
		if ((date == null) || date.equals("")) {
			return "-1";
		}
		int dateSlash1 = date.indexOf(DATEPARTITION);
		int dateSlash2 = date.lastIndexOf(DATEPARTITION);
		if(dateSlash1<0 || dateSlash1 == dateSlash2) return "-1";
		return date.substring(dateSlash2 + 1, date.length() < dateSlash2 + 3 ? date.length() : dateSlash2 + 3).trim();
	}
	
	public static String getCurWeek() {
		return getWeek(new Date());
	}
	
	public static String getWeek(Date date) {
		if (date == null) {
			return "-1";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNum = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekNum == 0) {
			weekNum = 7;
		}
		return String.valueOf(weekNum);
	}
 
	public static String getWeek(java.sql.Date date) {
		if (date == null) {
			return "-1";
		}
		return getWeek(new Date(date.getTime()));
	}
 
	public static String getWeek(String date) {
		return getWeek(getDateFShort(date));
	}
	
	public static String getMonth(Date date) {
		String str = toDateTimeString(date);
		return getMonth(str);
	}
	
	public static String getMonth(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getMonth(str);
	}
	
	private static String getMonth(String date) {
		if ((date == null) || date.equals("")) {
			return "-1";
		}
		int dateSlash1 = date.indexOf(DATEPARTITION);
		int dateSlash2 = date.lastIndexOf(DATEPARTITION);
		if(dateSlash1<0) return "-1";
		if (dateSlash1 == dateSlash2) {
			return date.substring(dateSlash1 + 1, date.length() < dateSlash2 + 3 ? date.length() : dateSlash2 + 3).trim();
		}
		return date.substring(dateSlash1 + 1, dateSlash2);
	}
	
	public static String getQuarter(Date date) {
		return getQuarterInt(date)+"";
	}
 
	public static String getQuarter(java.sql.Date date) {
		return getQuarterInt(date)+"";
	}

	public static String getQuarter(String date) {
		return getQuarterInt(date)+"";
	}
	
	public static int getQuarterInt(Date date) {
		String str = toDateTimeString(date);
		return getQuarterInt(str);
	}
	
	public static int getQuarterInt(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getQuarterInt(str);
	}
	
	public static int getQuarterInt(String date) {
		int month = Integer.parseInt(getMonth(date));
		if (month < 4) {
			return 1;
		} else if (month < 7) {
			return 2;
		} else if (month < 10) {
			return 3;
		} else if (month < 13) {
			return 4;
		} else {
			return -1;
		}
	}
	
	public static String getQuarter(String year, String month, boolean isNow) {
		return getQuarterInt(year, month, isNow)+"";
	}

	public static int getQuarterInt(String year, String month, boolean isNow) {
		if ((year == null) || (month == null) || "".equals(year) || "".equals(month)) {
			if (isNow) {
				return getQuarterInt(getCurDate());
			} else {
				return -1;
			}
		}
		String str = new StringBuffer(year)
				.append(DATEPARTITION)
				.append(month)
				.append(DATEPARTITION)
				.append("01")
				.toString();
		return getQuarterInt(str);
	}
	
	public static String getYear(Date date) {
		String str = toDateTimeString(date);
		return getYear(str);
	}
	
	public static String getYear(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getYear(str);
	}
	
	public static String getYear(String date) {
		if ((date == null) || date.equals("")) {
			return "-1";
		}
		int dateSlash1 = date.indexOf(DATEPARTITION);
		if(dateSlash1<0) return "-1";
		return date.substring(0, dateSlash1);
	}
	
	public static String getHour(Date date) {
		String str = toDateTimeString(date);
		return getHour(str);
	}
	
	public static String getHour(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getHour(str);
	}
	
	public static String getHour(String dateTime) {
		if ((dateTime == null) || dateTime.equals("")) {
			return "-1";
		}
		int dateSlash1 = dateTime.indexOf(":");
		if(dateSlash1<0) return "-1";
		return dateTime.substring(dateSlash1 - 2 < 0 ? 0 : dateSlash1 - 2, dateSlash1).trim();
	}

	public static String getMinute(Date date) {
		String str = toDateTimeString(date);
		return getMinute(str);
	}
 
	public static String getMinute(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getMinute(str);
	}
	
	public static String getMinute(String dateTime) {
		if ((dateTime == null) || dateTime.equals("")) {
			return "-1";
		}
		int dateSlash1 = dateTime.indexOf(":");
		int dateSlash2 = dateTime.lastIndexOf(":");
		if(dateSlash1<0) return "-1";
		if (dateSlash1 == dateSlash2) {
			return dateTime.substring(dateSlash1 + 1, dateTime.length() < dateSlash2 + 3 ? dateTime.length() : dateSlash2 + 3).trim();
		}
		return dateTime.substring(dateSlash1 + 1, dateSlash2).trim();
	}

	public static String getSecond(Date date) {
		String str = toDateTimeString(date);
		return getSecond(str);
	}
	
	public static String getSecond(java.sql.Date date) {
		String str = toDateTimeString(date);
		return getSecond(str);
	}
	
	public static String getSecond(String dateTime) {
		if ((dateTime == null) || dateTime.equals("")) {
			return "-1";
		}
		int dateSlash1 = dateTime.indexOf(":");
		int dateSlash2 = dateTime.lastIndexOf(":");
		if(dateSlash1<0 || dateSlash1 == dateSlash2) return "-1";
		return dateTime.substring(dateSlash2 + 1, dateTime.length() < dateSlash2 + 3 ? dateTime.length() : dateSlash2 + 3).trim();
	}
 
	public static Timestamp getTimestamp(long time) {
		return new Timestamp(time);
	}

	public static String getTimestampStr(long time) {
		return new Timestamp(time).toString();
	}
 
//	public static void main(String[] args) {
//		System.out.println("toDate---"+toDate(getCurDateFHigh()));
//		System.out.println("toDate---"+toDate(2014,5,2,15,23,44));
//		System.out.println("toDateString---"+toDateString(getCurDate()));
//		System.out.println("toTimeString---"+toTimeString(getCurDate()));
//		System.out.println("toDateTimeString---"+toDateTimeString(getCurDate()));
//		System.out.println("getDay---"+getDay(new Date()));
//		System.out.println("getDay---"+getDay(getCurSqlDate()));
//		System.out.println("getDay---"+getDay(getCurDateFShort()));
//		System.out.println("getCurWeek---"+getCurWeek());
//		System.out.println("getWeek---"+getWeek(new Date()));
//		System.out.println("getWeek---"+getWeek(getCurSqlDate()));
//		System.out.println("getWeek---"+getWeek(getCurDateFShort()));
//		System.out.println("getMonth---"+getMonth(new Date()));
//		System.out.println("getMonth---"+getMonth(getCurSqlDate()));
//		System.out.println("getMonth---"+getMonth(getCurDateFShort()));
//		System.out.println("getQuarter---"+getQuarter(new Date()));
//		System.out.println("getQuarter---"+getQuarter(getCurSqlDate()));
//		System.out.println("getQuarter---"+getQuarter(getCurDateFShort()));
//		System.out.println("getQuarter---"+getQuarter("2015", "06", true));
//		System.out.println("getQuarterInt---"+getQuarterInt(new Date()));
//		System.out.println("getQuarterInt---"+getQuarterInt(getCurSqlDate()));
//		System.out.println("getQuarterInt---"+getQuarterInt(getCurDateFShort()));
//		System.out.println("getQuarterInt---"+getQuarterInt("2015", "06", true));
//		System.out.println("getYear---"+getYear(new Date()));
//		System.out.println("getYear---"+getYear(getCurSqlDate()));
//		System.out.println("getYear---"+getYear(getCurDateFShort()));
//		System.out.println("getHour---"+getHour(new Date()));
//		System.out.println("getHour---"+getHour(getCurSqlDate()));
//		System.out.println("getHour---"+getHour(getCurDateFHigh()));
//		System.out.println("getMinute---"+getMinute(new Date()));
//		System.out.println("getMinute---"+getMinute(getCurSqlDate()));
//		System.out.println("getMinute---"+getMinute(getCurDateFHigh()));
//		System.out.println("getSecond---"+getSecond(new Date()));
//		System.out.println("getSecond---"+getSecond(getCurSqlDate()));
//		System.out.println("getSecond---"+getSecond(getCurDateFHigh()));
//		System.out.println("getTimestamp---"+getTimestamp(new Date().getTime()));
//		System.out.println("getTimestampStr---"+getTimestampStr(new Date().getTime()));
//	}
	
	/**
	 * 
	 * @Title: getExpendTime
	 * @Description: 时间相差毫秒数startTime-endTime
	 * @param startTime
	 * @param endTime
	 * @return
	 *
	 */
	public static long getExpendTime(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date startDate;
		Date endDate;
		long time = 0;
		try {
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);
			time = getExpendTime(startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public static long getExpendTime(Date startDate, Date endDate) {
		long time = 0;
		time = startDate.getTime() - endDate.getTime();
		return time;
	}

	public static long countStart(String desc) {
		StringBuffer bf = new StringBuffer();
		long time = new Date().getTime();
		logger.debug(bf.append(new Timestamp(time)).append("[").append(desc).append("]").toString());
		return time;
	}
	
	public static void countEnd(String desc, long startTime) {
		StringBuffer bf = new StringBuffer();
		long time = new Date().getTime();
		logger.debug(bf.append(new Timestamp(time)).append("[").append(desc).append("]").toString());
		long sec = (time - startTime) / (1000 * 60);
		long miao = (time - startTime) % (1000 * 60) / 1000;
		long haomiao = (time - startTime) % (1000 * 60) % 1000;
		bf = new StringBuffer();
		logger.debug(bf.append(desc).append("[").append(sec+"-"+miao+"-"+haomiao).append("]").toString());
	}
	
	/**
	 * 
	 * @Title: getWestWeekStart
	 * @Description: 星期日
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getWestWeekStart(Timestamp stamp) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(getDayStart(stamp).getTime()));
		tempCal.set(Calendar.DAY_OF_WEEK, tempCal.getActualMinimum(Calendar.DAY_OF_WEEK));
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getWestWeekEnd
	 * @Description: 星期六
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getWestWeekEnd(Timestamp stamp) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(getDayEnd(stamp).getTime()));
		tempCal.set(Calendar.DAY_OF_WEEK, tempCal.getActualMaximum(Calendar.DAY_OF_WEEK));
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getWeekStart
	 * @Description: 星期一
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getWeekStart(Timestamp stamp) {
		if ("7".equals(getWeek(stamp.toString()))) {
			return getNextDayStart(getWestWeekStart(getDayStart(stamp, -1)));
		} else {
			return getNextDayStart(getWestWeekStart(stamp));
		}
	}
	
	/**
	 * 
	 * @Title: getWeekEnd
	 * @Description: 星期日
	 * @param stamp
	 * @return
	 *
	 */
	public static Timestamp getWeekEnd(Timestamp stamp) {
		if ("7".equals(getWeek(stamp.toString()))) {
			return getNextDayEnd(getWestWeekEnd(getDayEnd(stamp, -1)));
		} else {
			return getNextDayEnd(getWestWeekEnd(stamp));
		}
	}
	
	public static Timestamp getQuarterStart(Timestamp stamp){
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(getDayStart(stamp).getTime()));
		tempCal.set(Calendar.MONTH, (getQuarterInt(stamp)-1)*3);
		tempCal.set(Calendar.DAY_OF_MONTH, tempCal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new Timestamp(tempCal.getTime().getTime());
	}
	
	public static Timestamp getQuarterEnd(Timestamp stamp){
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(getDayEnd(stamp).getTime()));
		tempCal.set(Calendar.MONTH, getQuarterInt(stamp)*3-1);
		tempCal.set(Calendar.DAY_OF_MONTH, tempCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Timestamp(tempCal.getTime().getTime());
	}
 
	public static Timestamp getYearStart(Timestamp stamp) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(getDayStart(stamp).getTime()));
		tempCal.set(Calendar.DAY_OF_YEAR, tempCal.getActualMinimum(Calendar.DAY_OF_YEAR));
		return new Timestamp(tempCal.getTime().getTime());
	}
 
	public static Timestamp getYearEnd(Timestamp stamp) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(getDayEnd(stamp).getTime()));
		tempCal.set(Calendar.DAY_OF_YEAR, tempCal.getActualMaximum(Calendar.DAY_OF_YEAR));
		return new Timestamp(tempCal.getTime().getTime());
	}

	/**
	 * 
	 * @Title: getWeekOfMonth
	 * @Description: 该日期是当月第几周(星期一是每周开始)
	 * @param date
	 * @return
	 *
	 */
	public static int getWeekOfMonth(Date date) {
		if (date == null) {
			date = new Date();
		}
		int weekNum = Integer.parseInt(getWeek(date));
		int dayNum = Integer.parseInt(getDay(date));
		if (dayNum - weekNum<= 0) {
			return 1;
		} else {
			return (((dayNum - weekNum-1)) / 7) + 2;
		}
	}
	
	public static int getWeekOfMonthFormDate(String date) {
		return getWeekOfMonth(toDate(date + " 00:00:00"));
	}
	
	public static int getWeekOfMonthFormDateTime(String dateTime) {
		return getWeekOfMonth(toDate(dateTime));
	}
	
	public static int getWeekOfMonth() {
		return getWeekOfMonth(null);
	}
	
//	public static void main(String[] args) {
//		System.out.println("getExpendTime---"+getExpendTime("2015-06-02 21:00:00.000","2015-06-02 21:03:00.000"));
//		long startTime = countStart("countStart");
//		countEnd("countEnd",startTime);
//		System.out.println("getWestWeekStart---"+getWestWeekStart(getCurTimestamp()));
//		System.out.println("getWestWeekEnd---"+getWestWeekEnd(getCurTimestamp()));
//		System.out.println("getWeekStart---"+getWeekStart(getCurTimestamp()));
//		System.out.println("getWeekEnd---"+getWeekEnd(getCurTimestamp()));
//		System.out.println("getYearStart---"+getYearStart(getCurTimestamp()));
//		System.out.println("getYearEnd---"+getYearEnd(getCurTimestamp()));
//		System.out.println("getQuarterStart1---"+getQuarterStart(getTimestamp(getDateFShort("2015-02-01").getTime())));
//		System.out.println("getQuarterEnd1---"+getQuarterEnd(getTimestamp(getDateFShort("2015-02-01").getTime())));
//		System.out.println("getQuarterStart2---"+getQuarterStart(getTimestamp(getDateFShort("2015-05-01").getTime())));
//		System.out.println("getQuarterEnd2---"+getQuarterEnd(getTimestamp(getDateFShort("2015-05-01").getTime())));
//		System.out.println("getQuarterStart3---"+getQuarterStart(getTimestamp(getDateFShort("2015-08-01").getTime())));
//		System.out.println("getQuarterEnd3---"+getQuarterEnd(getTimestamp(getDateFShort("2015-08-01").getTime())));
//		System.out.println("getQuarterStart4---"+getQuarterStart(getTimestamp(getDateFShort("2015-10-01").getTime())));
//		System.out.println("getQuarterEnd4---"+getQuarterEnd(getTimestamp(getDateFShort("2015-10-01").getTime())));
//		Date date = getDateFShort("2015-01-01");
//		for(int i=0;i<365;i++){
//			System.out.println("getWeekOfMonthFormDate---"+getDateFShort(date)+"---"+getWeekOfMonthFormDate(getDateFShort(date)));
//			date = getDateMoveNDays(date, 1);
//		}
//		Date date = new Date();
//		date.setTime(0);
//		System.out.println(date);
//	}
}
