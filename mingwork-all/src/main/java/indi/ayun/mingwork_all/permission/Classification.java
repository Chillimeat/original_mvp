package indi.ayun.mingwork_all.permission;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class Classification {
    private String[] permission;
    //分类
    public static int TYPE_OTHER=100;//其他
    public static int TYPE_SILENT=101;//沉默
    public static int TYPE_FRIENDLY=102;//友好
    public static int TYPE_POPUP=103;//弹出授权
    public static int TYPE_OPERATION=104;//跳转

    //沉默权限：没有系统弹窗、没有自动授权或者检测不到、没有跳转页面；
    private String PER_SILENT_WRITE_SETTINGS=Manifest.permission.WRITE_SETTINGS;
    private String PER_SILENT_REQUEST_INSTALL_PACKAGES=Manifest.permission.REQUEST_INSTALL_PACKAGES;
    private String PER_SILENT_MODIFY_PHONE_STATE=Manifest.permission.MODIFY_PHONE_STATE;
    private String PER_SILENT_READ_LOGS=Manifest.permission.READ_LOGS;

    private String[] PER_SILENT={PER_SILENT_WRITE_SETTINGS,PER_SILENT_REQUEST_INSTALL_PACKAGES,PER_SILENT_MODIFY_PHONE_STATE,PER_SILENT_READ_LOGS};


    //友好权限：可以检测，自动授权
    private String PER_FRIENDLY_INTERNET=Manifest.permission.INTERNET;
    private String PER_FRIENDLY_ACCESS_NETWORK_STATE=Manifest.permission.ACCESS_NETWORK_STATE;
    private String PER_FRIENDLY_VIBRATE=Manifest.permission.VIBRATE;

    private String[] PER_FRIENDLY={PER_FRIENDLY_INTERNET,PER_FRIENDLY_ACCESS_NETWORK_STATE,PER_FRIENDLY_VIBRATE};


    //弹窗权限：有系统弹窗，可以检测，弹窗手动授权
    private String PER_POPUP_WRITE_EXTERNAL_STORAGE=Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String PER_POPUP_READ_EXTERNAL_STORAGE=Manifest.permission.READ_EXTERNAL_STORAGE;
    private String PER_POPUP_CAMERA=Manifest.permission.CAMERA;
    private String PER_POPUP_READ_PHONE_STATE=Manifest.permission.READ_PHONE_STATE;
    private String PER_POPUP_READ_PHONE_NUMBERS=Manifest.permission.READ_PHONE_NUMBERS;
    private String PER_POPUP_ACCESS_FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private String PER_POPUP_ACCESS_NOTIFICATION_POLICY=Manifest.permission.ACCESS_NOTIFICATION_POLICY;

    private String[] PER_POPUP={PER_POPUP_WRITE_EXTERNAL_STORAGE,PER_POPUP_READ_EXTERNAL_STORAGE,PER_POPUP_CAMERA,PER_POPUP_READ_PHONE_STATE,PER_POPUP_READ_PHONE_NUMBERS,
            PER_POPUP_ACCESS_FINE_LOCATION,PER_POPUP_ACCESS_NOTIFICATION_POLICY};

    //操作权限：没有系统弹窗，可以检测，跳转页面手动设置


    public int getType(String premission){
        int type=TYPE_OTHER;
        for (int i=0;i<PER_SILENT.length;i++){
            if (premission.equals(PER_SILENT[i])){
                type=TYPE_SILENT;
            }
        }
        for (int i=0;i<PER_FRIENDLY.length;i++){
            if (premission.equals(PER_FRIENDLY[i])){
                type=TYPE_FRIENDLY;
            }
        }
        for (int i=0;i<PER_POPUP.length;i++){
            if (premission.equals(PER_POPUP[i])){
                type=TYPE_POPUP;
            }
        }
        return type;
    }
}
