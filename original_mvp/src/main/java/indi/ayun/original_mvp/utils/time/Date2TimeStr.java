package indi.ayun.original_mvp.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static indi.ayun.original_mvp.utils.time.Time2Date.parseToDate;
import static indi.ayun.original_mvp.utils.time.TimeFormat.day;
import static indi.ayun.original_mvp.utils.time.TimeFormat.detailDay;
import static indi.ayun.original_mvp.utils.time.TimeFormat.excelDate;
import static indi.ayun.original_mvp.utils.time.TimeFormat.fileName;
import static indi.ayun.original_mvp.utils.time.TimeFormat.tempTime;
import static indi.ayun.original_mvp.utils.time.TimeFormat.second;

public class Date2TimeStr {
    /**
     * 将Date类型转成年月日时分秒
     * @param date
     * @param simpleDateFormat
     * @return
     */
    public static String formatDateTimetoString(Date date, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(date);
    }
    /**
     * 根据给出的Date值和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param date 日期对象
     * @param formatStr 日期格式
     * @return 如果为null，返回字符串""
     */
    public static String formatDateTimetoString(Date date, String formatStr) {
        String reStr = "";
        if (date == null || formatStr == null || formatStr.trim().length() < 1) {
            return reStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(formatStr);
        reStr = sdf.format(date);
        return reStr == null ? "" : reStr;
    }



    /**
     * 根据给出的Date值和格式串采用给定的国家所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param date 日期对象
     * @param formatStr 日期格式
     * @param locale 日期格式符号要被使用的语言环境
     * @return 如果为null，返回字符串""
     */
    public static String formatDateTimetoString(Date date, String formatStr, Locale locale) {
        String reStr = "";
        if (date == null || formatStr == null || locale == null || formatStr.trim().length() < 1) {
            return reStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, locale);
        reStr = sdf.format(date);
        return reStr == null ? "" : reStr;
    }

    /**
     * 根据给出的Date值字符串和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param dateStr 日期字符串
     * @param formatStr 日期格式
     * @return 如果为null，返回""
     * @throws Exception 可能抛出的异常
     */
    public static String formatDateTimetoString(String dateStr, String formatStr) throws Exception {
        String dStr = "";
        if (dateStr != null && dateStr.trim().length() > 0 && formatStr != null && formatStr.trim().length() > 0) {
            dStr = formatDateTimetoString(parseToDate(dateStr), formatStr);
        }
        return dStr;
    }

    /**
     * 根据给出的Date值字符串和格式串采用指定国家的风格来格式化时间，并返回相应的字符串
     *
     * @param dateStr 日期字符串
     * @param formatStr 日期格式
     * @param locale 日期格式符号要被使用的语言环境
     * @return 如果为null，返回""
     * @throws Exception 可能抛出的异常
     */
    public static String formatDateTimetoString(String dateStr, String formatStr, Locale locale) throws Exception {
        String dStr = "";
        if (dateStr != null && dateStr.trim().length() > 0 && formatStr != null && formatStr.trim().length() > 0
                && locale != null) {
            dStr = formatDateTimetoString(parseToDate(dateStr, locale), formatStr, locale);
        }
        return dStr;
    }

    /**
     * 格式化excel中的时间
     * @param date
     * @return
     */
    public static String onExcelDate2Str(Date date) {
        return excelDate.format(date);
    }

    /**
     * 将日期格式化作为文件名
     * @param date
     * @return
     */
    public static String onFileNameDate2Str(Date date) {
        return fileName.format(date);
    }

    /**
     * second格式化日期(精确到秒)
     *
     * @param date
     * @return
     */
    public static String onSecond2Str(Date date) {
        return second.format(date);
    }

    /**
     * tempTime格式化日期(精确到秒)
     *
     * @param date
     * @return
     */
    public static String onTempDate2Str(Date date) {
        return tempTime.format(date);
    }
    /**
     * day格式化日期(精确到秒)
     *
     * @param date
     * @return
     */
    public static String onDay2Str(Date date) {
        return day.format(date);
    }

    /**
     * detailDay格式化日期(精确到秒)
     *
     * @param date
     * @return
     */
    public static String onDetailDay2Str(Date date) {
        return detailDay.format(date);
    }

}
