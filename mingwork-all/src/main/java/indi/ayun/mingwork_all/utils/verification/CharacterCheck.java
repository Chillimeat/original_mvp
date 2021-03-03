package indi.ayun.mingwork_all.utils.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterCheck {
    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */

    public static boolean checkStrChinese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!checkCharChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 检测char是否是中文
     * @param c
     * @return
     */
    public static boolean checkCharChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;
        }
        return false;

    }

    /**
     * 是否包含中文字符
     *
     * @param str 要判断的字符串
     * @return 是否包含中文字符
     */
    private static final char CN_CHAR_START = '\u4e00';
    private static final char CN_CHAR_END   = '\u9fa5';
    public static boolean containsChineseChar(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= CN_CHAR_START && chars[i] <= CN_CHAR_END) return true;
        }
        return false;
    }

    /**
     * String是否为true
     *
     * @param s
     * @return
     */
    public static boolean isTrue(String s) {
        return "true".equals(s)||"1".equals(s);
    }

    /**
     * 检测某个字符串是否被包含
     * @param patternStr 正则表达式
     * @param text 字符串
     * @return
     */
    public static String matcherStr(String patternStr, String text) {
        Pattern pattern = VoucherPatternUtil.compileRegex(patternStr);
        Matcher matcher = pattern.matcher(text);
        String group = null;
        if (matcher.find()) {
            group = matcher.group();
        }
        return group;
    }

    /**
     * 是否是乱码
     * @param strName
     * @return
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!CharacterCheck.checkCharChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }
}
