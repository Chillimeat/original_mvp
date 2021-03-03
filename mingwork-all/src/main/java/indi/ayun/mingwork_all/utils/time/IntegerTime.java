package indi.ayun.mingwork_all.utils.time;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IntegerTime {
    /**
     * 返回该时间相对于1970年1月1日开始计算的对应的毫秒数
     *
     * @param d 日期对象
     * @return 毫秒数
     */
    public static long getTimeLong(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        return d.getTime();
    }


    /**
     * 将年月日时分秒转成Long类型
     * @param dateTime
     * @return
     */
    public static Long getTimeLong(String dateTime,SimpleDateFormat simpleDateFormat) {
        try {
            Date e = simpleDateFormat.parse(dateTime);
            return Long.valueOf(e.getTime() / 1000L);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return Long.valueOf(0L);
        }
    }
    /**
     * 将格式化过的时间串转换成毫秒
     * @param day 将格式化过的时间
     * @param format 格式化字符串
     * @return 毫秒
     */
    public static long getTimeLong(String day, String format) {
        if (day == null || format == null)
            return 0;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date dt = formatter.parse(day);
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 将Long类型转成年月日时分秒
     * @param timeStamp
     * @return
     */
    public static String omTimeStampToTimeStr(Long timeStamp,SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date(timeStamp.longValue() * 1000L));
    }
    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL
     *            日期
     * @return
     */
    public static String omTimeStampToTimeStr(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }



    /**
     * 获取日期
     *
     * @param offset 表示偏移天数
     * @return
     */
    public String getNowDayOffset(int offset) {
        Calendar m_Calendar = Calendar.getInstance();
        long time = (long) m_Calendar.getTimeInMillis();
        time = time + offset * 24 * 3600 * 1000;
        Date myDate = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(myDate);
    }
    /**
     * 获取这天凌晨的秒数
     *
     * @return
     */
    public long getSecondsMorningTimeLong(Calendar c) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return tempCalendar.getTimeInMillis();
    }

    /**
     * 将double类型的数字保留两位小数（四舍五入）
     *
     * @param number
     * @return
     */
    public static String onDoubleTime2TimeStr(double number) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0.00");
        return df.format(number);
    }







}
