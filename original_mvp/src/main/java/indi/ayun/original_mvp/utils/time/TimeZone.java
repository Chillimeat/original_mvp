package indi.ayun.original_mvp.utils.time;

import java.util.Date;

public class TimeZone {
    /**
     * 东一区（荷兰时区）
     */
    public final static String TIME_ZONE_EAST_1=  "GMT+1";
    /**
     * 东二区（）
     */
    public final static String TIME_ZONE_EAST_2=  "GMT+2";
    /**
     * 东三区（）
     */
    public final static String TIME_ZONE_EAST_3 = "GMT+3";
    /**
     * 东四区（）
     */
    public final static String TIME_ZONE_EAST_4 = "GMT+4";
    /**
     * 东五区（）
     */
    public final static String TIME_ZONE_EAST_5 = "GMT+5";
    /**
     * 东六区（）
     */
    public final static String TIME_ZONE_EAST_6 = "GMT+6";
    /**
     * 东七区（）
     */
    public final static String TIME_ZONE_EAST_7 = "GMT+7";
    /**
     * 东八区（北京时区）
     */
    public final static String TIME_ZONE_EAST_8 = "GMT+8";
    /**
     * 东九区（东京时区）
     */
    public final static String TIME_ZONE_EAST_9 = "GMT+9";
    /**
     * 东十区（）
     */
    public final static String TIME_ZONE_EAST_10 = "GMT+10";
    /**
     * 东十一区（）
     */
    public final static String TIME_ZONE_EAST_11 = "GMT+11";
    /**
     * 东西十二区（）
     */
    public final static String TIME_ZONE_EW_12 = "GMT+12";
    /**
     * 0区（）
     */
    public final static String TIME_ZONE_0 = "GMT+0";
    /**
     * 西一区（）
     */
    public final static String TIME_ZONE_WEST_1 = "GMT-1";
    /**
     * 西二区（）
     */
    public final static String TIME_ZONE_WEST_2 = "GMT-2";
    /**
     * 西三区（）
     */
    public final static String TIME_ZONE_WEST_3 = "GMT-3";
    /**
     * 西四区（）
     */
    public final static String TIME_ZONE_WEST_4 = "GMT-4";
    /**
     * 西五区（纽约时区）
     */
    public final static String TIME_ZONE_WEST_5 = "GMT-5";
    /**
     * 西六区（）
     */
    public final static String TIME_ZONE_WEST_6 = "GMT-6";
    /**
     * 西七区（）
     */
    public final static String TIME_ZONE_WEST_7 = "GMT-7";
    /**
     * 西八区（洛杉矶时区）
     */
    public final static String TIME_ZONE_WEST_8 = "GMT-8";
    /**
     * 西九区（墨西哥时区）
     */
    public final static String TIME_ZONE_WEST_9 = "GMT-9";
    /**
     * 西十区（）
     */
    public final static String TIME_ZONE_WEST_10 = "GMT-10";
    /**
     * 西十一区（）
     */
    public final static String TIME_ZONE_WEST_11 = "GMT-11";

    /**
     * 获取当前手机对应的系统时区
     *
     */
    public static java.util.TimeZone getPhoneTimeZone() {
        return java.util.TimeZone.getDefault();
    }
    /**
     * 以“GMT+8：00”形式返回当前系统对应的时区
     * @return
     */
    public static String getCurrentTimeZoneStr() {
        java.util.TimeZone tz = java.util.TimeZone.getDefault();
        return createGmtOffsetString(true,true,tz.getRawOffset());
    }

    private static String createGmtOffsetString(boolean includeGmt, boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }
    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }
    /**
     * 获取更改时区后的时间
     * @param date 时间
     * @param oldZone 旧时区
     * @param newZone 新时区
     * @return 时间
     */
    public static Date changeTimeZone(Date date, java.util.TimeZone oldZone, java.util.TimeZone newZone){
        Date dateTmp = null;
        if (date != null)
        {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }
    /**
     * 获得时区偏移量 相对GMT RFC 82
     *
     * @param date
     * @return
     */
    public static String onTimeoffset(Date date) {
        return String.format("%tz", date);
    }
}
