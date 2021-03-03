package indi.ayun.mingwork_all.utils.calculation;


import java.math.BigDecimal;


/**
 * 主要功能： 资金运算工具类
 */
public class OtherBigDecimal {

    /**
     * 比较大小
     *
     * @param amount  输入的数值
     * @param compare 被比较的数字
     * @return true 大于被比较的数
     */
    public static Boolean compareBigDecimal(String amount, double compare) {

        BigDecimal lenth = new BigDecimal(amount);
        if (lenth.compareTo(BigDecimal.valueOf(compare)) == -1) {
            return false;
        }
        return true;
    }


}
