package indi.ayun.original_mvp.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static indi.ayun.original_mvp.utils.time.TimeFormat.day;
import static indi.ayun.original_mvp.utils.time.TimeFormat.detailDay;
import static indi.ayun.original_mvp.utils.time.TimeFormat.excelDate;
import static indi.ayun.original_mvp.utils.time.TimeFormat.fileName;
import static indi.ayun.original_mvp.utils.time.TimeFormat.FMTStr;
import static indi.ayun.original_mvp.utils.time.TimeFormat.second;
import static indi.ayun.original_mvp.utils.time.TimeFormat.tempTime;

public class Time2Date {
    /** 格式化日期的标准字符串 */
    public final static String Detail_Format = "yyyy-MM-dd HH:mm:ss";
    /**
     * 按操作系统默认国家的风格把给定的日期字符串格式化为一个Date型日期
     *
     * @param dateTimeStr 日期字符串
     * @return java.util.Date类型对象
     * @throws Exception 可能抛出的异常
     */
    public static Date parseToDate(String dateTimeStr) throws Exception {
        if (dateTimeStr == null || dateTimeStr.trim().length() < 1) {
            throw new IllegalArgumentException("参数dateTimeSt不能是null或空格串！");
        }
        int formatStrLength = FMTStr.length;
        int i = 0;

        for (i = 0; i < formatStrLength; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat(FMTStr[i]);
            try {
                return sdf.parse(dateTimeStr);
            } catch (ParseException e) {
            }
        }
        throw new Exception("日期格式不正确！");
    }
    /**
     * 按指定国家的风格把给定的日期字符串格式化为一个Date型日期
     *
     * @param dateTimeStr 日期字符串
     * @param locale  日期格式符号要被使用的语言环境
     * @return java.util.Date类型对象
     * @throws Exception 可能抛出的异常
     */
    public static Date parseToDate(String dateTimeStr, Locale locale) throws Exception {
        if (dateTimeStr == null || dateTimeStr.trim().length() < 1 || locale == null) {
            throw new IllegalArgumentException("参数dateTimeSt、locale不能是null或空格串！");
        }
        int formatStrLength = FMTStr.length;
        int i = 0;
        SimpleDateFormat sdf = null;
        for (i = 0; i < formatStrLength; i++) {
            sdf = new SimpleDateFormat(FMTStr[i], locale);
            try {
                return sdf.parse(dateTimeStr);
            } catch (ParseException e) {
            }
        }
        throw new Exception("日期格式不正确！");
    }
    /**
     * 按指定的格式和操作系统默认国家的风格把给定的日期字符串格式化为一个Date型日期
     * @param dateTime 日期毫秒字符串
     * @param formatStr 日期格式
     * @return java.util.Date类型对象
     * @throws Exception 可能抛出的异常
     */
    public static Date parseToDate(String dateTime, String formatStr) throws Exception {
        if (dateTime == null || formatStr == null || dateTime.trim().length() < 1 || formatStr.trim().length() < 1) {
            throw new IllegalArgumentException("参数dateTimeStr、formatStr不能是null或空格串！");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            throw new Exception(e);
        }
    }

    /**
     * 按指定的格式和指定国家的风格把给定的日期字符串格式化为一个Date型日期
     *
     * @param dateTime 日期字符串
     * @param formatStr 日期格式
     * @param locale 日期格式符号要被使用的语言环境
     * @return java.util.Date类型对象
     * @throws Exception
     *             可能抛出的异常
     */
    public static Date parseToDate(String dateTime , String formatStr, Locale locale) throws Exception {
        if (dateTime != null && formatStr != null && locale != null && dateTime .trim().length() > 0
                && formatStr.trim().length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr, locale);
            try {
                return sdf.parse(dateTime );
            } catch (ParseException e) {
                throw new Exception(e);
            }
        } else {
            throw new IllegalArgumentException("参数dateTimeStr、FMTStr、locale不能是null或空格串！");
        }

    }
    /**
     * 根据给出的年月和日返回一个日期型的对象
     *
     * @param year 年
     * @param month 月 ，1到12
     * @param day 日 ，1到31
     * @return java.util.Date类型对象
     * @throws Exception 可能抛出的异常
     */
    public static Date parseToDate(int year, int month, int day) throws Exception {
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            throw new IllegalArgumentException("参数不正确！");
        }
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);

        return parseToDate(yearStr + "-" + monthStr + "-" + dayStr);

    }

    /**
     * 根据给出的年月日、时分秒、返回一个对应的Date型对象
     *
     * @param year 年
     * @param month 月 ，1到12
     * @param day 日 ，1到31
     * @param h 小时，从0到23
     * @param m 分，从0到60
     * @param s 秒，从0到60
     * @return java.util.Date类型对象
     * @throws Exception 可能抛出的异常
     */
    public static Date parseToDate(int year, int month, int day, int h, int m, int s) throws Exception {
        if (month < 1 || month > 12 || day < 1 || day > 31 || h < 0 || h > 23 || m < 0 || m > 60 || s < 0 || s > 60) {
            throw new IllegalArgumentException("参数不正确！");
        }
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        String hStr = String.valueOf(h);
        String mStr = String.valueOf(m);
        String sStr = String.valueOf(s);

        return parseToDate(yearStr + "-" + monthStr + "-" + dayStr + " " + hStr + ":" + mStr + ":" + sStr);

    }
    /**
     * 将年月日时分秒转成Date类型
     * @param time
     * @param simpleDateFormat
     * @return
     */
    public static Date parseToDate(String time, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }
    /**
     * tempTime格式化日期(精确到秒)
     *
     * @param str
     * @return
     */
    public static Date onTempTime2Date(String str) {
        try {
            return tempTime.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    /**
     * day格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String onDat2Date(Date date) {
        return day.format(date);
    }

    /**
     * detailDay格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String onDetailDay2Date(Date date) {
        return detailDay.format(date);
    }
    /**
     * fileName格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String onFileName2Date(Date date) {
        return fileName.format(date);
    }
    /**
     * second格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String onSecond2Date(Date date) {
        return second.format(date);
    }
    /**
     * excelDate格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String onExcelDate2Date(Date date) {
        return excelDate.format(date);
    }
}
