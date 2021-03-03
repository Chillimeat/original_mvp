package indi.ayun.mingwork_all.utils.phone;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import java.util.List;

public class SystemAbility {
    /**
     * 跳转至系统设置界面
     *
     * @param mContext
     *            上下文
     */
    public static void toSettingActivity(Context mContext) {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        mContext.startActivity(settingsIntent);
    }
    /**
     * 跳转至WIFI设置界面
     *
     * @param mContext
     *            上下文
     */
    public static void toWIFISettingActivity(Context mContext) {
        Intent wifiSettingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        mContext.startActivity(wifiSettingsIntent);
    }
    /**
     * Gps是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }
}
