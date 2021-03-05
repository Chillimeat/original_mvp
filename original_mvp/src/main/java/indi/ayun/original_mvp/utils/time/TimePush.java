package indi.ayun.original_mvp.utils.time;

import java.util.Calendar;
import java.util.Date;

import static indi.ayun.original_mvp.utils.time.Date2TimeStr.formatDateTimetoString;
import static indi.ayun.original_mvp.utils.time.Time2Date.parseToDate;
import static indi.ayun.original_mvp.utils.time.TimeCount.getDaysOfMonth;

public class TimePush {
    /**
     * 返回24小时前的时间
     *
     * @param date 指定日期
     * @return 新日期对象
     */
    public static Date getLastDay(Date date) {
        long day = date.getTime();
        long lastDay = day - 24 * 60 * 60 * 1000;
        return new Date(lastDay);
    }
    /**
     * 使日期倒一天
     *
     * @param cal
     */
    public void getLastDay(Calendar cal) {
        //计算上一月有多少天
        int month = cal.get(Calendar.MONTH);//0到11
        int year = cal.get(Calendar.YEAR);
        int days = getDaysOfMonth(year, month);//上个月的天数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {//如果是本月第一天，倒回上一月
            if (month == 0) {//如果是本年第一个月，年份倒一天
                cal.roll(Calendar.YEAR, false);
                cal.set(Calendar.MONTH, 11);//去年最后一个月是12月
                cal.set(Calendar.DAY_OF_MONTH, 31);//12月最后一天总是31号
            } else {//月份向前
                cal.roll(Calendar.MONTH, false);
                cal.set(Calendar.DAY_OF_MONTH, days);//上个月最后一天
            }
        } else {
            cal.roll(Calendar.DAY_OF_MONTH, false);//如果是月内，日期倒一天
        }
    }
    /**
     * 取得30天前的这个时间
     *
     * @return 新日期对象
     */
    public static Date getDayLastMonth() {
        long day = new Date().getTime();
        long dayLastMonth = day - 24 * 60 * 60 * 1000 * 20;
        return new Date(dayLastMonth);
    }
    /**
     * 返回24小时后的时间
     *
     * @param date 指定日期
     * @return 新日期对象
     */
    public static Date getTomorrow(Date date) {
        long day = date.getTime();
        long tomorrow = day + 24 * 60 * 60 * 1000;
        return new Date(tomorrow);
    }
    /**
     * 使指定日期向前走一天，变成“明天”的日期
     *
     * @param cal 等处理日期
     */
    public void getTomorrow(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//0到11
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int days = getDaysOfMonth(year, month + 1);
        if (day == days) {//如果是本月最后一天，还要判断年份是不是要向前滚
            if (month == 11) {//如果是12月份，年份要向前滚
                cal.roll(Calendar.YEAR, true);
                cal.set(Calendar.MONTH, 0);//月份，第一月是0
                cal.set(Calendar.DAY_OF_MONTH, 1);
            } else {//如果不是12月份
                cal.roll(Calendar.MONTH, true);
                cal.set(Calendar.DAY_OF_MONTH, 1);
            }

        } else {
            cal.roll(Calendar.DAY_OF_MONTH, 1);//如果是月内，直接天数加1
        }
    }
    /**
     * 取得30天后的这个时间
     *
     * @return 新日期对象
     */
    public static Date getDayNextMonth() {
        long day = new Date().getTime();
        long dayNextMonth = day + 20 * 24 * 60 * 60 * 1000;
        return new Date(dayNextMonth);
    }
    /**
     * 取得下个月的这天，比如2月1日可取得3月1日，此方法有很大局限性，不能用于月末的天数
     *
     * @param date 指定日期
     * @return 新日期对象
     */
    public static Date getDayNextMonth(Date date) {
        String yearStr = formatDateTimetoString(date, "yyyy");
        String monthStr = formatDateTimetoString(date, "MM");
        String dayStr = formatDateTimetoString(date, "dd");
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        if (month == 12) {
            month = 1;
            year = year + 1;
            yearStr = String.valueOf(year);
            monthStr = String.valueOf(month);
        }

        String dateStr = yearStr + "-" + monthStr + "-" + dayStr;
        try {
            date = parseToDate(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }
    /**
     * 得到这个月的第一天
     * @param d
     * @return
     */
    public static Date getDateByFirstDayOfMonth(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 获取date日期的0点0分0秒
     *
     * @param d 指定日期
     * @return 指定日期的0点0分0秒
     */
    public static Date getDate0h0m0s(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}
