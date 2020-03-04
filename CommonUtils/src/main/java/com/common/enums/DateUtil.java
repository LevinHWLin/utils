package com.common.enums;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {  
	  
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();  
      
    private static final Object object = new Object();  
      
    /**
     * 获取SimpleDateFormat
     * 
     * @param pattern
     *            日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException
     *             异常：非法日期格�?
     */  
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {  
        SimpleDateFormat dateFormat = threadLocal.get();  
        if (dateFormat == null) {  
            synchronized (object) {  
                if (dateFormat == null) {  
                    dateFormat = new SimpleDateFormat(pattern);  
                    dateFormat.setLenient(false);  
                    threadLocal.set(dateFormat);  
                }  
            }  
        }  
        dateFormat.applyPattern(pattern);  
        return dateFormat;  
    }  
  
    /**
     * 获取日期中的某数值�?�如获取月份
     * 
     * @param date
     *            日期
     * @param dateType
     *            日期格式
     * @return 数�??
     */  
    private static int getInteger(Date date, int dateType) {  
        int num = 0;  
        Calendar calendar = Calendar.getInstance();  
        if (date != null) {  
            calendar.setTime(date);  
            num = calendar.get(dateType);  
        }  
        return num;  
    }  
  
    /**
     * 增加日期中某类型的某数�?��?�如增加日期
     * 
     * @param date
     *            日期字符�?
     * @param dateType
     *            类型
     * @param amount
     *            数�??
     * @return 计算后日期字符串
     */  
    private static String addInteger(String date, int dateType, int amount) {  
        String dateString = null;  
        DateStyle dateStyle = getDateStyle(date);  
        if (dateStyle != null) {  
            Date myDate = StringToDate(date, dateStyle);  
            myDate = addInteger(myDate, dateType, amount);  
            dateString = DateToString(myDate, dateStyle);  
        }  
        return dateString;  
    }  
  
    /**
     * 增加日期中某类型的某数�?��?�如增加日期
     * 
     * @param date
     *            日期
     * @param dateType
     *            类型
     * @param amount
     *            数�??
     * @return 计算后日�?
     */  
    private static Date addInteger(Date date, int dateType, int amount) {  
        Date myDate = null;  
        if (date != null) {  
            Calendar calendar = Calendar.getInstance();  
            calendar.setTime(date);  
            calendar.add(dateType, amount);  
            myDate = calendar.getTime();  
        }  
        return myDate;  
    }  
  
    /**
     * 获取精确的日�?
     * 
     * @param timestamps
     *            时间long集合
     * @return 日期
     */  
    private static Date getAccurateDate(List<Long> timestamps) {  
        Date date = null;  
        long timestamp = 0;  
        Map<Long, long[]> map = new HashMap<Long, long[]>();  
        List<Long> absoluteValues = new ArrayList<Long>();  
  
        if (timestamps != null && timestamps.size() > 0) {  
            if (timestamps.size() > 1) {  
                for (int i = 0; i < timestamps.size(); i++) {  
                    for (int j = i + 1; j < timestamps.size(); j++) {  
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));  
                        absoluteValues.add(absoluteValue);  
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };  
                        map.put(absoluteValue, timestampTmp);  
                    }  
                }  
  
                // 有可能有相等的情况�?�如2012-11�?2012-11-01。时间戳是相等的。此时minAbsoluteValue�?0
                // 因此不能将minAbsoluteValue取默认�??0
                long minAbsoluteValue = -1;  
                if (!absoluteValues.isEmpty()) {  
                    minAbsoluteValue = absoluteValues.get(0);  
                    for (int i = 1; i < absoluteValues.size(); i++) {  
                        if (minAbsoluteValue > absoluteValues.get(i)) {  
                            minAbsoluteValue = absoluteValues.get(i);  
                        }  
                    }  
                }  
  
                if (minAbsoluteValue != -1) {  
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);  
  
                    long dateOne = timestampsLastTmp[0];  
                    long dateTwo = timestampsLastTmp[1];  
                    if (absoluteValues.size() > 1) {  
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;  
                    }  
                }  
            } else {  
                timestamp = timestamps.get(0);  
            }  
        }  
  
        if (timestamp != 0) {  
            date = new Date(timestamp);  
        }  
        return date;  
    }  
  
    /**
     * 判断字符串是否为日期字符�?
     * 
     * @param date
     *            日期字符�?
     * @return true or false
     */  
    public static boolean isDate(String date) {  
        boolean isDate = false;  
        if (date != null) {  
            if (getDateStyle(date) != null) {  
                isDate = true;  
            }  
        }  
        return isDate;  
    }  
  
    /**
     * 获取日期字符串的日期风格。失敗返回null�?
     * 
     * @param date
     *            日期字符�?
     * @return 日期风格
     */  
    public static DateStyle getDateStyle(String date) {  
        DateStyle dateStyle = null;  
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();  
        List<Long> timestamps = new ArrayList<Long>();  
        for (DateStyle style : DateStyle.values()) {  
            if (style.isShowOnly()) {  
                continue;  
            }  
            Date dateTmp = null;  
            if (date != null) {  
                try {  
                    ParsePosition pos = new ParsePosition(0);  
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);  
                    if (pos.getIndex() != date.length()) {  
                        dateTmp = null;  
                    }  
                } catch (Exception e) {  
                }  
            }  
            if (dateTmp != null) {  
                timestamps.add(dateTmp.getTime());  
                map.put(dateTmp.getTime(), style);  
            }  
        }  
        Date accurateDate = getAccurateDate(timestamps);  
        if (accurateDate != null) {  
            dateStyle = map.get(accurateDate.getTime());  
        }  
        return dateStyle;  
    }  
  
    /**
     * 将日期字符串转化为日期�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @return 日期
     */  
    public static Date StringToDate(String date) {  
        DateStyle dateStyle = getDateStyle(date);  
        return StringToDate(date, dateStyle);  
    }  
  
    /**
     * 将日期字符串转化为日期�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @param pattern
     *            日期格式
     * @return 日期
     */  
    public static Date StringToDate(String date, String pattern) {  
        Date myDate = null;  
        if (date != null) {  
            try {  
                myDate = getDateFormat(pattern).parse(date);  
            } catch (Exception e) {  
            }  
        }  
        return myDate;  
    }  
  
    /**
     * 将日期字符串转化为日期�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @param dateStyle
     *            日期风格
     * @return 日期
     */  
    public static Date StringToDate(String date, DateStyle dateStyle) {  
        Date myDate = null;  
        if (dateStyle != null) {  
            myDate = StringToDate(date, dateStyle.getValue());  
        }  
        return myDate;  
    }  
  
    /**
     * 将日期转化为日期字符串�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return 日期字符�?
     */  
    public static String DateToString(Date date, String pattern) {  
        String dateString = null;  
        if (date != null) {  
            try {  
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {  
            }  
        }  
        return dateString;  
    }  
  
    /**
     * 将日期转化为日期字符串�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param dateStyle
     *            日期风格
     * @return 日期字符�?
     */  
    public static String DateToString(Date date, DateStyle dateStyle) {  
        String dateString = null;  
        if (dateStyle != null) {  
            dateString = DateToString(date, dateStyle.getValue());  
        }  
        return dateString;  
    }  
  
    /**
     * 将日期字符串转化为另�?日期字符串�?�失败返回null�?
     * 
     * @param date
     *            旧日期字符串
     * @param newPattern
     *            新日期格�?
     * @return 新日期字符串
     */  
    public static String StringToString(String date, String newPattern) {  
        DateStyle oldDateStyle = getDateStyle(date);  
        return StringToString(date, oldDateStyle, newPattern);  
    }  
  
    /**
     * 将日期字符串转化为另�?日期字符串�?�失败返回null�?
     * 
     * @param date
     *            旧日期字符串
     * @param newDateStyle
     *            新日期风�?
     * @return 新日期字符串
     */  
    public static String StringToString(String date, DateStyle newDateStyle) {  
        DateStyle oldDateStyle = getDateStyle(date);  
        return StringToString(date, oldDateStyle, newDateStyle);  
    }  
  
    /**
     * 将日期字符串转化为另�?日期字符串�?�失败返回null�?
     * 
     * @param date
     *            旧日期字符串
     * @param olddPattern
     *            旧日期格�?
     * @param newPattern
     *            新日期格�?
     * @return 新日期字符串
     */  
    public static String StringToString(String date, String olddPattern, String newPattern) {  
        return DateToString(StringToDate(date, olddPattern), newPattern);  
    }  
  
    /**
     * 将日期字符串转化为另�?日期字符串�?�失败返回null�?
     * 
     * @param date
     *            旧日期字符串
     * @param olddDteStyle
     *            旧日期风�?
     * @param newParttern
     *            新日期格�?
     * @return 新日期字符串
     */  
    public static String StringToString(String date, DateStyle olddDteStyle, String newParttern) {  
        String dateString = null;  
        if (olddDteStyle != null) {  
            dateString = StringToString(date, olddDteStyle.getValue(), newParttern);  
        }  
        return dateString;  
    }  
  
    /**
     * 将日期字符串转化为另�?日期字符串�?�失败返回null�?
     * 
     * @param date
     *            旧日期字符串
     * @param olddPattern
     *            旧日期格�?
     * @param newDateStyle
     *            新日期风�?
     * @return 新日期字符串
     */  
    public static String StringToString(String date, String olddPattern, DateStyle newDateStyle) {  
        String dateString = null;  
        if (newDateStyle != null) {  
            dateString = StringToString(date, olddPattern, newDateStyle.getValue());  
        }  
        return dateString;  
    }  
  
    /**
     * 将日期字符串转化为另�?日期字符串�?�失败返回null�?
     * 
     * @param date
     *            旧日期字符串
     * @param olddDteStyle
     *            旧日期风�?
     * @param newDateStyle
     *            新日期风�?
     * @return 新日期字符串
     */  
    public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {  
        String dateString = null;  
        if (olddDteStyle != null && newDateStyle != null) {  
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());  
        }  
        return dateString;  
    }  
  
    /**
     * 增加日期的年份�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负�?
     * @return 增加年份后的日期字符�?
     */  
    public static String addYear(String date, int yearAmount) {  
        return addInteger(date, Calendar.YEAR, yearAmount);  
    }  
  
    /**
     * 增加日期的年份�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负�?
     * @return 增加年份后的日期
     */  
    public static Date addYear(Date date, int yearAmount) {  
        return addInteger(date, Calendar.YEAR, yearAmount);  
    }  
  
    /**
     * 增加日期的月份�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param monthAmount
     *            增加数量。可为负�?
     * @return 增加月份后的日期字符�?
     */  
    public static String addMonth(String date, int monthAmount) {  
        return addInteger(date, Calendar.MONTH, monthAmount);  
    }  
  
    /**
     * 增加日期的月份�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param monthAmount
     *            增加数量。可为负�?
     * @return 增加月份后的日期
     */  
    public static Date addMonth(Date date, int monthAmount) {  
        return addInteger(date, Calendar.MONTH, monthAmount);  
    }  
  
    /**
     * 增加日期的天数�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @param dayAmount
     *            增加数量。可为负�?
     * @return 增加天数后的日期字符�?
     */  
    public static String addDay(String date, int dayAmount) {  
        return addInteger(date, Calendar.DATE, dayAmount);  
    }  
  
    /**
     * 增加日期的天数�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param dayAmount
     *            增加数量。可为负�?
     * @return 增加天数后的日期
     */  
    public static Date addDay(Date date, int dayAmount) {  
        return addInteger(date, Calendar.DATE, dayAmount);  
    }  
  
    /**
     * 增加日期的小时�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @param hourAmount
     *            增加数量。可为负�?
     * @return 增加小时后的日期字符�?
     */  
    public static String addHour(String date, int hourAmount) {  
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);  
    }  
  
    /**
     * 增加日期的小时�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param hourAmount
     *            增加数量。可为负�?
     * @return 增加小时后的日期
     */  
    public static Date addHour(Date date, int hourAmount) {  
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);  
    }  
  
    /**
     * 增加日期的分钟�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @param minuteAmount
     *            增加数量。可为负�?
     * @return 增加分钟后的日期字符�?
     */  
    public static String addMinute(String date, int minuteAmount) {  
        return addInteger(date, Calendar.MINUTE, minuteAmount);  
    }  
  
    /**
     * 增加日期的分钟�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param dayAmount
     *            增加数量。可为负�?
     * @return 增加分钟后的日期
     */  
    public static Date addMinute(Date date, int minuteAmount) {  
        return addInteger(date, Calendar.MINUTE, minuteAmount);  
    }  
  
    /**
     * 增加日期的秒钟�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @param dayAmount
     *            增加数量。可为负�?
     * @return 增加秒钟后的日期字符�?
     */  
    public static String addSecond(String date, int secondAmount) {  
        return addInteger(date, Calendar.SECOND, secondAmount);  
    }  
  
    /**
     * 增加日期的秒钟�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @param dayAmount
     *            增加数量。可为负�?
     * @return 增加秒钟后的日期
     */  
    public static Date addSecond(Date date, int secondAmount) {  
        return addInteger(date, Calendar.SECOND, secondAmount);  
    }  
  
    /**
     * 获取日期的年份�?�失败返�?0�?
     * 
     * @param date
     *            日期字符�?
     * @return 年份
     */  
    public static int getYear(String date) {  
        return getYear(StringToDate(date));  
    }  
  
    /**
     * 获取日期的年份�?�失败返�?0�?
     * 
     * @param date
     *            日期
     * @return 年份
     */  
    public static int getYear(Date date) {  
        return getInteger(date, Calendar.YEAR);  
    }  
  
    /**
     * 获取日期的月份�?�失败返�?0�?
     * 
     * @param date
     *            日期字符�?
     * @return 月份
     */  
    public static int getMonth(String date) {  
        return getMonth(StringToDate(date));  
    }  
  
    /**
     * 获取日期的月份�?�失败返�?0�?
     * 
     * @param date
     *            日期
     * @return 月份
     */  
    public static int getMonth(Date date) {  
        return getInteger(date, Calendar.MONTH) + 1;  
    }  
  
    /**
     * 获取日期的天数�?�失败返�?0�?
     * 
     * @param date
     *            日期字符�?
     * @return �?
     */  
    public static int getDay(String date) {  
        return getDay(StringToDate(date));  
    }  
  
    /**
     * 获取日期的天数�?�失败返�?0�?
     * 
     * @param date
     *            日期
     * @return �?
     */  
    public static int getDay(Date date) {  
        return getInteger(date, Calendar.DATE);  
    }  
  
    /**
     * 获取日期的小时�?�失败返�?0�?
     * 
     * @param date
     *            日期字符�?
     * @return 小时
     */  
    public static int getHour(String date) {  
        return getHour(StringToDate(date));  
    }  
  
    /**
     * 获取日期的小时�?�失败返�?0�?
     * 
     * @param date
     *            日期
     * @return 小时
     */  
    public static int getHour(Date date) {  
        return getInteger(date, Calendar.HOUR_OF_DAY);  
    }  
  
    /**
     * 获取日期的分钟�?�失败返�?0�?
     * 
     * @param date
     *            日期字符�?
     * @return 分钟
     */  
    public static int getMinute(String date) {  
        return getMinute(StringToDate(date));  
    }  
  
    /**
     * 获取日期的分钟�?�失败返�?0�?
     * 
     * @param date
     *            日期
     * @return 分钟
     */  
    public static int getMinute(Date date) {  
        return getInteger(date, Calendar.MINUTE);  
    }  
  
    /**
     * 获取日期的秒钟�?�失败返�?0�?
     * 
     * @param date
     *            日期字符�?
     * @return 秒钟
     */  
    public static int getSecond(String date) {  
        return getSecond(StringToDate(date));  
    }  
  
    /**
     * 获取日期的秒钟�?�失败返�?0�?
     * 
     * @param date
     *            日期
     * @return 秒钟
     */  
    public static int getSecond(Date date) {  
        return getInteger(date, Calendar.SECOND);  
    }  
  
    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @return 日期
     */  
    public static String getDate(String date) {  
        return StringToString(date, DateStyle.YYYY_MM_DD);  
    }  
  
    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null�?
     * 
     * @param date
     *            日期
     * @return 日期
     */  
    public static String getDate(Date date) {  
        return DateToString(date, DateStyle.YYYY_MM_DD);  
    }  
  
    /**
     * 获取日期的时间�?�默认HH:mm:ss格式。失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @return 时间
     */  
    public static String getTime(String date) {  
        return StringToString(date, DateStyle.HH_MM_SS);  
    }  
  
    /**
     * 获取日期的时间�?�默认HH:mm:ss格式。失败返回null�?
     * 
     * @param date
     *            日期
     * @return 时间
     */  
    public static String getTime(Date date) {  
        return DateToString(date, DateStyle.HH_MM_SS);  
    }  
  
    /**
     * 获取日期的星期�?�失败返回null�?
     * 
     * @param date
     *            日期字符�?
     * @return 星期
     */  
    public static Week getWeek(String date) {  
        Week week = null;  
        DateStyle dateStyle = getDateStyle(date);  
        if (dateStyle != null) {  
            Date myDate = StringToDate(date, dateStyle);  
            week = getWeek(myDate);  
        }  
        return week;  
    }  
  
    /**
     * 获取日期的星期�?�失败返回null�?
     * 
     * @param date
     *            日期
     * @return 星期
     */  
    public static Week getWeek(Date date) {  
        Week week = null;  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;  
        switch (weekNumber) {  
        case 0:  
            week = Week.SUNDAY;  
            break;  
        case 1:  
            week = Week.MONDAY;  
            break;  
        case 2:  
            week = Week.TUESDAY;  
            break;  
        case 3:  
            week = Week.WEDNESDAY;  
            break;  
        case 4:  
            week = Week.THURSDAY;  
            break;  
        case 5:  
            week = Week.FRIDAY;  
            break;  
        case 6:  
            week = Week.SATURDAY;  
            break;  
        }  
        return week;  
    }  
  
    /**
     * 获取两个日期相差的天�?
     * 
     * @param date
     *            日期字符�?
     * @param otherDate
     *            另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */  
    public static int getIntervalDays(String date, String otherDate) {  
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));  
    }  
    
    /**
     * 计算两个日期相差的秒�?
     * @param date
     * @param otherDate
     * @return
     */
    public static int getIntervalSeconds(String date, String otherDate) {  
        return getIntervalSeconds(StringToDate(date), StringToDate(otherDate));  
    }  
    
    public static int getAbsIntervalSeconds(Date date, Date otherDate) {  
        int num = -1;  
        if (date != null && otherDate != null) {  
            long time = Math.abs(date.getTime() - otherDate.getTime());  
            num = (int) (time / 1000);  
        }  
        return num;  
    }  
  
    /**
     * @param date
     *            日期
     * @param otherDate
     *            另一个日�?
     * @return 相差天数。如果失败则返回-1
     */  
    public static int getIntervalDays(Date date, Date otherDate) {  
        int num = -1;  
        Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(date), DateStyle.YYYY_MM_DD);  
        Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(otherDate), DateStyle.YYYY_MM_DD);  
        if (dateTmp != null && otherDateTmp != null) {  
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());  
            num = (int) (time / (24 * 60 * 60 * 1000));  
        }  
        return num;  
    }  
    
    public static int getIntervalSeconds(Date date, Date otherDate) {  
        int num = -1;  
        if (date != null && otherDate != null) {  
            long time = date.getTime() - otherDate.getTime();  
            num = (int) (time / 1000);  
        }  
        return num;  
    }  
    

    /**
     * 判断两个日期是否相等
     * 
     * @param date1
     * @param date2
     * @param fmt
     *            yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static boolean equals(Date date1,Date date2, String fmt)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date1).equals(sdf.format(date2));
    }
    
    /**
     * 将UTC时间转换为东八区时间
     * 
     * @param UTCTime
     * @return
     */
    public static Date utc2local(String UTCTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date UTCDate = null ;
        try {
            UTCDate = format.parse(UTCTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         
        return UTCDate ;
    }
    
    /**
     * 东八区时间转为UTC时间
     * 
     * @param GTMTime
     * @return
     */
    public static String local2utc(Date GTMTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String local = format.format(GTMTime);
        return local;
    }

    /**
     * @param date
     *            日期
     * @param otherDate
     *            另一个日�?
     * @return 相差天数。如果失败则返回-1
     */  
    public static Object getDays(Date startTime, Date eneTime) {  
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差�?
        long diff = startTime.getTime() - eneTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小�?
        long hour = diff % nd / nh;
        // 计算差多少分�?
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day;
    } 
    
    /**
     * @param date
     *            日期
     * @param otherDate
     *            另一个日�?
     * @return 相差小时。如果失败则返回-1
     */  
    public static Object getHours(Date startTime, Date eneTime) {  
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差�?
        long diff = startTime.getTime() - eneTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小�?
        long hour = diff % nd / nh;
        // 计算差多少分�?
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return hour;
    }  
    
    /**
     * @param date
     *            日期
     * @param otherDate
     *            另一个日�?
     * @return 相差分钟。如果失败则返回-1
     */  
    public static Object getMinutes(Date startTime, Date eneTime) {  
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差�?
        long diff = startTime.getTime() - eneTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小�?
        long hour = diff % nd / nh;
        // 计算差多少分�?
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return min;
    }  
    
    /**
     * 获取当前系统时间
     * 
     * @return String
     */
    public static String getNow()
    {
        return getDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    public static int compareDate(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断时间是否在某个范�?
     */
    public static boolean isBetweenRange(Date beginTime,Date endTime,Date now){
        Calendar s =Calendar.getInstance();
        s.setTime(beginTime);

        Calendar e =Calendar.getInstance();
        e.setTime(endTime);

        Calendar n =Calendar.getInstance();
        n.setTime(now);

        if(n.after(s) && n.before(e)){
            return true;
        }
        return false;
    }
}