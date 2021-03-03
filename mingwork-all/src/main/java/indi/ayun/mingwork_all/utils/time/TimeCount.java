package indi.ayun.mingwork_all.utils.time;

import java.util.Calendar;
import java.util.Date;

import static indi.ayun.mingwork_all.utils.time.DateAmend.setDayOfDate;
import static indi.ayun.mingwork_all.utils.time.DateAmend.setHourOfDate;
import static indi.ayun.mingwork_all.utils.time.DateAmend.setMinuteOfDate;
import static indi.ayun.mingwork_all.utils.time.DateAmend.setSecondOfDate;
import static indi.ayun.mingwork_all.utils.time.DateDetailed.getMonthOfYear;
import static indi.ayun.mingwork_all.utils.time.DateDetailed.getYearOfDate;
import static indi.ayun.mingwork_all.utils.time.NowTime.getSystemDate;

public class TimeCount {
    /**
     * 返回指定日期的月份的天数量
     *
     * @param d
     *            日期对象
     * @return 天数
     */
    public static int getDaysOfMonth(Date d) {
        int year = getYearOfDate(d);
        int month = getMonthOfYear(d);
        return getDaysOfMonth(year, month);
    }

    /**
     * 返回指定日期的月份的天数量
     *
     * @param year 年
     * @param month 月
     * @return 天数
     */
    public static int getDaysOfMonth(int year, int month) {
        int days = 0;
        if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                days = 29;
            } else {
                days = 28;
            }
        }
        if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            days = 30;
        }
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
                || (month == 12)) {
            days = 31;
        }
        return days;
    }
    /**
     * 获取当月开始时0点0分0秒
     *
     * @return 日期对象
     */
    public static Date getCurrentMouthStart() {
        Date d = getSystemDate();
        d = setDayOfDate(d, 1);
        d = setHourOfDate(d, 0);
        d = setMinuteOfDate(d, 0);
        d = setSecondOfDate(d, 0);
        return d;
    }
    /**
     * 获取指定日期的 0点0分0秒
     *
     * @param d
     *            指定日期
     * @return 指定日期的0点0分0秒
     */
    private static long get24HourMill(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定日期的 0点0分0秒
     *
     * @param d
     *            指定日期
     * @return 指定日期的0点0分0秒
     */
    public static Date getZeroDate(Date d) {
        if (d == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的 23点59分59秒
     *
     * @param date
     *            指定日期
     * @return 指定日期的0点0分0秒
     */
    public static Date getLastDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
