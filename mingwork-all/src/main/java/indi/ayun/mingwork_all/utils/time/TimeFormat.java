package indi.ayun.mingwork_all.utils.time;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeFormat {
    public static final SimpleDateFormat YYYYMMDD_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat HHMMSS_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat HHMM_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final SimpleDateFormat YYYYMMDDHHMMSS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());



    public static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat detailDay = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public static SimpleDateFormat second = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
    public static SimpleDateFormat tempTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat excelDate = new SimpleDateFormat("yyyy/MM/dd");

    /**
     *  G    Era    标志符            Text    公元
     y    年                    Year    1996; 96
     M    年中的月份                Month    July; Jul; 07
     w    年中的周数                Number    27
     W    月份中的周数            Number    2
     D    年中的天数                Number    189
     d    月份中的天数            Number    10
     F    月份中的星期            Number    2
     E    星期中的天数            Text    Tuesday; Tue
     a    am/pm 标记            Text    PM
     H    一天中的小时数（0-23）    Number    0
     k    一天中的小时数（1-24）    Number    24
     K    am/pm 中的小时数（0-11）    Number    0
     h    am/pm 中的小时数（1-12）    Number    12
     m    小时中的分钟数            Number    30
     s    分钟中的秒数            Number    55
     S    毫秒数                Number    978
     z    时区    General time zone    Pacific Standard Time; PST; GMT-08:00
     Z    时区    RFC 822 time zone    -0800
     */
    /**
     * 把时间格式化成如：2002-08-03 8:26:30.400 am 格式的字符串
     */
    public final static String FMT_yyyyMMddHHmmssSa_12 =            "yyyy-MM-dd KK:mm:ss.S a";

    /**
     * 把时间格式化成如：2002-08-03 8:26:16 am 格式的字符串
     */
    public final static String FMT_yyyyMMddHHmmssa_12 =             "yyyy-MM-dd KK:mm:ss a";

    /**
     * 把时间格式化成如：2002-08-03 8:26 am 格式的字符串
     */
    public final static String FMT_yyyyMMddHHmma_12 =               "yyyy-MM-dd KK:mm a";

    /**
     * 把时间格式化成如：2002-08-03 8 am 格式的字符串
     */
    public final static String FMT_yyyyMMddHHa_12 =                 "yyyy-MM-dd KK a";

    /**
     * 把时间格式化成如：2002-08-03 08:26:30.400 am  格式的字符串
     */
    public final static String FMT_yyyyMMddHHmmssSa =               "yyyy-MM-dd HH:mm:ss.S a";

    /**
     * 把时间格式化成如：2002-08-03 08:26:30.400 格式的字符串
     */
    public final static String FMT_yyyyMMddHHmmssS =                "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 把时间格式化成如：2002-08-03 08:26:16 格式的字符串
     */
    public final static String FMT_yyyyMMddHHmmss =                 "yyyy-MM-dd HH:mm:ss";

    /**
     * 把时间格式化成如：2002-08-03 08:26 格式的字符串
     */
    public final static String FMT_yyyyMMddHHmm =                   "yyyy-MM-dd HH:mm";

    /**
     * 把时间格式化成如：2002-08-03 08 格式的字符串
     */
    public final static String FMT_yyyyMMddHH =                     "yyyy-MM-dd HH";

    /**
     * 把时间格式化成如：2002-07-05 am 格式的字符串
     */
    public final static String FMT_yyyyMMdda =                      "yyyy-MM-dd a";

    /**
     * 把时间格式化成如：2002-07-05 格式的字符串
     */
    public final static String FMT_yyyyMMdd =                       "yyyy-MM-dd";

    /**
     * 把时间格式化成如：2002-07 格式的字符串
     */
    public final static String FMT_yyyyMM =                         "yyyy-MM";

    /**
     * 把时间格式化成如：20020803082630400格式的(17位)字符串
     */
    public final static String FMT_yyyyMMddHHmmssS_17 =             "yyyyMMddHHmmssS";

    /**
     * 把时间格式化成如：20020803082630格式的(14位)字符串
     */
    public final static String FMT_yyyyMMddHHmmss_14 =              "yyyyMMddHHmmss";

    /**
     * 把时间格式化成如：20020806 格式的(8位)字符串
     */
    public final static String FMT_yyyyMMdd_8=                      "yyyyMMdd";

    /**
     * 把时间格式化成如：200208 格式的(6位)字符串
     */
    public final static String FMT_yyyyMM_6=                        "yyyyMM";

    /**
     * 把时间格式化成如：12:08 PM(下午) 格式的字符串
     */
    public final static String FMT_HHmmA_12 =                       "KK:mm a";

    /**
     * 把时间格式化成如：0:55 AM上午，CST 格式的字符串
     */
    public final static String FMT_HHmmAz_12 =                      "KK:mm a,z";

    /**
     * 把时间格式化成如：0:56 AM上午，中国标准时间 格式的字符串
     */
    public final static String FMT_HHmmAzzzz_12 =                   "KK:mm a,zzzz";

    /**
     * 把时间格式化成如：12:08:23 am 格式的字符串
     */
    public final static String FMT_HHmmssA_12 =                     "KK:mm:ss a";

    /**
     * 把时间格式化成如：0:55:33 AM上午，CST 格式的字符串
     */
    public final static String FMT_HHmmssAz_12 =                    "KK:mm:ss a,z";

    /**
     * 把时间格式化成如：0:56:23 AM上午，中国标准时间 格式的字符串
     */
    public final static String FMT_HHmmssAzzzz_12 =                 "KK:mm:ss a,zzzz";

    /**
     * 把时间格式化成如：22:04:45 格式的字符串
     */
    public final static String FMT_HHmmss =                         "HH:mm:ss";

    /**
     * 把时间格式化成如：22:04:45.824 格式的字符串
     */
    public final static String FMT_HHmmssS =                        "HH:mm:ss.S";

    /**
     * 把时间格式化成如：22:04 格式的字符串
     */
    public final static String FMT_HHmm =                           "HH:mm";

    /**
     * 把时间格式化成如：22:04，CST 格式的字符串
     */
    public final static String FMT_HHmmz =                          "HH:mm,z";

    /**
     * 把时间格式化成如：22:04，中国标准时间 格式的字符串
     */
    public final static String FMT_HHmmzzzz =                       "HH:mm,zzzz";

    /**
     * 把时间格式化成如：Sun,Nov 14,'2004 格式的字符串
     */
    public final static String FMT_WWMMDDYY_EN =                    "EEE,MMM d,''yyyy";

    /**
     * 把时间格式化成如：星期日，2004年十一月14号 格式的字符串
     */
    public final static String FMT_WWMMDDYY_CN =                    "EEE,yyyy年MMMd号";

    /**
     * 把时间格式化成如：Sun,Nov 14,'2004 格式的字符串
     */
    public final static String FMT_MMDDYY_EN =                       "MMM d,''yyyy";

    /**
     * 把时间格式化成如：星期日，2004年十一月14号 格式的字符串
     */
    public final static String FMT_MMDDYY_CN =                      "yyyy年MMMd号";

    /**
     * 把时间格式化成如：星期几 格式的字符串，即可获得该日这个时间是星期几
     */
    public final static String FMT_WW =                              "EEE";

    /**
     * 常用的格式化时间的格式组，用于本类中格式化字符串成时间型
     */
    public static String[] FMTStr ={
            FMT_yyyyMMddHHmmssSa_12 ,FMT_yyyyMMddHHmmssa_12,FMT_yyyyMMddHHmma_12,FMT_yyyyMMddHHa_12,
            FMT_yyyyMMddHHmmssSa,FMT_yyyyMMddHHmmssS,FMT_yyyyMMddHHmmss,FMT_yyyyMMddHHmm,FMT_yyyyMMddHH ,FMT_yyyyMMdda,FMT_yyyyMMdd,
            FMT_yyyyMM,FMT_yyyyMMddHHmmssS_17,FMT_yyyyMMddHHmmss_14,FMT_yyyyMMdd_8,FMT_yyyyMM_6,
            FMT_HHmmA_12 ,FMT_HHmmAz_12,FMT_HHmmAzzzz_12,FMT_HHmmssA_12,FMT_HHmmssAz_12,FMT_HHmmssAzzzz_12,
            FMT_HHmmss,FMT_HHmmssS,FMT_HHmm,FMT_HHmmz,FMT_HHmmzzzz,FMT_WWMMDDYY_EN,FMT_WWMMDDYY_CN,FMT_MMDDYY_EN,FMT_MMDDYY_CN,FMT_WW};

    /**
     * 获得时区偏移量 相对GMT RFC 82
     */
    public static String FMTP_OFFSET="%tz";

    /**
     * 获得下午或上午
     */
    public static String FMTP_AMORPM="%tp";

    /**
     * 获得当前微妙数 9位
     */
    public static String FMTP_MICROSECOND="%tN";

    /**
     * 获得当前毫秒数 3位
     */
    public static String FMTP_MILL="%tL";

    /**
     * 获得当前秒 2位
     */
    public static String FMTP_SECOND="%tS";

    /**
     * 获得当前分钟 2位
     */
    public static String FMTP_MINUTE="%tM";

    /**
     * 获得当前小时 1-12
     */
    public static String FMTP_HOUR12="%tl";

    /**
     * 获得当前小时 00-23
     */
    public static String FMTP_HOUR24="%tH";

    /**
     * 获得当前时间 15:25
     */
    public static String FMTP_HHMM="%tR";

    /**
     * 获得当前时间 15:23:50
     */
    public static String FMTP_HHMMSS="%tT";

    /**
     * 获得当前时间 03:22:06 下午
     */
    public static String FMTP_HHMMSS_PMORAM="%tr";

    /**
     * 获取当前时间到日 03/25/08（月/日/年）
     */
    public static String FMTP_EXCEL_MMDDYY="%tD";

    /**
     * 获取当前时间到日 2008-03-25 年—月—日
     */
    public static String FMTP_DAY_YYMMDD="%tF";

    /**
     * 获得日期天 1-31
     */
    public static String FMTP_DAYNUM_ONE="%te";

    /**
     * 获得日期天 01-31
     */
    public static String FMTP_DAYNUM_TWO="%td";

    /**
     * 一年中的第几天 085
     */
    public static String FMTP_DAYNUM_FOR_YEAR="%tj";

    /**
     * 获得月份简称
     */
    public static String FMTP_MONTH_REFERRED="%tb";

    /**
     * 获得月份全称
     */
    public static String FMTP_MONTH_FULLNAME="%tB";

    /**
     * 获得月份 01-12
     */
    public static String FMTP_MONTHNUM="%tm";

    /**
     *获得星期简称
     */
    public static String FMTP_WEEK_REFERRED="%ta";

    /**
     * 获得星期全称
     */
    public static String FMTP_WEEK_FULLNAME="%tA";

    /**
     * 获得年简称 16
     */
    public static String FMTP_YEAR_REFERRED="%ty";

    /**
     * 获得年全称 2016
     */
    public static String FMTP_YEAR_FULLNAME="%tY";

    /**
     * 星期二 三月 25 13:37:22 CST 2016
     */
    public static String FMTP_TIMECST="%tc";

    /**
     * 获取时间戳到秒
     */
    public static String FMTP_TIMESTAMP_SECOND="%ts";

    /**
     * 获取时间戳到毫秒
     */
    public static String FMTP_TIMESTAMP_MILL="%tQ";

    public static String[] FMTPStr ={
            FMTP_OFFSET,FMTP_AMORPM,FMTP_MICROSECOND,FMTP_MILL,FMTP_SECOND,FMTP_MINUTE,
            FMTP_HOUR12,FMTP_HOUR24,FMTP_HHMM,FMTP_HHMMSS,FMTP_HHMMSS_PMORAM,FMTP_EXCEL_MMDDYY,FMTP_DAY_YYMMDD,FMTP_DAYNUM_ONE,
            FMTP_DAYNUM_TWO,FMTP_DAYNUM_FOR_YEAR,FMTP_MONTH_REFERRED,FMTP_MONTH_FULLNAME,FMTP_MONTHNUM,FMTP_WEEK_REFERRED,
            FMTP_WEEK_FULLNAME,FMTP_YEAR_REFERRED,FMTP_YEAR_FULLNAME,FMTP_TIMECST,FMTP_TIMESTAMP_SECOND,FMTP_TIMESTAMP_MILL};
}
