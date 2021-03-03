package indi.ayun.mingwork_all.utils.time;

import java.util.Calendar;
import java.util.Date;

public class DateAmend {
    /**
     * 设置一个日期对象的年份是新的给定的年份
     *
     * @param d 需要设定的日期对象
     * @param year 新的年份
     * @return 新日期对象
     */
    public static Date setYearOfDate(Date d, int year) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的月份是新的给定的月份
     *
     * @param d
     *            需要设定的日期对象
     * @param month
     *            新的月份
     * @return 新日期对象
     */
    public static Date setMonthOfDate(Date d, int month) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的天是新的给定的天
     *
     * @param d
     *            需要设定的日期对象
     * @param day
     *            新的天
     * @return 新日期对象
     */
    public static Date setDayOfDate(Date d, int day) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的小时是新的给定的小时
     *
     * @param d
     *            需要设定的日期对象
     * @param hour
     *            新的小时数
     * @return 新日期对象
     */
    public static Date setHourOfDate(Date d, int hour) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的分钟是新的给定的分钟数
     *
     * @param d
     *            需要设定的日期对象
     * @param minute
     *            新的分钟数
     * @return 新日期对象
     */
    public static Date setMinuteOfDate(Date d, int minute) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的秒数是新的给定的秒数
     *
     * @param d
     *            需要设定的日期对象
     * @param second
     *            新的秒数
     * @return 新日期对象
     */
    public static Date setSecondOfDate(Date d, int second) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的毫秒数是新的给定的毫秒数
     *
     * @param d
     *            需要设定的日期对象
     * @param millisecond
     *            新的毫秒数
     * @return 新日期对象
     */
    public static Date setMillisecondOfDate(Date d, int millisecond) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

}
