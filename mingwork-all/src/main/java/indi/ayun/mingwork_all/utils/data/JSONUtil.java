package indi.ayun.mingwork_all.utils.data;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ayun on 2018/1/19.
 */

public class JSONUtil {
    /*
    读取资源文件中的json格式的数据，
    1。 在assets文件中建立点json文件，写入json数据
    2。 使用时String json = JSONUtill.readJSON(getContext());Bean bean = JSON.parseObject(json, Bean.class);
     */
    public static String readLib(Context context,String assets) {
        StringBuffer sb = new StringBuffer();
        AssetManager am = context.getAssets();
        try {
            InputStream open = am.open(assets);
            BufferedReader reader = new BufferedReader(new InputStreamReader(open));
            String next = "";
            while ((next = reader.readLine()) != null) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * json字符串生成JSONObject对象
     * @param json
     * @return
     */
    public static JSONObject string2JSONObject(String json) {
        JSONObject jsonObject = null;
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            jsonObject = (JSONObject) jsonParser.nextValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
