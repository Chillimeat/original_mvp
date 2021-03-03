package indi.ayun.mingwork_all.utils.encryption;

/**
 * @Description:主要功能: 异或加密
 * 1.介绍：
 *      异或加密是一种很简单的加密算法，无论是原理还是操作性上，都不具备任何难度，所以，在做一些简单的加密时，被广为采用。
 *      但因为很简答，破解起来也很容易，所以对于更加私密的信息，不要用这种方法加密。
 * 2. 算法：
 *      数学运算符为XOR(exclusive OR),在计算机中通常用"^"的符号表示，根据异或的运算规则，相同为0，不同为1；
 *      （1）计算6^3=110^011=101
 *      （2）异或运算具有可逆性。即：若a^b=c，则有b^c=a （a,b,c分别表示0或1）
 */

public class XORUtils {
    /**
     * 异或加密
     * @param str
     * @param privatekey
     * @return
     */
    public static String XorEncode(String str, String privatekey) {
        int[] snNum = new int[str.length()];
        String result = "";
        String temp = "";
        for (int i = 0, j = 0; i < str.length(); i++, j++) {
            if (j == privatekey.length())
                j = 0;
            snNum[i] = str.charAt(i) ^ privatekey.charAt(j);
        }
        for (int k = 0; k < str.length(); k++) {
            if (snNum[k] < 10) {
                temp = "00" + snNum[k];
            } else {
                if (snNum[k] < 100) {
                    temp = "0" + snNum[k];
                }
            }
            result += temp;
        }
        return result;
    }

    /**
     * 异或解密
     * @param str
     * @param privateKey
     * @return
     */
    public static String XorDecode(String str, String privateKey) {
        char[] snNum = new char[str.length() / 3];
        String result = "";

        for (int i = 0, j = 0; i < str.length() / 3; i++, j++) {
            if (j == privateKey.length())
                j = 0;
            int n = Integer.parseInt(str.substring(i * 3, i * 3 + 3));
            snNum[i] = (char) ((char) n ^ privateKey.charAt(j));
        }
        for (int k = 0; k < str.length() / 3; k++) {
            result += snNum[k];
        }
        return result;
    }
}
