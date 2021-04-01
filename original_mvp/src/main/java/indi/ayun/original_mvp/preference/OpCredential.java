package indi.ayun.original_mvp.preference;

import android.content.Context;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.utils.phone.Info;
import indi.ayun.original_mvp.utils.verification.IsNothing;


public class OpCredential {
    private final String KEY_USER_ACCOUNT = "account";//用户账号
    private final String KEY_USER_TOKEN = "token";//用户永久的token
    private final String KEY_USER_FIRST = "isFirst";//是否第一次打开
    private final String KEY_UER_ILOGIN ="isLogin";//是否登录
    private final String KEY_USER_CLOCK_ID="Uid";

    private final String ANDROID_ID = "androidId";//用于判断是否第一次打开
    private final String ANDROID_APPLICATION_STATE="application_state";//app的状态

    public void saveUserUid(Long Uid) {
        op.saveLong(KEY_USER_CLOCK_ID, Uid);
    }
    public Long getUserUid() {
        return op.getLong(KEY_USER_CLOCK_ID,0);
    }

    public void saveUserLogin(boolean isLogin) {
        op.saveBoolean(KEY_UER_ILOGIN, isLogin);
    }
    public boolean getUserLogin() {
        return op.getBoolean(KEY_UER_ILOGIN,false);
    }

    public void saveUserAccount(String account) {
        op.saveString(KEY_USER_ACCOUNT, account);
    }
    public String getUserAccount(boolean identification) {
        if (!getUserLogin()) {
            if (identification) {
                return "游客" + Info.getAndroidID(OriginalMVP.getContext());
            }
        }
        if (null==op.getString(KEY_USER_ACCOUNT)||!IsNothing.onAnything(op.getString(KEY_USER_ACCOUNT))){
            if (identification) {
                return "游客" + Info.getAndroidID(OriginalMVP.getContext());
            }
        }
        return op.getString(KEY_USER_ACCOUNT);
    }

    public void saveUserToken(String token) {
        op.saveString(KEY_USER_TOKEN, token);
    }
    public String getUserToken() {
        return op.getString(KEY_USER_TOKEN);
    }

    public void saveAndroidId(String token) {
        op.saveString(ANDROID_ID, token);
    }
    public String getAndroidId() {
        return op.getString(ANDROID_ID);
    }

    public void saveUserFirst(boolean isFirst) {
        op.saveBoolean(KEY_USER_FIRST, isFirst);
    }
    public boolean getUserFirst() {
        return op.getBoolean(KEY_USER_FIRST,false);
    }

    public void saveAPPState(int isFirst) {
        op.saveInt(ANDROID_APPLICATION_STATE, isFirst);
    }
    public int getAPPState() {
        return op.getInt(ANDROID_APPLICATION_STATE,-1);
    }

    /**
     * 是否第一次使用,true就是第一次
     * @return
     */
    public boolean isFirst() {
        if (IsNothing.onDataStr(getAndroidId())) {
            saveAndroidId(Info.getAndroidID(OriginalMVP.getContext()));
            return true;
        } else {
            return false;
        }
    }
    //单例模式
    private static OpCredential instance;

    /**
     * 单列堆栈集合对象
     * @return ActivityMgr 单列堆栈集合对象
     */
    public static OpCredential getInstance() {
        if (instance == null) {
            synchronized (OpCredential.class){
                if (instance == null) {
                    instance = new OpCredential();
                }
            }
        }
        return instance;
    }

    private Op op;

    public OpCredential init(Context context) {
        op=new Op(context,OriginalMVP.getAppName());
        return this;
    }

}
