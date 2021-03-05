package indi.ayun.original_mvp.utils.storage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import indi.ayun.original_mvp.mlog.MLog;

public class StreamUtil {

    /**
     * 描述：从输入流中获得String.
     * @param is 输入流
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            // 最后一个\n删除
            if (sb.indexOf("\n") != -1
                    && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    /**
     * 根据地址获取InputStream
     *
     * @param urlStr
     * @return InputStream
     * @throws IOException
     */
    public InputStream getInputStreamByStringURL(String urlStr) {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            MLog.e("AppFileMgr-->>getInputStreamByStringURL:", "根据地址获取InputStream失败！" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            MLog.e("AppFileMgr-->>getInputStreamByStringURL:", "根据地址获取InputStream失败！" + e.getMessage());
        }
        return inputStream;
    }
}
