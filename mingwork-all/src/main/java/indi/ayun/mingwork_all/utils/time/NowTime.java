package indi.ayun.mingwork_all.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import static indi.ayun.mingwork_all.utils.time.Date2TimeStr.formatDateTimetoString;
import static indi.ayun.mingwork_all.utils.time.Time2Date.parseToDate;

public class NowTime {
    /**
     * 返回系统时间，以日期对象形式返回
     *
     * @return 系统时间
     */
    public static Date getSystemDate() {
        return new Date(System.currentTimeMillis());
    }
    /**
     * 时间戳，返回系统时间，以毫秒形式返回
     * @return 毫秒数
     */
    public static long getSystemTimeMs() {
        return System.currentTimeMillis();
    }
    /**
     * 时间戳，返回系统时间，以秒形式返回
     *
     * @return 秒数
     */
    public static long getSystemTimeS() {
        return System.currentTimeMillis()/1000;
    }
    /**
     * 当天的日期
     * @return
     */
    public static String todayYyyyMmDd(SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date());
    }

}
