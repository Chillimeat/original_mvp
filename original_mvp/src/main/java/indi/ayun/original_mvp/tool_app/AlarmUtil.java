package indi.ayun.original_mvp.tool_app;

import java.util.Calendar;

import indi.ayun.original_mvp.utils.verification.IsNothing;

/**
 * @Description TODO
 * @Author Created by ayun on 2021/3/31 16:56.
 */
public class AlarmUtil {

    private static AlarmUtil alarmUtil;

    /**
     * 不需要单例，只要链式写法就好
     *
     * @return MediaUtil
     */
    public static AlarmUtil getInstance() {
        if (alarmUtil == null) alarmUtil = new AlarmUtil();
        return alarmUtil;
    }

    /**
     * 将获取的日期转成两位数
     *
     * @param a
     * @return
     */
    public String getTwoDigitTime(int a) {
        String A = String.valueOf(a);
        if (a < 10 && A.length() == 1) {
            A = "0" + A;
        }
        return A;
    }

    public String getTwoDigitNowS() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.SECOND);
        String A = String.valueOf(a);
        if (a < 10 && A.length() == 1) {
            A = "0" + A;
        }
        return A;
    }

    public String getTwoDigitNowMinutes() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.MINUTE);
        String A = String.valueOf(a);
        if (a < 10 && A.length() == 1) {
            A = "0" + A;
        }
        return A;
    }

    public String getTwoDigitNow24H() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.HOUR_OF_DAY);
        String A = String.valueOf(a);
        if (a < 10 && A.length() == 1) {
            A = "0" + A;
        }
        return A;
    }

    public String getTwoDigitNowD() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.DAY_OF_MONTH);
        String A = String.valueOf(a);
        if (a < 10 && A.length() == 1) {
            A = "0" + A;
        }
        return A;
    }

    public String getTwoDigitNowM() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.MONTH) + 1;
        String A = String.valueOf(a);
        if (a < 10 && A.length() == 1) {
            A = "0" + A;
        }
        return A;
    }


    public int getNowS() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.SECOND);
        return a;
    }

    public int getNowMinutes() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.MINUTE);
        return a;
    }

    public int getNow24H() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.HOUR_OF_DAY);
        return a;
    }

    public int getNowD() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.DAY_OF_MONTH);
        return a;
    }

    public int getNowM() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.MONTH) + 1;
        return a;
    }

    public int getNowY() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.YEAR);
        return a;
    }

    public int getNowW() {
        Calendar calendar = Calendar.getInstance();
        int a = calendar.get(Calendar.DAY_OF_WEEK);
        if (a != 1) {
            a = a - 1;
        } else {
            a = 7;
        }
        return a;
    }

    public int[] getF(int f) {
        int[] ff;
        String fs = String.valueOf(f);
        if (IsNothing.onAnything(fs)) {
            ff = new int[fs.length()];
            for (int i = 0; i < fs.length(); i++) {
                ff[i] = Integer.parseInt(fs.substring(i, i + 1));
            }
        } else {
            ff = new int[1];
            ff[0] = 0;
        }

        return ff;
    }
}
