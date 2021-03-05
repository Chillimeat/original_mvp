package indi.ayun.original_mvp.utils.verification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class VoucherPatternUtil {
    /**
     * 身份证
     */
    public static final String REGEX_IDCARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /**
     * 手机号码
     */
    public static final Pattern REGEX_PHONE_NUMBER = Pattern.compile("^(1)\\d{10}");

    //座机号码表达式
    private final static Pattern REGEX_PLANE= Pattern.compile("^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$");

    /**
     * 银行帐号
     */
    public static final Pattern REGEX_BANK_ACCOUNT = Pattern.compile("^[0-9]{16,19}$");

    /**
     * 邮政编码
     */
    public static final Pattern REGEX_ZIPCODE = Pattern.compile("([0-9]{3})+.([0-9]{4})+");
    /**
     * 邮箱
     */
    public static final Pattern REGEX_EMAIL= Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");


    /**
     * 车牌
     */
    public static final Pattern REGEX_CAR = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");

    //条形码表达式
    private final static Pattern REGEX_ONECODE = Pattern.compile("^(([0-9])|([0-9])|([0-9]))\\d{10}$");
    //IP地址表达式
    private final static Pattern REGEX_IPADDRESS = Pattern.compile("[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))");

    //URL地址表达式
    private final static Pattern REGEX_URL = Pattern.compile("(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?");

    //真实姓名表达式
    private final static Pattern REGEX_REALNEM = Pattern.compile("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*");

    //ip-v6地址
    private final static Pattern REGEX_IPV6=Pattern.compile("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");

    //ip-v6地址
    private final static Pattern REGEX_IPV4=Pattern.compile("\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");

    /**
     * 编译后的正则表达式缓存
     */
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    /**
     * 编译一个正则表达式，并且进行缓存,如果缓存已存在则使用缓存
     *
     * @param regex 表达式
     * @return 编译后的Pattern
     */
    public static final Pattern compileRegex(String regex) {
        Pattern pattern = PATTERN_CACHE.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            PATTERN_CACHE.put(regex, pattern);
        }
        return pattern;
    }



    /**
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_IDCARD, idCard);
    }

    /**
     * 手机号码否正确
     * @param s
     * @return
     */
    public static boolean isMobileNumber(String s) {
        Matcher m = REGEX_PHONE_NUMBER.matcher(s);
        return m.matches();
    }
    /**
     * 验证座机号码是否正确
     * @param plane 座机号码
     * @return boolean
     */
    public static boolean isPlane(String plane) {
        return REGEX_PLANE.matcher(plane).matches();
    }
    /**
     * 银行账号否正确
     * @param s
     * @return
     */
    public static boolean isAccountNumber(String s) {
        Matcher m = REGEX_BANK_ACCOUNT.matcher(s);
        return m.matches();
    }


    /**
     * 邮政编码是否正确
     * @param s
     * @return
     */
    public static boolean isPostCode(String s) {
        Matcher m = REGEX_ZIPCODE.matcher(s);
        return m.matches();
    }
    /**
     * 邮箱是否正确
     * @param
     * @return
     */
    public static boolean isEmail(String email) {
        Matcher m = REGEX_EMAIL.matcher(email);
        return m.matches();
    }

    /**
     * 车牌是否正确
     * @param
     * @return
     */
    public static boolean isCar(String email) {
        Matcher m = REGEX_CAR.matcher(email);
        return m.matches();
    }

    /**
     * 验证是否是条形码
     * @param oneCode 条形码
     * @return boolean
     */
    public static boolean isOneCode(String oneCode) {
        return REGEX_ONECODE.matcher(oneCode).matches();
    }
    /**
     * 验证真实姓名
     * @param str  验证字符
     * @return
     */
    public static boolean isRealName(String str){
        return REGEX_REALNEM.matcher(str).matches();
    }
    /**
     * 验证IP地址是否正确
     * @param ipaddress IP地址
     * @return boolean
     */
    public static boolean isIpAddress(String ipaddress){
        return REGEX_IPADDRESS.matcher(ipaddress).matches();
    }
    /**
     * 验证IP地址是否正确
     * @param ipaddress IP地址
     * @return boolean
     */
    public static boolean isIpV6(String ipaddress){
        return REGEX_IPV6.matcher(ipaddress).matches();
    }
    /**
     * 验证IP地址是否正确
     * @param ipaddress IP地址
     * @return boolean
     */
    public static boolean isIpV4(String ipaddress){
        return REGEX_IPV4.matcher(ipaddress).matches();
    }


    /**
     * 验证URL地址是否正确
     * @param url 地址
     * @return boolean
     */
    public static boolean isURL(String url){
        return REGEX_URL.matcher(url).matches();
    }

}
