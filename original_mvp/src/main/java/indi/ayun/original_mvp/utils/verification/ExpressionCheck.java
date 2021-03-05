package indi.ayun.original_mvp.utils.verification;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import indi.ayun.original_mvp.base.UtilBase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("rawtypes")
public class ExpressionCheck extends UtilBase {
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
	
	//非零表达式
	private final static Pattern notZero_pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
	
	//数字表达式
	private final static Pattern number_pattern = Pattern.compile("^[0-9]*$");
	
	//大写字母表达式
	private final static Pattern upChar_pattern = Pattern.compile("^[A-Z]+$");
	
	//小写字母表达式
	private final static Pattern lowChar_pattern = Pattern.compile("^[a-z]+$");

	//大小写字母表达式
	private final static Pattern letter_pattern = Pattern.compile("^[A-Za-z]+$");
	
	//中文汉字表达式
	private final static Pattern chinese_pattern = Pattern.compile("^[\u4e00-\u9fa5],{0,}$");
	



	
	/**
	 * 验证非零正整数
	 * @param str 验证字符
	 * @return boolean 
	 */
	public static boolean isNotZero(String str) {
		return notZero_pattern.matcher(str).matches();
	}

	
	/**
	 * 验证是数字
	 * @param str 验证字符
	 * @return boolean   
	 */
	public static boolean isNumber(String str) {
		return number_pattern.matcher(str).matches();
	}
	
	
	/**
	 * 验证是大写字母
	 * @param str 验证字符
	 * @return boolean   
	 */
	public static boolean isUpChar(String str) {
		return upChar_pattern.matcher(str).matches();
	}
	
	
	/**
	 * 验证是小写字母
	 * @param str 验证字符
	 * @return boolean   
	 */
	public static boolean isLowChar(String str) {
		return lowChar_pattern.matcher(str).matches();
	}
	
	
	/**
	 * 验证是英文字母
	 * @param str 验证字符
	 * @return boolean   
	 */
	public static boolean isLetter(String str) {
		return letter_pattern.matcher(str).matches();
	}
	
	
	/**
	 * 验证输入汉字
	 * @param str 验证字符
	 * @return boolean
	 */
	public static boolean isChinese(String str) {
		return chinese_pattern.matcher(str).matches();
	}
    
    /**
     * 验证是否是正整数
     * @param str 验证字符
     * @return boolean
     */
	public static boolean isInteger(String str){
		try{
			Integer.valueOf(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 验证是否是小数
	 * @param paramString 验证字符
	 * @return boolean   
	 */
	public static boolean isPoint(String paramString){
		if(paramString.indexOf(".") > 0){
			if(paramString.substring(paramString.indexOf(".")).length() > 3){
				return false;
			}
		}
		return true;
	}


	/**
	 * 判断是否有特殊字符
	 * @param str 验证字符
	 * @return boolean   
	 */
	public static boolean isPeculiarStr(String str){
		boolean flag = false;
		String regEx = "[^0-9a-zA-Z\u4e00-\u9fa5]+";
		if(str.length() != (str.replaceAll(regEx, "").length())) {
			flag = true;
		}
			return  flag;
	}

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str
     *            指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str
     *            指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!IsNothing.onDataStr(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }


	/**
	 * 判断字符串是否为连续数字 45678901等
	 *
	 * @param str 待验证的字符串
	 * @return 是否为连续数字
	 */
	public static boolean isContinuousNum(String str) {
		if (TextUtils.isEmpty(str))
			return false;
		if (!isNumber(str))
			return true;
		int len = str.length();
		for (int i = 0; i < len - 1; i++) {
			char curChar = str.charAt(i);
			char verifyChar = (char) (curChar + 1);
			if (curChar == '9')
				verifyChar = '0';
			char nextChar = str.charAt(i + 1);
			if (nextChar != verifyChar) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否是纯字母
	 *
	 * @param str 待验证的字符串
	 * @return 是否是纯字母
	 */
	public static boolean isAlphaBetaString(String str) {
		if (TextUtils.isEmpty(str)) {
			return false;
		}

		Pattern p = Pattern.compile("^[a-zA-Z]+$");// 从开头到结尾必须全部为字母或者数字
		Matcher m = p.matcher(str);

		return m.find();
	}

	/**
	 * 判断字符串是否为连续字母 xyZaBcd等
	 *
	 * @param str 待验证的字符串
	 * @return 是否为连续字母
	 */
	public static boolean isContinuousWord(String str) {
		if (TextUtils.isEmpty(str))
			return false;
		if (!isAlphaBetaString(str))
			return true;
		int len = str.length();
		String local = str.toLowerCase();
		for (int i = 0; i < len - 1; i++) {
			char curChar = local.charAt(i);
			char verifyChar = (char) (curChar + 1);
			if (curChar == 'z')
				verifyChar = 'a';
			char nextChar = local.charAt(i + 1);
			if (nextChar != verifyChar) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否是日期
	 * 20120506 共八位，前四位-年，中间两位-月，最后两位-日
	 *
	 * @param date    待验证的字符串
	 * @param yearlen yearlength
	 * @return 是否是真实的日期
	 */
	public static boolean isRealDate(String date, int yearlen) {
		int len = 4 + yearlen;
		if (date == null || date.length() != len)
			return false;

		if (!date.matches("[0-9]+"))
			return false;

		int year = Integer.parseInt(date.substring(0, yearlen));
		int month = Integer.parseInt(date.substring(yearlen, yearlen + 2));
		int day = Integer.parseInt(date.substring(yearlen + 2, yearlen + 4));

		if (year <= 0)
			return false;
		if (month <= 0 || month > 12)
			return false;
		if (day <= 0 || day > 31)
			return false;

		switch (month) {
			case 4:
			case 6:
			case 9:
			case 11:
				return day > 30 ? false : true;
			case 2:
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
					return day > 29 ? false : true;
				return day > 28 ? false : true;
			default:
				return true;
		}
	}

}
	
	
	
	
	
