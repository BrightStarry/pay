package com.jia.pay.common.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类DateUtil.java的实现描述： 时间日期处理工具
 *
 * @author yinerbao 2016年3月31日 下午7:33:43
 */
@SuppressWarnings("deprecation")
public class DateUtil extends DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String FMT_YYYY = "yyyy";

    public static final String FMT_Y_M = "yyyy-MM";

    public static final String FMT_Y_M_D = "yyyy-MM-dd";

    public static final String FMT_YMD = "yyyyMMdd";

    public static final String FMT_Y_M_D_H_M = "yyyy-MM-dd HH:mm";

    public static final String FMT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static final String FMT_YMDHMS = "yyyyMMddHHmmss";

    public static final String FMT_Y_M_D_H_M_S_NEW = "yyyy MM dd HH:mm:ss";

    public static final String FMT_Y_M_D_H_M_S_SSS = "yyyy-MM-dd HH:mm:ss.SSS";


    /**
     * 编号生成
     */
    public static final String fmt_y_m_d_h_m_s_sss_num = "yyyyMMddHHmmssSSS";

    /**
     * 编号生成
     */
    public static final String FMT_年月日时分 = "yyyy年MM月dd日HH时mm分";

    /**
     * 返回string的时间戳
     *
     * @param mils
     * @return
     */
    public static String fomartToNumber(long mils) {
        return format(new Date(mils), fmt_y_m_d_h_m_s_sss_num);
    }

    /**
     * 返回string的年月日时分
     *
     * @param mils
     * @return
     */
    public static String fomatymdsf(long mils) {
        return format(new Date(mils), FMT_年月日时分);
    }

    /**
     * 返回string的时间戳
     *
     * @param mils
     * @return
     */
    public static Date getDateByMilsr(long mils) {
        if (mils <= 0) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(mils));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        return parseDate(year + "-" + month + "-" + day);

    }

    /**
     * 拿到今天的日期的字符显示。
     *
     * @return
     */
    public static String getTodayDate() {
        return format(new Date(), FMT_Y_M_D);
    }

    /**
     * 拿到今天的日期的字符显示。
     *
     * @return
     */
    public static String getYYYYMMDDTodayDate() {
        return format(new Date(), FMT_YMD);
    }

    /**
     * 拿到今天的日期时间的字符显示。
     *
     * @return
     */
    public static String getYYYYMMDDHHMMSSNowTime() {
        return format(new Date(), FMT_YMDHMS);
    }

    /**
     * 拿到今天的日期+时间的字符显示。
     *
     * @return
     */
    public static String getTodayDateTime() {
        return format(new Date(), FMT_Y_M_D_H_M_S);
    }

    /**
     * 格式化制定的date
     *
     * @param date
     * @return
     */
    public static String formateDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }

        return new SimpleDateFormat(FMT_Y_M_D).format(date);
    }

    /**
     * 格式化制定的datetime
     *
     * @param date
     * @return
     */
    public static String formateDatetime(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }

        return format(date, FMT_Y_M_D_H_M_S);
    }

    public static Date addDayFroDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     * 增加指定天数，如果在当前时间之后，返回当前时间
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDayFroDateAfterNow(Date date, int days) {
        Date addDate = addDayFroDate(date, days);
        Date now = new Date();
        return addDate.after(now) ? now : addDate;
    }

    public static Date minusDayForDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -days);
        return c.getTime();
    }

    public static Date getDateMin(Date date) {
        try {
            return new SimpleDateFormat(FMT_Y_M_D_H_M_S).parse(formateDate(date) + " 00:00:00");
        } catch (Exception ex) {
            logger.error("getDateMin error, date = " + date.toLocaleString(), ex);
        }
        return date;
    }

    public static Date getDateMax(Date date) {
        try {
            return new SimpleDateFormat(FMT_Y_M_D_H_M_S).parse(formateDate(date) + " 23:59:59");
        } catch (Exception ex) {
            logger.error("getDateMax error, date = " + date.toLocaleString(), ex);
        }
        return date;
    }

    public static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat(FMT_Y_M_D_H_M_S).parse(dateStr);
        } catch (Exception ex) {
            logger.error("parseDate error, dateString = " + dateStr, ex);
        }
        return null;

    }

    /**
     * startDate加上days是否超过endate，超过返回true，否则返回false
     *
     * @param startDate
     * @param endDate
     * @param days
     * @return
     */
    public static boolean compareDate(Date startDate, Date endDate, int days) {
        // Date tempStartDate = parseDate(formateDate(startDate));
        if (startDate == null || endDate == null) {
            return false;
        }
        return addDays(startDate, days).after(endDate);
    }

    public static int getCurrentWeek() {

        return getWeekByDate(new Date());
    }

    public static int getWeekByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static String ymdFormat(Date date) {
        if (date == null) {
            return null;
        }

        return format(date, FMT_Y_M_D);
    }

    public static String ymdhmsFormatDate(Date date) {
        if (date == null) {
            return null;
        }

        return format(date, FMT_Y_M_D_H_M_S);
    }

    /**
     * 返回指定日期是哪一年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    /**
     * 使用预设Format格式化Date成FMT_Y_M_D_H_M_S
     *
     * @param date
     * @return
     */
    public static String format(long mils) {
        return format(new Date(mils), FMT_Y_M_D_H_M_S);
    }

    /**
     * 使用参数Format格式化Date成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用参数Format格式化Date成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, SimpleDateFormat pattern) {
        return date == null ? "" : pattern.format(date);
    }

    /**
     * 获取一天中最小时间
     *
     * @param date
     * @return
     */
    public static Date getMinTimeInDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }

    /**
     * 获取一天中最大时间
     *
     * @param date
     * @return
     */
    public static Date getMaxTimeInDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 59);
        date.set(Calendar.MILLISECOND, 999);
        return date.getTime();
    }

    /**
     * 返回两个时间之前的小时数 ，取整
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getHoursBetweenTwoDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }

        long hours = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60);
        hours = hours > 0L ? hours : -hours;
        return hours;
    }

    /**
     * 返回日期的年月日格式 example:2015年7月8日 14:50:00
     *
     * @param date
     * @return
     */
    public static String getYearMonthDay(Date date) {
        String dateStr = "";
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        dateStr = year + "年" + month + "月" + day + "日";

        if (hour > 0) {
            if (hour < 10) {
                dateStr += " 0" + hour;
            } else {
                dateStr += " " + hour;
            }
        }

        if (minute > 0) {
            if (minute < 10) {
                dateStr += ":0" + minute;
            } else {
                dateStr += ":" + minute;
            }
        }

        if (second > 0) {
            if (second < 10) {
                dateStr += ":0" + second;
            } else {
                dateStr += ":" + second;
            }
        }

        return dateStr;
    }

    /**
     * 返回两个日期间相差的天数（去除小时比较，只比较日期）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDaysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return null;
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * string 转 date ，根据formatPattern
     */
    @SneakyThrows
    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(dateStr);

    }

    // public static void main(String[] args) {
    // System.out.println(DateUtil.getYearMonthDay(new Date()));
    // }

}
