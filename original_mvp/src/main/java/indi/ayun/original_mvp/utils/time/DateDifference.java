package indi.ayun.original_mvp.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static indi.ayun.original_mvp.utils.time.Date2TimeStr.formatDateTimetoString;

public class DateDifference {
    /**
     * 比较两个时间的先后顺序。 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
     * @param d1 日期对象
     * @param d2 日期对象
     * @return 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
     */
    public static int compareTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }

        long dI1 = d1.getTime();
        long dI2 = d2.getTime();

        if (dI1 > dI2) {
            return -1;
        } else if (dI1 < dI2) {
            return 1;
        } else {
            return 0;
        }

    }
    /**
     * 比较日期,当返回true的时候，说明第一个日期参数是大于第二个日期参数的，即第一个比第二个晚.等于是false
     * @param standDate
     * @param desDate
     * @return
     */
    public static boolean compareTwoDate(String standDate, SimpleDateFormat s1, String desDate,SimpleDateFormat s2) {
        try {
            return s1.parse(desDate).before(s2.parse(standDate));
        } catch (ParseException var3) {
            var3.printStackTrace();
            return false;
        }
    }
    /**
     * 判断某两天是不是同一天
     *
     * @param c1
     * @param c2
     * @return
     */
    public boolean compareTwoDate(Calendar c1, Calendar c2) {

        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
            return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH))
            return false;
        return true;
    }

    /**
     * 计算两个时间见得月份差，可为负数
     *
     * @param sDate
     *            开始时间
     * @param eDate
     *            结束时间
     * @return 月份差
     */
    public static int getMonthCount(Date sDate, Date eDate) {
        String sDateStr = formatDateTimetoString(sDate, "MM");
        String eDateStr = formatDateTimetoString(eDate, "MM");
        int monthCount = Integer.parseInt(eDateStr) - Integer.parseInt(sDateStr) + 1;
        return monthCount;
    }

    /**
     * 计算两个时间见得年份差，可为负数
     *
     * @param sDate 开始时间
     * @param eDate 结束时间
     * @return 年份差
     */
    public static int getYearCount(Date sDate, Date eDate) {
        String sDateStr = formatDateTimetoString(sDate, "yyyy");
        String eDateStr = formatDateTimetoString(eDate, "yyyy");
        return Integer.parseInt(eDateStr) - Integer.parseInt(sDateStr);
    }


    /**
     * 返回两个日期之间的毫秒数的差距
     *
     * @param d1 日期对象
     * @param d2 日期对象
     * @return 二者至1970年1.1后的毫秒数的差值
     */
    public static long getMillisecondsOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long dI1 = d1.getTime();
        long dI2 = d2.getTime();
        return (dI1 - dI2);
    }

    /**
     * 获得两个日期之间相差的秒数
     *
     * @param d1 日期对象
     * @param d2 日期对象
     * @return 两日期之间相差的秒数
     */
    public static double getSecondsOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long i = getMillisecondsOfTwoDate(d1, d2);

        return (double) i / 1000;
    }

    /**
     * 获得两个日期之间相差的分钟数
     *
     * @param d1 日期对象
     * @param d2 日期对象
     * @return 两日期之间相差的分钟数
     */
    public static double getMinutesOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long millions = getMillisecondsOfTwoDate(d1, d2);
        return (double) millions / 60 / 1000;
    }

    /**
     * 获得两个日期之间相差的小时数
     *
     * @param d1 日期对象
     * @param d2 日期对象
     * @return 两日期之间相差的小时数
     */
    public static double getHoursOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long millions = getMillisecondsOfTwoDate(d1, d2);
        return (double) millions / 60 / 60 / 1000;
    }

    /**
     * 获得两个日期之间相差的天数
     *
     * @param d1 日期对象
     * @param d2 日期对象
     * @return 两日期之间相差的天数
     */
    public static double getDaysOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long millions = getMillisecondsOfTwoDate(d1, d2);
        return (double) millions / 24 / 60 / 60 / 1000;
    }
    /**
     * 得到两个日期的天数
     * @param sdate1 日期一
     * @param sdate2 日期二
     * @return 天数
     */
    public static int getDateInterval(String sdate1, SimpleDateFormat s1,String sdate2,SimpleDateFormat s2) {
        Date date1 = null;
        Date date2 = null;
        long betweenDays=0;

        try {
            date1 = s1.parse(sdate1);
            date2 = s2.parse(sdate2);

            long beginTime = date1.getTime();
            long endTime = date2.getTime();
            betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (int) betweenDays;
    }
    /**
     * 时间差
     *
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 返回时间差数组：(年，月，周，天，时，分，秒，毫秒)
     */
    public static Object[] timeDifference(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return new Object[] { 0, 0, 0, 0, 0, 0, 0 };
        } else {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(startTime);
            end.setTime(endTime);

            long startMs = start.getTimeInMillis();
            long endMs = end.getTimeInMillis();
            long l_differ = endMs - startMs;// 毫秒数
            long ll_differ = l_differ / 1000;// 秒

            long second_differ = l_differ / 1000;// 秒

            long year_differ = second_differ / (60 * 60 * 24 * 365);// 得到年数
            long month_differ = second_differ / (60 * 60 * 24 * 30);// 得到月数
            long week_differ = second_differ / (60 * 60 * 24 * 7);// 得到周数

            long day_differ = second_differ / (60 * 60 * 24);// 得到天数
            second_differ = second_differ - day_differ * 60 * 60 * 24;// 天
            long hour_differ = second_differ / (60 * 60);// 时
            second_differ = second_differ - hour_differ * 60 * 60;
            long minute_differ = second_differ / 60;// 分
            second_differ = second_differ - minute_differ * 60;

            return new Object[] { year_differ, month_differ, week_differ, day_differ, hour_differ, minute_differ,
                    second_differ, ll_differ };
        }
    }
}
