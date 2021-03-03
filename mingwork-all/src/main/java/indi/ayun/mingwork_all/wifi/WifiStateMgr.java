package indi.ayun.mingwork_all.wifi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public class WifiStateMgr {
    private static WifiStateMgr wifiStateMgr;
    private WifiManager wifiManager;// 声明Wifi管理对象
    private WifiManager.WifiLock wifiLock;// Wifi锁
    private WifiStateMgr() {
    }

    public static WifiStateMgr getInstance() {
        if (wifiStateMgr == null) {
            synchronized (WifiStateMgr.class) {
                if (wifiStateMgr == null) {
                    wifiStateMgr = new WifiStateMgr();
                }
            }
        }
        return wifiStateMgr;
    }

    private Context mContext;

    public void init(Context context) {
        mContext = context;
        this.wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);// 获取Wifi服务
    }
    /**
     * 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
     *
     * @return 取值为WifiManager中的WIFI_STATE_ENABLED、WIFI_STATE_ENABLING、WIFI_STATE_DISABLED、WIFI_STATE_DISABLING、WIFI_STATE_UNKNOWN之一
     * @throws Exception 没有找到wifi设备
     */
    private int getWifiStateInt() throws Exception {
        WifiManager wifiManager = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE));
        if (wifiManager != null) {
            return wifiManager.getWifiState();
        } else {
            throw new Exception("wifi device not found!");
        }
    }

    /**
     * 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
     *
     * @return
     * @throws Exception
     */
    public String getWifiStateStr() throws Exception {
        String s = "未找到状态";
        switch (getWifiStateInt()) {
            case 3:
                s = "wifi已启用";
                break;
            case 4:
                s = "未知";
                break;
            case 1:
                s = "wifi有缺陷";
                break;
            case 2:
                s = "wifi可用";
                break;
            case 0:
                s = "wifi停用";
                break;
            default:
                break;
        }
        return s;
    }
    /**
     * 打开 wifi  需要权限CHANGE_WIFI_STATE
     * @return
     */
    @SuppressLint("MissingPermission")
    public boolean openWifi(){
        if (!wifiManager.isWifiEnabled()) {
            return wifiManager.setWifiEnabled(true);
        } else {
            return false;
        }
    }

    /**
     * 关闭Wifi 需要权限CHANGE_WIFI_STATE
     * @return
     */
    @SuppressLint("MissingPermission")
    public boolean closeWifi(){
        if (!wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return wifiManager.setWifiEnabled(false);
        }
    }
    /**
     * 添加一个连接 需要权限CHANGE_WIFI_STATE
     * @param config
     * @return
     */
    @SuppressLint("MissingPermission")
    public boolean addNetWordLink(WifiConfiguration config) {
        int NetId = wifiManager.addNetwork(config);
        return wifiManager.enableNetwork(NetId, true);
    }

    /**
     * 禁用一个链接 需要权限CHANGE_WIFI_STATE
     * @param NetId
     * @return
     */
    @SuppressLint("MissingPermission")
    public boolean disableNetWordLink(int NetId) {
        wifiManager.disableNetwork(NetId);
        return wifiManager.disconnect();
    }

    /**移除一个链接 需要权限CHANGE_WIFI_STATE
     *
     * @param NetId
     * @return
     */
    @SuppressLint("MissingPermission")
    public boolean removeNetworkLink(int NetId) {
        return wifiManager.removeNetwork(NetId);
    }

    /**
     *  锁定wifi
     *  锁定WiFI就是判断wifi是否建立成功，在这里使用的是held(握手) acquire
     */
    public void lockWifi() {
        wifiLock.acquire();
    }


    /**
     * 解锁wifi
     */
    public void unLockWifi() {
        if (!wifiLock.isHeld()) {
            wifiLock.release(); // 释放资源
        }
    }

    /**
     * 创建一个Wifi锁，需要时调用
     */
    public void createWifiLock() {
        wifiLock = wifiManager.createWifiLock("flyfly"); // 创建一个锁的标志
    }
}
