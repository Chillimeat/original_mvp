package indi.ayun.mingwork_all.retrofit2.utils;

import android.text.TextUtils;

import indi.ayun.mingwork_all.retrofit2.bean.KeyValue;

import java.io.File;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpTool {
    public static Executor executor = Executors.newCachedThreadPool();

    public HttpTool() {
    }

    public static String appendUrlParams(String url, List<KeyValue> params, String charset) {
        if (url != null && params != null && !params.isEmpty()) {
            StringBuilder queryBuilder = new StringBuilder(url);
            if (!url.contains("?")) {
                queryBuilder.append("?");
            } else if (!url.endsWith("?")) {
                queryBuilder.append("&");
            }

            Iterator var4 = params.iterator();

            while(var4.hasNext()) {
                KeyValue keyValue = (KeyValue)var4.next();
                String name = keyValue.key;
                String value = keyValue.getValueStr();
                if (!TextUtils.isEmpty(name) && value != null) {
                    try {
                        queryBuilder.append(URLEncoder.encode(name, charset)).append("=").append(URLEncoder.encode(value, charset)).append("&");
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }
                }
            }

            if (queryBuilder.charAt(queryBuilder.length() - 1) == '&') {
                queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            }

            if (queryBuilder.charAt(queryBuilder.length() - 1) == '?') {
                queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            }

            return queryBuilder.toString();
        } else {
            return url;
        }
    }

    public static String getContentType(File file) {
        if (file != null) {
            boolean isPng = file.getName().lastIndexOf("png") > 0 || file.getName().lastIndexOf("PNG") > 0;
            if (isPng) {
                return "image/png; charset=UTF-8";
            }

            boolean isJpg = file.getName().lastIndexOf("jpg") > 0 || file.getName().lastIndexOf("JPG") > 0 || file.getName().lastIndexOf("jpeg") > 0 || file.getName().lastIndexOf("JPEG") > 0;
            if (isJpg) {
                return "image/jpeg; charset=UTF-8";
            }
        }

        return "application/octet-stream; charset=UTF-8";
    }
}
