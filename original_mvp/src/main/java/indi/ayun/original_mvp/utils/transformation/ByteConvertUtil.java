package indi.ayun.original_mvp.utils.transformation;

/**
 * @Description 将数据转成byte
 */
public class ByteConvertUtil {
    private ByteConvertUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * 十六进制字符串转换为byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * char转换为byte数组
     * @param c
     * @return
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * int转换为byte数组
     *
     * @param res
     * @return
     */
    public static byte[] intToByte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }
    /**
     * String转Byte
     * @param str
     * @return
     */
    public static byte stringToByte(String str) {
        byte b = 0;
        if (str != null) {
            try {
                b = Byte.parseByte(str);
            } catch (Exception e) {

            }
        }
        return b;
    }
}
