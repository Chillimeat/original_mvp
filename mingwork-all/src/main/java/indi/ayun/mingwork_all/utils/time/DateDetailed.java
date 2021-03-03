package indi.ayun.mingwork_all.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDetailed {
    /**
     * 返回一个时间的年份整数
     * @param d 日期对象
     * @return 年份
     */
    public static int getYearOfDate(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取年
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getYearOfStr(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateTime);
            e.setTime(date);
            return e.get(1);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0;
        }
    }
    /**
     * 返回一个时间的月份整数
     * @param d 日期对象
     * @return 月份
     */
    public static int getMonthOfYear(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MONTH) + 1;
    }
    /**
     * 获取月
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getMonthOfStr(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateTime);
            e.setTime(date);
            return e.get(2);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0;
        }
    }
    /**
     * 获得下午或上午
     *
     * @param date
     * @return
     */
    public static String getAmOrPm(Date date) {
        return String.format("%tp", date);
    }
    /**
     * 返回一个时间的天份整数，是这个月的第几天，也就是几号
     *
     * @param d 日期对象
     * @return 天份
     */
    public static int getDayOfMonth(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 获取日
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getDayOfStr(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateTime);
            e.setTime(date);
            return e.get(5);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回一个时间的天份整数，是这个周的第几天，也就是周几
     *
     * @param d 日期对象
     * @return 天份
     */
    public static int getDayOfWeek(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }
    /**
     * 获取星期几
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getDayOfWeek(String dateTime, SimpleDateFormat simpleDateFormat) {
        return getDayOfWeek(Time2Date.parseToDate(dateTime, simpleDateFormat));
    }

    /**
     * 返回一个时间的周的整数，是这个月的第几周
     *
     * @param d 日期对象
     * @return 周
     */
    public static int getWeekOfMonth(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }
    /**
     * 日期中某个月份的第几周
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getWeekOfMonth(String dateTime, SimpleDateFormat simpleDateFormat) {
        return getWeekOfMonth(Time2Date.parseToDate(dateTime, simpleDateFormat));
    }
    /**
     * 返回一个时间的周的整数，是这个年份的第几周
     *
     * @param d 日期对象
     * @return 周
     */
    public static int getWeekOfYear(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    /**
     * 日期中某个年份的第几周
     * @param time
     * @param simpleDateFormat
     * @return
     */
    public static int getWeekOfYear(String time, SimpleDateFormat simpleDateFormat) {
        return getWeekOfYear(Time2Date.parseToDate(time, simpleDateFormat));
    }

    /**
     * 返回一个时间的天份整数，是这个年份的第几天
     *
     * @param d 日期对象
     * @return 天份
     */
    public static int getDayOfYear(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
    /**
     * 返回该时间所对应的在一天中的小时数的整数，如当前(Date now)是下午3点，返回为15
     *
     * @param d 日期对象
     * @return 小时
     */
    public static int getHoursOfDay24(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        return hours;
    }
    /**
     * 返回该时间所对应的在一天中的小时数的整数(采用12小时制)，如当前(Date now)是下午3点，返回为3
     *
     * @param d 日期对象
     * @return 小时
     */
    public static int getHoursOfDay12(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int hours = calendar.get(Calendar.HOUR);
        return hours;
    }
    /**
     * 返回该时间所对应的分钟数中的整数，如now是15点14分，则返回14
     *
     * @param d 日期对象
     * @return 分钟
     */
    public static int getMinutesOfHour(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = calendar.get(Calendar.MINUTE);

        return minutes;
    }
    /**
     * 返回该时间所对应的秒数中的整数，如now是15点14分34秒，则返回34
     *
     * @param d 日期对象
     * @return 秒
     */
    public static int getSecondsOfMinute(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int seconds = calendar.get(Calendar.SECOND);

        return seconds;
    }
    /**
     * 返回该时间所对应的毫秒数中的整数，如now是15点14分34秒470毫秒，则返回470
     *
     * @param d 日期对象
     * @return 毫秒
     */
    public static int getMillisecondsOfSecond(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        return millisecond;
    }
    /**
     * 获得当前微妙数 9位
     * @param date
     * @return
     */
    public static String getMicrosecond(Date date) {
        return String.format("%tN", date);
    }

}
