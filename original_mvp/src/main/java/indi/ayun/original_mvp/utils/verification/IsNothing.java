package indi.ayun.original_mvp.utils.verification;

import android.view.View;

import indi.ayun.original_mvp.base.BaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基本数据类型：字符型char； 布尔型boolean； 整数类型byte short int long； 浮点类型float double；
 * 引用数据类型（类类型）：类class；接口interface； 数组[]； 字符串String；array；list；map；set；
 * 基本数据的封箱：字符型Character； 布尔型Boolean； 整数类型Byte Short Int Long； 浮点类型Float Double；
 * 此类用于判断数据或者对象是否为空。其中布尔型boolean；类class；接口interface；三种无需判断;
 */
//TODO:多个参数不确定参数
public class IsNothing {
    private char aChar;
    private byte aByte;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;

    private static Character character;
    private static Byte bByte;
    private static Short bShort;
    private static Integer integer;
    private static Long bLong;
    private static Float bFloat;
    private static Double bDouble;

    private static ArrayList array;
    private static List list;
    private static Map map;
    private static Set set;
    private static String string;

    private static Object[] objects;

    public static boolean simple(Object value){
        if (null!=value) {
            return true;
        }else {
            return false;
        }
    }
    public static boolean view(View value){
        if (null!=value) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean onAnything(Character value){
        if (null!=value) {
            if (value!='\0')return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Character[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Character[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Byte value){
        if (null!=value) {
            if (value!=0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(byte[] value){
        if (null!=value) {
            if (value.length>0)
            return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(byte[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Short value){
        if (null!=value) {
            if (value!=0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Short[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Short[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Integer value){
        if (null!=value) {
            if (value!=0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Integer[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Integer[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Long value){
        if (null!=value) {
            if (value!=0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Long[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Long[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Float value){
        if (null!=value) {
            //有效保存7位有效数字
            if (value!=0.0&&value>=0.0000001)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Float[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Float[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Double value){
        if (null!=value) {
            //有效保存10位有效数字
            if (value!=0.0&&value>=0.0000000001)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Double[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Double[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(ArrayList<Object> value){
        if (null!=value) {

            if (value.isEmpty())return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(List<Object> value){
        if (null!=value) {
            if (!value.isEmpty())return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Map<Object,Object> value){
        if (null!=value) {
            if (!value.isEmpty())return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(Set<Object> value){
        if (null!=value) {
            if (!value.isEmpty())return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(String value){
        if (null!=value) {
            return ! "".equals(value)&&value.trim().length()!= 0;
        }else {
            return false;
        }
    }
    public static boolean onAnything(String[] value){
        if (null!=value) {
            if (value.length>0)return true;
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onAnything(String[][] value){
        if (null!=value) {
            if (value.length>0&&value[0].length>0){
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }
    public static boolean onDataArray(Object[] value){
        if (null!=value) {
            if (value.length>0&&!value.toString().equals("[]")&&!value.toString().equals("")){
                boolean a=false;
                for (int i=0;i<value.length;i++){
                    if (null!=value[i]&&!value[i].equals("")){//有些傻逼会给中间插入空值
                        a=true;
                        break;
                    }
                }
                return a;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean onDataList(List<? extends BaseBean> value){
        if (null!=value) {
            if (value.size()>0){
                for (int i=0;i<value.size();i++){
                    if (value.get(i)==null)return false;
                }
                return true;
            }
            else return false;
        }else {
            return false;
        }
    }

    /**
     * 验证是否为空串 (包括空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串,返回true)
     * @param str 验证字符
     * @return boolean
     */
    public static boolean onDataStr(String str) {
        if (str == null || "".equals(str) || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
}
