package indi.ayun.original_mvp.utils.calculation;


import java.math.BigDecimal;


/**
 * 主要功能： 资金运算工具类
 * 位数处理
 */
public class DigitBigDecimal {

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double rounding(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String rounding(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }
    /**
     * 金额分割，四舍五人金额
     *
     * @param s
     * @return
     */
    public static String formatMoney(BigDecimal s) {
        String retVal = "";
        String str = "";
        boolean is_positive_integer = false;
        if (null == s) {
            return "0.00";
        }

        if (0 == s.doubleValue()) {
            return "0.00";
        }
        //判断是否正整数
        if (s.toString().indexOf("-") != -1) {
            is_positive_integer = true;
        } else {
            is_positive_integer = false;
        }
        //是负整数
        if (is_positive_integer) {
            //去掉 - 号
            s = new BigDecimal(s.toString().substring(1, s.toString().length()));
        }
        str = s.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        StringBuffer sb = new StringBuffer();
        String[] strs = str.split("\\.");
        int j = 1;
        for (int i = 0; i < strs[0].length(); i++) {
            char a = strs[0].charAt(strs[0].length() - i - 1);
            sb.append(a);
            if (j % 3 == 0 && i != strs[0].length() - 1) {
                sb.append(",");
            }
            j++;
        }
        String str1 = sb.toString();
        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < str1.length(); i++) {
            char a = str1.charAt(str1.length() - 1 - i);
            sb1.append(a);
        }
        sb1.append(".");
        sb1.append(strs[1]);
        retVal = sb1.toString();

        if (is_positive_integer) {
            retVal = "-" + retVal;
        }
        return retVal;
    }

    /**
     * 四舍五入金额
     *
     * @param s
     * @return
     */
    public static String formatMoney1(BigDecimal s) {
        String retVal = "";
        String str = "";
        boolean is_positive_integer = false;
        if (null == s) {
            return "0.00";
        }

        if (0 == s.doubleValue()) {
            return "0.00";
        }
        //判断是否正整数
        if (s.toString().indexOf("-") != -1) {
            is_positive_integer = true;
        } else {
            is_positive_integer = false;
        }
        //是负整数
        if (is_positive_integer) {
            //去掉 - 号
            s = new BigDecimal(s.toString().substring(1, s.toString().length()));
        }
        str = s.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        StringBuffer sb = new StringBuffer();
        String[] strs = str.split("\\.");
        int j = 1;
        for (int i = 0; i < strs[0].length(); i++) {
            char a = strs[0].charAt(strs[0].length() - i - 1);
            sb.append(a);
            if (j % 3 == 0 && i != strs[0].length() - 1) {
                sb.append("");
            }
            j++;
        }
        String str1 = sb.toString();
        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < str1.length(); i++) {
            char a = str1.charAt(str1.length() - 1 - i);
            sb1.append(a);
        }
        sb1.append(".");
        sb1.append(strs[1]);
        retVal = sb1.toString();

