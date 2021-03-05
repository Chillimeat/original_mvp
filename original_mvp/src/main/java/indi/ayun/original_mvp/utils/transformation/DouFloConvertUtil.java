package indi.ayun.original_mvp.utils.transformation;

import indi.ayun.original_mvp.utils.verification.TypeCheck;

public class DouFloConvertUtil {


    /**
     * 将对象转为Double,如果对象无法转换,将使用默认值
     *
     * @param object       要转换的对象
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    public static double toDouble(Object object, double defaultValue) {
        if (object instanceof Number)
            return ((Number) object).doubleValue();
        if (TypeCheck.isNumber(object)) {
            return Double.parseDouble(object.toString());
        }
        if (null == object) return defaultValue;
        return 0;
    }
    /**
     * String转Double
     * @param str
     * @return
     */
    public static double stringToDouble(String str) {
        double i = 0;
        if (str != null) {
            try {
                i = Double.parseDouble(str.trim());
            } catch (Exception e) {
                i = 0;
            }
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * Long转Double
     * @param d
     * @return
     */
    public static double longToDouble(long d) {
        double lo = 0;
        try {
            lo = Double.parseDouble(String.valueOf(d));
        } catch (Exception e) {
            lo = 0;
        }
        return lo;
    }
}
