package indi.ayun.mingwork_all.preference;

import android.content.Context;
import android.content.SharedPreferences;

import indi.ayun.mingwork_all.MingWork_All;
import indi.ayun.mingwork_all.base.UtilBase;
import indi.ayun.mingwork_all.utils.phone.Info;
import indi.ayun.mingwork_all.utils.verification.IsNothing;


public class CredentialPreferences extends UtilBase {

    private static final String KEY_USER_ACCOUNT = "account";//用户账号
    private static final String KEY_USER_TOKEN = "token";//用户永久的token
    private static final String KEY_USER_FIRST = "isFirst";//是否第一次打开
    private static final String KEY_UER_ILOGIN ="isLogin";//是否登录
    private static final String KEY_USER_CLOCK_ID="Uid";

    private static final String ANDROID_ID = "androidId";//用于判断是否第一次打开
    private static final String ANDROID_APPLICATION_STATE="application_state";//app的状态

    public static void saveUserUid(Long Uid) {
        saveLong(KEY_USER_CLOCK_ID, Uid);
    }
    public static Long getUserUid() {
        return getLong(KEY_USER_CLOCK_ID,0);
    }

    public static void saveUserLogin(boolean isLogin) {
        saveBoolean(KEY_UER_ILOGIN, isLogin);
    }
    public static boolean getUserLogin() {
        return getBoolean(KEY_UER_ILOGIN);
    }

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }
    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }
    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    public static void saveAndroidId(String token) {
        saveString(ANDROID_ID, token);
    }
    public static String getAndroidId() {
        return getString(ANDROID_ID);
    }

    public static void saveUserFirst(boolean isFirst) {
        saveBoolean(KEY_USER_FIRST, isFirst);
    }
    public static boolean getUserFirst() {
        return getBoolean(KEY_USER_FIRST);
    }

    public static void saveAPPState(int isFirst) {
        saveInt(ANDROID_APPLICATION_STATE, isFirst);
    }
    public static int getAPPState() {
        return getInt(ANDROID_APPLICATION_STATE,-1);
    }




    private static void saveLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }
    private static long getLong(String key, long value) {
        return getSharedPreferences().getLong(key, value);
    }

    private static void saveInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }
    private static int getInt(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }
    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    static SharedPreferences getSharedPreferences() {
        return MingWork_All.getContext().getSharedPreferences(MingWork_All.getAppName(), Context.MODE_PRIVATE);
    }

    /**
     * 是否第一次使用,true就是第一次
     * @return
     */
    public static boolean checkFirst() {
        if (IsNothing.onDataStr(CredentialPreferences.getAndroidId())) {
            CredentialPreferences.saveAndroidId(Info.getAndroidID(MingWork_All.getContext()));
            return true;
        } else {
            return false;
        }
    }
}
