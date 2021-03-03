package indi.ayun.mingwork_all.utils.time;


import java.util.Date;
import static indi.ayun.mingwork_all.utils.time.Date2TimeStr.formatDateTimetoString;
import static indi.ayun.mingwork_all.utils.time.DateDifference.getHoursOfTwoDate;
import static indi.ayun.mingwork_all.utils.time.DateDifference.getMinutesOfTwoDate;
import static indi.ayun.mingwork_all.utils.time.DateDifference.getSecondsOfTwoDate;
import static indi.ayun.mingwork_all.utils.time.NowTime.getSystemDate;
import static indi.ayun.mingwork_all.utils.time.TimeCount.getZeroDate;

/**
 * Created by ayun on 2018/1/19.
 */

public class TimeBackset {
    private final static long MINUTE = 60 * 1000;// 1分钟
    private final static long HOUR = 60 * MINUTE;// 1小时
    private final static long DAY = 24 * HOUR;// 1天
    private final static long MONTH = 31 * DAY;// 月
    private final static long YEAR = 12 * MONTH;// 年


    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String getPastDateAll(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > YEAR) {
            r = (diff / YEAR);
            return r + "年前";
        }
        if (diff > MONTH) {
            r = (diff / MONTH);
            return r + "个月前";
        }
        if (diff > DAY) {
            r = (diff / DAY);
            return r + "天前";
        }
        if (diff > HOUR) {
            r = (diff / HOUR);
            return r + "个小时前";
        }
        if (diff > MINUTE) {
            r = (diff / MINUTE);
            return r + "分钟前";
        }
        return "刚刚";
    }



    /**
     * 不确定表示方式的时间差函数
     *
     * @param startDate
     *            指定时间
     * @param endDate
     *            结束时间
     * @return 时间差指定格式字符串
     */
    private static String getPastDateOtherDiff(Date startDate, Date endDate) {
        String[] type = new String[] { "年", "个月", "星期", "天", "小时", "分钟", "秒", "秒" };
        Object[] obj = DateDifference.timeDifference(startDate, endDate);
        String value = "1秒前";
        for (int i = 0; i < obj.length; i++) {
            if (!"0".equals(obj[i].toString())) {
                value = obj[i].toString() + type[i] + "前";
                break;
            }
        }
        return value;
    }



    /**
     * 流逝时间  今天
     * @return 时间
     * @throws Exception
     */
    public static String getPastDateToday(Date date) throws Exception {
        String timeStr;
        Date currDate = getSystemDate();
        if (date.before(getZeroDate(currDate))) {
            timeStr ="今天以前";
        } else {
            // 时
            double hours = getHoursOfTwoDate(currDate, date);
            if (hours < 24 && hours >= 1) {
                timeStr = String.valueOf((int) hours) + "小时前";
            } else {
                // 分
                double minutes = getMinutesOfTwoDate(currDate, date);
                if (minutes < 60 && minutes >= 1) {
                    timeStr = String.valueOf((int) minutes) + "分钟前";
                } else {
                    // 秒
                    double seconds =getSecondsOfTwoDate(currDate, date);
                    if (seconds < 60 && seconds >= 1) {
                        // timeStr = String.valueOf(seconds)+"秒前";
                        timeStr = "刚刚";
                    } else {
                        timeStr = "-";
                    }
                }
            }
        }
        return timeStr;
    }

}
