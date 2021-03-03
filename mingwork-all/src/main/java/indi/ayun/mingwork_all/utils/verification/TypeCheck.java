package indi.ayun.mingwork_all.utils.verification;

import indi.ayun.mingwork_all.base.UtilBase;

public class TypeCheck extends UtilBase {
    /**
     * 参数是否是有效整数
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是整数
     */
    public static boolean isInt(Object obj) {
        if (!IsNothing.simple(obj))
            return false;
        if (obj instanceof Integer)
            return true;
        return obj.toString().matches("[-+]?\\d+");
    }
    /**
     * 判断一个对象是否可以为boolean类型,包括字符串中的true和false,o和1
     *
     * @param obj 要判断的对象
     * @return 是否是一个boolean类型
     */
    public static boolean isBoolean(Object obj) {
        boolean a=false;
        if (obj instanceof Boolean) a= true;
        if (obj instanceof String) {
            String strVal = String.valueOf(obj);
            if ("true".equalsIgnoreCase(strVal) || "false".equalsIgnoreCase(strVal) || "0".equalsIgnoreCase(strVal) || "1".equalsIgnoreCase(strVal))
                a= true;
        }
        if (obj instanceof Integer) {
            int intVal = ((Integer) obj).intValue();
            if (intVal==0||intVal==1)a= true;
        }
        return a;
    }

    /**
     * 是否可以是double，包含了字符串
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是double
     */
    public static boolean isDouOrFlo(Object obj) {
        if (!IsNothing.simple(obj))
            return false;
        if (obj instanceof Double || obj instanceof Float)
            return true;
        return VoucherPatternUtil.compileRegex("[-+]?\\d+\\.\\d+").matcher(obj.toString()).matches();
    }
    /**
     * 是否是有效数字 （整数或者小数）
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是数字
     */
    public static boolean isNumber(Object obj) {
        if (obj instanceof Number) return true;
        return TypeCheck.isInt(obj) || TypeCheck.isDouOrFlo(obj);
    }

}
