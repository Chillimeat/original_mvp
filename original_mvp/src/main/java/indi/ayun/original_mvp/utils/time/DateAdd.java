package indi.ayun.original_mvp.utils.time;

import java.util.Calendar;
import java.util.Date;

public class DateAdd {
    /**
     * 把给定的时间加上指定的时间值，可以为负
     *
     * @param d 需要设定的日期对象
     * @param times 时间值
     * @param type
     *            类型，Calendar.MILLISECOND，毫秒<BR>
     *            Calendar.SECOND，秒<BR>
     *            Calendar.MINUTE，分钟<BR>
     *            Calendar.HOUR，小时<BR>
     *            Calendar.DATE，日<BR>
     * @return 如果d为null，返回null
     */
    public static Date addTime(Date d, double times, int type) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        long qv = 1;
        switch (type) {
            case Calendar.MILLISECOND:
                qv = 1;
                break;
            case Calendar.SECOND:
                qv = 1000;
                break;
            case Calendar.MINUTE:
                qv = 1000 * 60;
                break;
            case Calendar.HOUR:
                qv = 1000 * 60 * 60;
                break;
            case Calendar.DATE:
                qv = 1000 * 60 * 60 * 24;
                break;
            default:
                throw new RuntimeException("时间类型不正确!type＝" + type);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        long milliseconds = (long) Math.round(Math.abs(times) * qv);
        if (times > 0) {
            for (; milliseconds > 0; milliseconds -= 2147483647) {
                if (milliseconds > 2147483647) {
                    calendar.add(Calendar.MILLISECOND, 2147483647);
                } else {
                    calendar.add(Calendar.MILLISECOND, (int) milliseconds);
                }
            }
        } else {
            for (; milliseconds > 0; milliseconds -= 2147483647) {
                if (milliseconds > 2147483647) {
                    calendar.add(Calendar.MILLISECOND, -2147483647);
                } else {
                    calendar.add(Calendar.MILLISECOND, -(int) milliseconds);
                }
            }
        }
        return calendar.getTime();
    }
    /**
     * 把给定的时间加上指定的年份，可以为负，返回新的被加上了年份的日期对象，不影响参数日期对象值
     *
     * @param d
     *            需要设定的日期对象
     * @param years
     *            年份
     * @return 新日期对象
     */
    public static Date addYears(Date d, int years) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的月份，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param months
     *            月份
     * @return 新日期对象
     */
    public static Date addMonths(Date d, int months) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的天数，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param days
     *            天数
     * @return 新日期对象
     */
    public static Date addDays(Date d, int days) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, days * 24);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的小时，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param hours
     *            小时
     * @return 新日期对象
     */
    public static Date addHours(Date d, int hours) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的分钟，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param minutes
     *            分钟
     * @return 新日期对象
     */
    public static Date addMinutes(Date d, int minutes) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的秒数，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param seconds
     *            秒
     * @return 新日期对象
     */
    public static Date addSeconds(Date d, int seconds) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的毫秒数，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param milliseconds
     *            毫秒
     * @return 新日期对象
     */
    public static Date addMilliseconds(Date d, int milliseconds) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MILLISECOND, milliseconds);
        return calendar.getTime();
    }

}
