package indi.ayun.original_mvp.utils.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.WindowManager;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.base.UtilBase;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.utils.transformation.StringConvertUtil;
import indi.ayun.original_mvp.utils.verification.IsNothing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Pattern;

public class Info extends UtilBase {
//<p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
    /**
     * 获得电话管理实例对象
     * @return TelephonyManager 电话管理实例对象
     */
    public TelephonyManager getTM() {
        TelephonyManager telephonyManager = null;
        telephonyManager = (TelephonyManager) OriginalMVP.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        MLog.i("SysInfoUtils-->>getSysTelephonyManager",  telephonyManager + "");
        return telephonyManager;
    }
    /**
     * 获得客户端操作系统名称
     * @return
     */
    public String getSysClientOs() {
        String OsName = Build.ID;
        return OsName;
    }
    /**
     * 获取操作系统的版本号
     * @return String 系统版本号
     */
    public String getSysRelease() {
        String release = Build.VERSION.RELEASE;
        MLog.i("SysInfoUtils-->>getSysRelease",  release);
        return release;
    }

    /**
     * 获取当前操作系统的语言
     * @return String 系统语言
     */
    public String getSysLanguage() {
        String language = Locale.getDefault().getLanguage();
        MLog.i("SysInfoUtils-->>getSysLanguage",  language);
        return language;
    }
    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取手机CPU序列号
     * @return String cpu序列号(16位) 读取失败为"0000000000000000"
     */
    public String getSysCPUSerialNum() {
        String str = "", strCPU = "", cpuSerialNum = "0000000000000000";
        try {
            //读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            //查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        //提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        //去空格
                        cpuSerialNum = strCPU.trim();
                        break;
                    }
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuSerialNum;
    }
    /**
     * 获取手机系统版本号
     *
     * @return
     */
    public int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    /**
     * 获取手机型号
     */
    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取手机imei串号 ,GSM手机的 IMEI15位十进制数 和 CDMA手机的 MEID14位十六进制数.
     * Android 6.0以下的系统，即使是双卡双待的设备，也只能获取一个DeviceId。
     * Android 6.0以上的系统，使用getDeviceId可传入下标号获取多个设备号信息。get
     * Android 8.0以上的系统，TelephonyManager提供了两个独立的API以获取IMEI和MEID： getImei 、 getMeid
     * 这是手机的国际设备识别码
     * @param context
     */
    @SuppressLint("MissingPermission")
    public String getPhoneImei(Context context,int slotlndex) {

        String imei="";
        if (getTM() == null)
            imei= "";
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            imei= getTM().getDeviceId();
        }else if ( Build.VERSION_CODES.M<=Build.VERSION.SDK_INT&&Build.VERSION.SDK_INT<Build.VERSION_CODES.O) {
            imei= getTM().getDeviceId(slotlndex);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imei= getTM().getImei(slotlndex);
        }
        return imei;
    }
    @SuppressLint("MissingPermission")
    public String getPhoneMeid(Context context,int slotlndex) {
        String meid="";
        if (getTM() == null)
            meid= "";
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            meid= getTM().getDeviceId();
        }else if ( Build.VERSION_CODES.M<=Build.VERSION.SDK_INT&&Build.VERSION.SDK_INT<Build.VERSION_CODES.O) {
            meid= getTM().getDeviceId(slotlndex);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            meid= getTM().getMeid(slotlndex);
        }
        return meid;
    }

    /**
     * 获取设备号SN
     * @return
     */
    @SuppressLint("MissingPermission")
    public String getSN(){
        String sn="";
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.O) {
            sn = Build.SERIAL;
        }else{
            sn=Build.getSerial();
        }
        return sn;
    }
    /**
     * 获取Android ID
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getAndroidID(Context context){
       return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    /**
     * 获取手机sim卡号,IMSI
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public String getPhoneIMSI(Context context) {
        if (getTM() == null)
            return null;
        return getTM().getSubscriberId();
    }
    /**
     * 获取获取SIM的ICCID
     */
    @SuppressLint("MissingPermission")
    public String getSimICCID(Context context) {
        return getTM().getSimSerialNumber() ;
    }

    /**
     * 获取手机的Mac地址
     * @param context
     * @return
     */
    public static String getMac(Context context) {

        String strMac = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            MLog.e("=====", "6.0以下");
            strMac = getLocalMacAddressFromWifiInfo(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            strMac = getMacAddress(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (IsNothing.onAnything(getMacAddress())) {
                strMac = getMacAddress();
                return strMac;
            } else if (IsNothing.onAnything(getMachineHardwareAddress())) {
                strMac = getMachineHardwareAddress();
                return strMac;
            } else {
                strMac = getLocalMacAddressFromBusybox();
                return strMac;
            }
        }
        return "02:00:00:00:00:00";
    }
    private static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac = winfo.getMacAddress();
        return mac;
    }
    private static String getMacAddress(Context context) {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (IsNothing.onAnything(macAddress0)) {
                return macAddress0;
            }
        }
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            MLog.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                MLog.e("----->" + "NetInfoManager",
                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial;
    }
    private static String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                MLog.e("----->" + "NetInfoManager",
                        "getMacAddress0:" + e.toString());
            }

        }
        return "";

    }
    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            MLog.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }
    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }
    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }
    private static String getMacAddress() {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }
    private static String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");
        // 如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "网络异常";
        }
        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            result = Mac;
        }
        return result;
    }
    private static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            while ((line = br.readLine()) != null
                    && line.contains(filter) == false) {
                result += line;
            }

            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = StringConvertUtil.bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }
    /**
     * 获取本地化ip地址
     * @return
     */
    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface
                    .getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface
                        .nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取WIFI连接下的ip地址
     * @param wifiManager
     * @return
     */
    public static String getWifiIP(WifiManager wifiManager) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ip = intToIp(wifiInfo.getIpAddress());
        return ip != null ? ip : "";
    }

    /**
     * 获取GPRS连接下的ip地址
     * @return
     */
    public static String getGPRSIP() {
        String ip = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                for (Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip = null;
        }
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取手机号
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public String getPhoneNum(Context context) {
        if (getTM() == null)
            return null;
        return getTM().getLine1Number();
    }

    /**
     * 获取国际长途区号
     */
    @SuppressLint("MissingPermission")
    public  String getNetworkCountryIso(Context context) {
        return getTM().getNetworkCountryIso();
    }
    /**
     * 获取获取网络运营商代号（MCC+MNC）
     */
    @SuppressLint("MissingPermission")
    public  String getNetworkOperator(Context context) {
        return getTM().getNetworkOperator();
    }
    /**
     * 获取网络运营商名称
     */
    @SuppressLint("MissingPermission")
    public  String getNetworkOperatorName(Context context) {
        return getTM().getNetworkOperatorName() ;
    }
    /**
     * 获取设备当前位置
     */
    @SuppressLint("MissingPermission")
    public  CellLocation getCellLocation(Context context) {
        return getTM().getCellLocation() ;
    }
    /**
     * 获取手机状态(0：无活动 1：响铃 2：待机)
     * @param  context 上下文
     * @return Integer 手机状态
     */
    public  Integer getPhoneState(Context context) {
        Integer callState = getTM().getCallState();
        return callState;
    }
    /**
     * 获取手机的客户身份，销往不同地区的手机CID不同
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public  int getCID(Context context) {
        CellLocation cellLocation = getCellLocation(context);
        int cid=0;
        if(cellLocation instanceof GsmCellLocation) {
            GsmCellLocation location = (GsmCellLocation) cellLocation;
            cid = location.getCid();
        }
        return cid;
    }

    /**
     * 获取手机的位置区域编码
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public  int getLAD(Context context) {
        CellLocation cellLocation = getCellLocation(context);
        int lad=0;
        if(cellLocation instanceof GsmCellLocation) {
            GsmCellLocation location = (GsmCellLocation) cellLocation;
            lad = location.getLac();
        }
        return lad;
    }

    /**
     * 小区分配主扰码，小区识别
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public  int getPSC(Context context) {
        CellLocation cellLocation = getCellLocation(context);
        int psc=0;
        if(cellLocation instanceof GsmCellLocation) {
            GsmCellLocation location = (GsmCellLocation) cellLocation;
            psc = location.getPsc();
        }
        return psc;
    }
    /**
     * 获取手机类型
     */
    @SuppressLint("MissingPermission")
    public  String getPhoneType(Context context) {
        return getTM().getPhoneType()+"";
    }
    /**
     * 获取SIM卡提供商的ISO国家代码
     */
    @SuppressLint("MissingPermission")
    public  String getSimCountryIso(Context context) {
        return getTM().getSimCountryIso();
    }
    /**
     * 判断是否是平板
     */
    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    /**
     * 获取Cpu内核数
     * @return
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }

            });
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获得SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字。(注：SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断))
     * @param context 上下文
     * @return String SIM移动国家编码和移动网络编码
     */
    public String getSimOperator(Context context) {
        String code = "";
        if (getTM().getSimState() == 5) {
            code = getTM().getSimOperator();
        }
        return code;
    }

    /**
     * 服务商名称(注:例如：中国移动、联通SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断)).
     * @param context 上下文
     * @return String 服务商名称
     */
    public  String getSimOperatorName(Context context) {
        String simOperatorName = "";
        if (getTM().getSimState() == 5) {
            simOperatorName = getTM().getSimOperatorName();
        }
        MLog.i("SysInfoUtils-->>getSysSimPrivatorName",  simOperatorName);
        return simOperatorName;
    }
    /**
     * 获取运营商信息(三大运营商)
     * @param content 上下文
     * @return String 获取运营商名称
     */
    public String getCarrier(Context content) {
        String moblieType = "";
        @SuppressLint("MissingPermission") String imsi = getTM().getSubscriberId();
        if (imsi != null && imsi.length() > 0) {
            //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //中国移动
                moblieType = "China Mobile";
            } else if (imsi.startsWith("46001")) {
                //中国联通
                moblieType = "China Unicom";
            } else if (imsi.startsWith("46003")) {
                //中国电信
                moblieType = "China Telecom";
            }
        }
        MLog.i("SysInfoUtils-->>getSysCarrier",  moblieType);
        return moblieType;
    }
    /**
     * 获取手机状态信息getSimState
     *      * @see #SIM_STATE_UNKNOWN
     *      * @see #SIM_STATE_ABSENT
     *      * @see #SIM_STATE_PIN_REQUIRED
     *      * @see #SIM_STATE_PUK_REQUIRED
     *      * @see #SIM_STATE_NETWORK_LOCKED
     *      * @see #SIM_STATE_READY
     *      * @see #SIM_STATE_NOT_READY
     *      * @see #SIM_STATE_PERM_DISABLED
     *      * @see #SIM_STATE_CARD_IO_ERROR
     *      * @see #SIM_STATE_CARD_RESTRICTED
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     */
    @SuppressLint("MissingPermission")
    public String getSimState(Context context,int slotIndex) {
        int a=0;String s="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            a=getTM().getSimState(slotIndex);
        }else {
            a=getTM().getSimState();
        }
        switch (a){
            case 0:
                s="未知";
                break;
            case 1:
                s="不存在";
                break;
            case 2:
                s="需要PIN";
                break;
            case 3:
                s="需要PUK";
                break;
            case 4:
                s="网络锁定";
                break;
            case 5:
                s="就绪";
                break;
            case 6:
                s="未就绪";
                break;
            case 7:
                s="已禁用";
                break;
            case 8:
                s="卡IO错误";
                break;
            case 9:
                s="受限制的SIM卡";
            default:;
        }
        return s;
    }
    /**
     * 获取手机状软件版本
     */
    @SuppressLint("MissingPermission")
    public String getDeviceSoftwareVersion(Context context) {
        return getTM().getDeviceSoftwareVersion();
    }

    /**
     * 获取手机电话留言号码
     */
    @SuppressLint("MissingPermission")
    public String getVoiceMailNumber(Context context) {
        return getTM().getVoiceMailNumber();
    }



    /**
     * 获取硬件型号
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取编译厂商
     * @return
     */
    public static String getBuildBrand() {
        return Build.BRAND;
    }

    /**
     *获取编译服务器主机
     * @return
     */
    public static String getBuildHost() {
        return Build.HOST;
    }

    /**
     *获取系统编译作者
     * @return
     */
    public static String getBuildUser() {
        return Build.USER;
    }

    /**
     *获取编译系统版本(5.1)
     * @return
     */
    public static String getBuildVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     *获取开发代号
     * @return
     */
    public static String getBuildVersionCodename() {
        return Build.VERSION.CODENAME;
    }

    /**
     * 获取源码控制版本号
     * @return
     */
    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     *获取硬件制造厂商
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     *获取系统启动程序版本号
     * @return
     */
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    /**
     * 获取屏幕显示id
     * @param ctx
     * @return
     */
    public static String getScreenDisplayID(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        return String.valueOf(wm.getDefaultDisplay().getDisplayId());
    }

    /**
     *获取语言
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取国家
     * @param ctx
     * @return
     */
    public static String getCountry(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        Locale locale = Locale.getDefault();
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY ? tm.getSimCountryIso().toLowerCase(Locale.getDefault()) : locale.getCountry().toLowerCase(locale);
    }

    /**
     *获取GSF序列号
     * @param context
     * @return
     */
    //<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES"/>
    public static String getGSFID(Context context) {
        String result;
        final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        final String ID_KEY = "android_id";
        String[] params = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);
        if (c == null || !c.moveToFirst() || c.getColumnCount() < 2) {
            return null;
        } else {
            result = Long.toHexString(Long.parseLong(c.getString(1)));
        }
        c.close();
        return result;
    }
    /**
     *  获取设备信息
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取主板信息
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     *  获取基带版本(无线电固件版本 Api14以上)
     * @return
     */
    public static String getRadioVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ? Build.getRadioVersion() : "";
    }



}
