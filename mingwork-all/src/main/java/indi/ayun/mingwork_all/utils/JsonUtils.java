package indi.ayun.mingwork_all.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JsonUtils {
    public static final <T> T parseObject(String text, TypeReference<T> type) throws Exception {
        T t = null;
        try {
            t = (T) JSON.parseObject(text, type, new com.alibaba.fastjson.parser.Feature[0]);
        } catch (Exception e) {
            throw e;
        }
        return t;
    }

    public static final <T> T parseObject(String text, Class<T> clazz) throws Exception {
        T t = null;
        try {
            t = (T)JSON.parseObject(text, clazz);
        } catch (Exception e) {
            throw e;
        }
        return t;
    }

    public static final String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }
}
