package indi.ayun.mingwork_all.utils.character;



/**
* 主要功能： 字符串操作工具类
*/
public class StringUtils {

    /**
     * 将字符串的第一位转为小写
     *
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * 将字符串的第一位转为大写
     *
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * 下划线命名转为驼峰命名
     *
     * @param str 下划线命名格式
     * @return 驼峰命名格式
     */
    public static final String underScoreCase2CamelCase(String str) {
        if (!str.contains("_")) return str;
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        boolean hitUnderScore = false;
        sb.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                hitUnderScore = true;
            } else {
                if (hitUnderScore) {
                    sb.append(Character.toUpperCase(c));
                    hitUnderScore = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名法转为下划线命名
     *
     * @param str 驼峰命名格式
     * @return 下划线命名格式
     */
    public static final String camelCase2UnderScoreCase(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 字符串连接，将参数列表拼接为一个字符串
     * @param split
     * @param more
     * @return 回拼接后的字符串
     */
    public static String concatSpiltWith(String split, Object... more) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < more.length; i++) {
            if (i != 0) buf.append(split);
            buf.append(more[i]);
        }
        return buf.toString();
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param str 字符串
     * @return 字符串ASCII码
     */
    public static String toASCII(String str) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = str.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * 将字符串转移为Unicode码
     * @param str 字符串
     * @return
     */
    public static String toUnicode(String str) {
        StringBuffer strBuf = new StringBuffer();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            strBuf.append("\\u").append(Integer.toHexString(chars[i]));
        }
        return strBuf.toString();
    }

    /**
     * 将字符串转移为Unicode码
     * @param chars 字符数组
     * @return
     */
    public static String toUnicodeString(char[] chars) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            strBuf.append("\\u").append(Integer.toHexString(chars[i]));
        }
        return strBuf.toString();
    }

}
