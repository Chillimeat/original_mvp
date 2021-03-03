package indi.ayun.mingwork_all.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Attribute {
    private static final String[] CHINESE_ZODIAC = new String[]{"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC = new String[]{"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};


    /**
     * 获取日期中的生肖
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static String getChineseZodiac(String dateTime, SimpleDateFormat simpleDateFormat) {
        int yyyy = DateDetailed.getYearOfStr(dateTime, simpleDateFormat);
        return getChineseZodiac(yyyy);
    }

    /**
     * 获取日期中的生肖
     * @param date
     * @return
     */
    public static String getChineseZodiac(Date date) {
        int yyyy = DateDetailed.getYearOfDate(date);
        return getChineseZodiac(yyyy);
    }

    /**
     * 获取日期中的生肖
     * @param year
     * @return
     */
    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }


    /**
     * 获取日期中的星座
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static String getZodiac(String dateTime, SimpleDateFormat simpleDateFormat) {
        int dd = DateDetailed.getDayOfStr(dateTime, simpleDateFormat);
        int month = DateDetailed.getMonthOfStr(dateTime, simpleDateFormat);
        return getZodiac(month, dd);
    }

    /**
     * 获取日期中的星座
     * @param date
     * @return
     */
    public static String getZodiac(Date date) {
        int dd = DateDetailed.getDayOfMonth(date);
        int month = DateDetailed.getMonthOfYear(date);
        return getZodiac(month, dd);
    }

    /**
     * 获取日期中的星座
     * @param month
     * @param day
     * @return
     */
    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]?month - 1:(month + 10) % 12];
    }
}
