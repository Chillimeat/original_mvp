package indi.ayun.mingwork_all.mlog.base;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.mlog.MLogUtil;

public class JsonLog {

    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(MLog.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(MLog.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        MLogUtil.printLine(tag, true);
        message = headString + MLog.LINE_SEPARATOR + message;
        String[] lines = message.split(MLog.LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        MLogUtil.printLine(tag, false);
    }
}