        if (is_positive_integer) {
            retVal = "-" + retVal;
        }
        return retVal;
    }

    /**
     * 获取自己想要的数据格式
     *
     * @param s                需处理的数据
     * @param numOfIntPart     整数位数
     * @param numOfDecimalPart 小数位数
     * @return 处理过的数据
     */
    public static String adjustDouble(String s, int numOfIntPart, int numOfDecimalPart) {

        //按小数点的位置分割成整数部分和小数部分
        String[] array = s.split("\\.");
        char[] tempA = new char[numOfIntPart];
        char[] tempB = new char[numOfDecimalPart];
        //整数部分满足精度要求(情况1)
        if (array[0].length() == numOfIntPart) {
            //直接获取整数部分长度字符
            for (int i = 0; i < array[0].length(); i++) {
                tempA[i] = array[0].charAt(i);
            }
            //小数部分精度大于或等于指定的精度
            if (numOfDecimalPart <= array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    tempB[i] = array[1].charAt(i);

                }
            }
            //小数部分精度小于指定的精度
            if (numOfDecimalPart > array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    if (i < array[1].length()) {
                        tempB[i] = array[1].charAt(i);
                    } else {
                        tempB[i] = '0';
                    }


                }
            }
            if (numOfDecimalPart == 0) {
                return String.valueOf(tempA) + String.valueOf(tempB);
            }
            return String.valueOf(tempA) + "." + String.valueOf(tempB);
        }

        //整数部分位数大于精度要求(情况2)
        if (array[0].length() > numOfIntPart) {
            //先倒序获取指定位数的整数
            for (int i = array[0].length() - 1, j = 0; (i >= array[0].length() - numOfIntPart) && (j < numOfIntPart); i--, j++) {
                tempA[j] = array[0].charAt(i);
                System.out.println(tempA[j]);
            }
            char[] tempA1 = new char[numOfIntPart];
            //调整顺序
            for (int j = 0, k = tempA.length - 1; j < numOfIntPart && (k >= 0); j++, k--) {
                tempA1[j] = tempA[k];
                System.out.println("tempA1[j]" + tempA1[j]);

            }
            //小数部分精度大于或等于指定的精度
            if (numOfDecimalPart <= array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    tempB[i] = array[1].charAt(i);

                }

            }
            //小数部分精度小于指定的精度
            if (numOfDecimalPart > array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    if (i < array[1].length()) {
                        tempB[i] = array[1].charAt(i);
                    } else {
                        tempB[i] = '0';
                    }


                }
            }

            return String.valueOf(tempA1) + "." + String.valueOf(tempB);
        }


        //整数部分满足精度要求(情况3)
        if (array[0].length() == numOfIntPart) {
            //直接获取整数部分长度字符
            for (int i = 0; i < array[0].length(); i++) {
                tempA[i] = array[0].charAt(i);
            }
            //小数部分精度小于指定的精度
            if (numOfDecimalPart > array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    if (i < array[1].length()) {
                        tempB[i] = array[1].charAt(i);
                    } else {
                        tempB[i] = '0';
                    }


                }
            }

            //小数部分精度大于或等于指定的精度
            if (numOfDecimalPart <= array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    tempB[i] = array[1].charAt(i);

                }

            }
            if (numOfDecimalPart == 0) {
                return String.valueOf(tempA) + String.valueOf(tempB);
            }
            return String.valueOf(tempA) + "." + String.valueOf(tempB);
        }

        //整数部分大于精度要求(情况4)
        if (array[0].length() > numOfIntPart) {
            //先倒序获取指定位数的整数
            for (int i = array[0].length() - 1, j = 0; (i >= array[0].length() - numOfIntPart + 1) && (j < numOfIntPart); i--, j++) {
                //System.out.println("<<<<"+(i-array[0].length()+1));
                tempA[j] = array[0].charAt(i);
            }
            char[] tempA1 = new char[numOfIntPart];
            //调整顺序
            for (int j = 0, k = tempA.length - 1; j < numOfIntPart && (k >= 0); j++) {
                tempA1[j] = tempA[k];
                k--;
            }

            //小数部分精度小于指定的精度
            if (numOfDecimalPart > array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    if (i >= array[1].length()) {
                        tempB[i] = '0';

                    } else {
                        tempB[i] = array[1].charAt(i);
                    }


                }
            }
            //小数部分精度大于或等于指定的精度
            if (numOfDecimalPart <= array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    tempB[i] = array[1].charAt(i);

                }

            }

            if (numOfDecimalPart == 0) {
                return String.valueOf(tempA1) + String.valueOf(tempB);
            }
            return String.valueOf(tempA1) + "." + String.valueOf(tempB);
        }

        //整数部分小于精度要求(情况5)
        if (array[0].length() < numOfIntPart) {
            //先倒序获取指定位数的整数
            char[] tempA1 = new char[numOfIntPart];
            for (int i = array[0].length() - 1, j = 0; (i >= numOfIntPart - array[0].length() - (numOfIntPart - array[0].length())) && (j < numOfIntPart); i--, j++) {
                tempA1[j] = array[0].charAt(i);
                System.out.println("<<<<<<tempA1[j]" + tempA1[j]);
            }

            //补0
            for (int i = array[0].length(); i < array[0].length() + numOfIntPart - array[0].length(); i++) {
                tempA1[i] = '0';

                System.out.println("<<<<<<" + tempA1[i]);
            }

            char[] tempA2 = new char[numOfIntPart];
            //调整顺序
            for (int j = 0, k = tempA1.length - 1; j < numOfIntPart && (k >= 0); j++) {
                tempA2[j] = tempA1[k];
                k--;
            }
            //小数部分精度小于指定的精度
            if (numOfDecimalPart > array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    if (i < array[1].length()) {
                        tempB[i] = array[1].charAt(i);
                    } else {
                        tempB[i] = '0';
                    }
                }
            }
            //小数部分精度大于或等于指定的精度
            if (numOfDecimalPart <= array[1].length()) {
                for (int i = 0; i < numOfDecimalPart; i++) {
                    tempB[i] = array[1].charAt(i);

                }
            }
            if (numOfDecimalPart == 0) {
                return String.valueOf(tempA2) + String.valueOf(tempB);
            }
            return String.valueOf(tempA2) + "." + String.valueOf(tempB);
        }

        //情况(6)
        if ((array[0].length() < numOfIntPart) && (array[1].length() < numOfDecimalPart)) {
            for (int i = 0; i < numOfIntPart - array[0].length(); i++) {
                s = "0" + s;
            }

            for (int i = 0; i < numOfDecimalPart - array[1].length(); i++) {
                s = s + "0";
            }
            return s;
        }

        return null;
    }


    public static void main(String[] args) {
        System.out.println("金额0：         " + formatMoney1(new BigDecimal(0)));
        System.out.println("金额0.0 ：         " + formatMoney1(new BigDecimal(0.0)));
        System.out.println("金额0.00：         " + formatMoney1(new BigDecimal(0.00)));
        System.out.println("金额0.58：         " + formatMoney1(new BigDecimal(0.58)));
        System.out.println("金额5.58：         " + formatMoney1(new BigDecimal(5.58)));
        System.out.println("金额5.54：          " + formatMoney1(new BigDecimal(5.54)));
        System.out.println("金额512322.555555111：          " + formatMoney1(new BigDecimal(512322.555555111)));
        System.out.println("金额3423423425.54：     " + formatMoney1(new BigDecimal(3423423425.54)));
        System.out.println("金额3423423425.58：      " + formatMoney1(new BigDecimal(3423423425.58)));
        System.out.println("金额1000000.543453：     " + formatMoney1(new BigDecimal(1000000.543453)));
        System.out.println("金额9343788754.573453：     " + formatMoney1(new BigDecimal(-9343788754.573453)));
        System.out.println("金额9343788756.577：     " + formatMoney1(new BigDecimal(-9343788756.577)));
        System.out.println("金额-343788756.577：     " + formatMoney1(new BigDecimal(-343788756.577)));
        System.out.println("金额-34756.54：     " + formatMoney1(new BigDecimal(-34756.54)));
        System.out.println("金额-34756.556：     " + formatMoney1(new BigDecimal(-34756.556)));

        //DateUtils.rollDay(new Date(), -15);
        //直接使用浮点数进行计算，得到的结果是有问题的
        //System.out.println(0.01 + 0.05);

        //使用了BigDecimal类进行计算后，可以做到精确计算
        //System.out.println(BigDecimalMoney.add(0.0000000000005, 0.00000001));
    }

}
