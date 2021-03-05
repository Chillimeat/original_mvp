package indi.ayun.original_mvp.utils.transformation;

import indi.ayun.original_mvp.utils.storage.StreamUtil;

import java.io.File;
import java.io.FileInputStream;

public class StringConvertUtil {

    /**
     * 将文件转成字符串
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = StreamUtil.convertStreamToString(fin);
        // Make sure you close all streams.
        fin.close();
        return ret;
    }
    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    public static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }
}
