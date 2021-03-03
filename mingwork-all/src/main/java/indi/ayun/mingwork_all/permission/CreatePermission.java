package indi.ayun.mingwork_all.permission;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.ayun.mingwork_all.R;
import com.luck.picture.lib.BuildConfig;

import indi.ayun.mingwork_all.config.ConstantConfig;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.notice.ToastUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static indi.ayun.mingwork_all.utils.app.App.getPackageName;
import static indi.ayun.mingwork_all.utils.phone.Info.getManufacturer;

public class CreatePermission extends BaseMPermission {
    private String[] permission;
    private Activity activity;

    public static int YES = 1;
    public static int NO = -1;
    public static int UNKNOWN = 0;

    private CreatePermission(Activity activity) {
        this.activity = activity;
    }

    /**
     * 在base赋值环境
     * @param activity
     * @return
     */
    public static CreatePermission with(Activity activity) {
        return new CreatePermission(activity);
    }

    /**
     * 赋值要检查的权限
     * @param permission
     * @return
     */
    public CreatePermission checkPermission(String... permission) {
        this.permission = permission;
        return this;
    }

    /**
     * 开始检查权限，控制是否检查低版本
     * @param lowerVersion
     * @return
     */
    public int check(boolean lowerVersion) {
        return checkPermission(activity, permission,lowerVersion);
    }
    /**
     * 开始检查权限，默认不检查低版本
     * @return
     */
    public int check() {
        return checkPermission(activity, permission,false);
    }
    /**
     * 开始检查权限，控制返回
     * @param lowerVersion
     * @return
     */
    public boolean checkYes(boolean lowerVersion) {
        if (checkPermission(activity, permission,lowerVersion)==CreatePermission.YES)
        return true;
        else return false;
    }

    /**
     * 开始检查权限，控制返回
     * @return
     */
    public boolean checkYes() {
        if (checkPermission(activity, permission,false)==CreatePermission.YES)
            return true;
        else return false;
    }

    /**
     * 开始检查权限，控制返回
     * @param lowerVersion
     * @return
     */
    public boolean checkNo(boolean lowerVersion) {
        if (checkPermission(activity, permission,lowerVersion)==CreatePermission.NO)
            return true;
        else return false;
    }

    /**
     * 开始检查权限，控制返回
     * @return
     */
    public boolean checkNo() {
        if (checkPermission(activity, permission,false)==CreatePermission.NO)
            return true;
        else return false;
    }


    /**
     * 检查权限，
     * @param object 环境
     * @param permissions 权限
     * @param lowerVersion  是否检查低版本
     * @return
     */
    @SuppressLint("WrongConstant")
    @TargetApi(value = Build.VERSION_CODES.M)
    private static int checkPermission(Object object, String[] permissions,boolean lowerVersion) {
        if (!isOverMarshmallow()&&!lowerVersion) {//低版本不用
            return YES;
        }

        if (permissions != null && permissions.length > 0) {//传过来的权限名称为空直接是同意
            int b = YES;
            Classification classification = new Classification();//分类
            for (int i = 0; i < permissions.length; i++) {
                if (classification.getType(permissions[i]) == Classification.TYPE_FRIENDLY) {//友好权限直接通过
                    b = YES;
                } else if (classification.getType(permissions[i]) == Classification.TYPE_POPUP
                        ||classification.getType(permissions[i]) == Classification.TYPE_OTHER) {//弹窗权限，或者没有录入的权限

                    if (getActivity(object).getApplicationInfo().targetSdkVersion<23&&lowerVersion) {
                        if (PermissionChecker.checkSelfPermission(getActivity(object), permissions[i]) != PackageManager.PERMISSION_GRANTED ) {
                            b = NO;
                        } else {
                            b = YES;
                        }
                    }else {
                        if (ActivityCompat.checkSelfPermission(getActivity(object), permissions[i]) != PackageManager.PERMISSION_GRANTED ) {
                            b = NO;
                        } else {
                            b = YES;
                        }
                    }
                } else {
                    b = UNKNOWN;
                }
            }
            return b;
        } else {
            return YES;
        }
    }

    /**
     * 二次授权包含：允许通知；顶部预览；锁屏显示；显示详情；后台弹出等。能检测到的就只有允许通知一项
     *
     * @return
     */
    public int checkNotificationPermission() {
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        ApplicationInfo appInfo = activity.getApplicationInfo();
        String pkg = activity.getApplicationContext().getPackageName();
        AppOpsManager mAppOps = (AppOpsManager) activity.getSystemService(Context.APP_OPS_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int b = UNKNOWN;
            try {

                Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
                sServiceField.setAccessible(true);
                Object sService = sServiceField.invoke(notificationManager);

                int uid = appInfo.uid;

                Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage", String.class, Integer.TYPE);
                method.setAccessible(true);
                if ((boolean) method.invoke(sService, pkg, uid)) {
                    b = YES;
                } else {
                    b = NO;
                }
            } catch (Exception e) {
                MLog.e(e);
            }
            return b;
        } else {
            //Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKA
            int uid = appInfo.uid;
            int b = UNKNOWN;
            Class appOpsClass;
            try {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                if (((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED)) {
                    b = YES;
                } else {
                    b = NO;
                }
            } catch (ClassNotFoundException e) {
                MLog.e(e);
            } catch (NoSuchMethodException e) {
                MLog.e(e);
            } catch (NoSuchFieldException e) {
                MLog.e(e);
            } catch (InvocationTargetException e) {
                MLog.e(e);
            } catch (IllegalAccessException e) {
                MLog.e(e);
            }
            return b;

        }
    }

    /**
     * 检测免打扰权限
     *
     * @return
     */
    public int checkNotificationPolicyAccessGranted() {

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            return NO;
        } else {
            return YES;
        }
    }

    /**
     * 未知安装包
     */
    public int checkStartInstallPermissionSettingActivity() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (activity.getPackageManager().canRequestPackageInstalls()) {
                return YES;
            } else {
                return NO;
            }
        } else {
            return YES;
        }
    }

    /**
     * 唤醒权限，后台弹出界面、锁屏显示。
     */
    public int checkBackgroundStart() {

        AppOpsManager ops = (AppOpsManager) activity.getSystemService(Context.APP_OPS_SERVICE);
        try {
            int op = 10021; // >= 23
            // ops.checkOpNoThrow(op, uid, packageName)
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), activity.getPackageName());
            if (result == AppOpsManager.MODE_ALLOWED) {
                return YES;
            } else {
                return NO;
            }
        } catch (Exception e) {
            MLog.e("not support:" + e.getMessage());
        }
        return UNKNOWN;
    }

    /**
     * 悬浮窗
     */
    public int checkSuspendedWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                return NO;
            } else {
                return YES;
            }
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Deoz
     */
    public int checkIgnoringBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = activity.getPackageName();
            PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(packageName)) {
                return YES;
            } else {
                return NO;
            }
        } else {
            return UNKNOWN;
        }
    }

    /**
     * 修改系统权限
     */
    public int checkModifySys(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //大于等于23 请求权限
            if ( !Settings.System.canWrite(activity.getApplicationContext())) {
                return NO;
            }else {
                return YES;
            }
        }else{
            return YES;
        }
    }

    /**
     * 后台高耗电保护
     */
    public int checkHighConsumption() {
        return UNKNOWN;
    }

    //---------------------------------------------------------------------------------------------跳转
    //跳转到通知管理
    public void gotoNotificationSetting() {
        ApplicationInfo appInfo = activity.getApplicationInfo();
        String pkg = activity.getApplicationContext().getPackageName();

        int uid = appInfo.uid;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
                } else {
                    //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                    intent.putExtra("app_package", pkg);
                    intent.putExtra("app_uid", uid);
                }
                activity.startActivityForResult(intent,  ConstantConfig.REQUEST_PERMISSION_NOTIFICATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            gotoAPPDetails();
        }
    }


    //跳转至自启动授权页面
    private static HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>() {
        {

            put("Xiaomi", Arrays.asList(
                    "com.miui.securitycenter/com.miui.permcenter.autostart.AutoStartManagementActivity",//MIUI10_9.8.1(9.0)
                    "com.miui.securitycenter"
            ));

            put("samsung", Arrays.asList(
                    "com.samsung.android.sm_cn/com.samsung.android.sm.ui.ram.AutoRunActivity",
                    "com.samsung.android.sm_cn/com.samsung.android.sm.ui.appmanagement.AppManagementActivity",
                    "com.samsung.android.sm_cn/com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity",
                    "com.samsung.android.sm_cn/.ui.ram.RamActivity",
                    "com.samsung.android.sm_cn/.app.dashboard.SmartManagerDashBoardActivity",

                    "com.samsung.android.sm/com.samsung.android.sm.ui.ram.AutoRunActivity",
                    "com.samsung.android.sm/com.samsung.android.sm.ui.appmanagement.AppManagementActivity",
                    "com.samsung.android.sm/com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity",
                    "com.samsung.android.sm/.ui.ram.RamActivity",
                    "com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity",

                    "com.samsung.android.lool/com.samsung.android.sm.ui.battery.BatteryActivity",
                    "com.samsung.android.sm_cn",
                    "com.samsung.android.sm"
            ));

            //componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");//锁屏清理
            put("HUAWEI", Arrays.asList(
                    "com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity",//EMUI9.1.0(方舟,9.0)
                    "com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity",//26
                    "com.huawei.systemmanager/.optimize.process.ProtectActivity",
                    "com.huawei.systemmanager/.optimize.bootstart.BootStartActivity",
                    "com.huawei.systemmanager/com.huawei.permissionmanager.ui.MainActivity",//21
                    "com.huawei.systemmanager"//最后一行可以写包名, 这样如果签名的类路径在某些新版本的ROM中没找到 就直接跳转到对应的安全中心/手机管家 首页.
            ));

            put("vivo", Arrays.asList(
                    "com.iqoo.secure/.ui.phoneoptimize.BgStartUpManager",
                    "com.iqoo.secure/.safeguard.PurviewTabActivity",
                    "com.vivo.permissionmanager/.activity.BgStartUpManagerActivity",
                    //"com.iqoo.secure/.ui.phoneoptimize.AddWhiteListActivity", //这是白名单, 不是自启动
                    "com.iqoo.secure",
                    "com.vivo.permissionmanager"
            ));

            put("Meizu", Arrays.asList(
                    "com.meizu.safe/.permission.SmartBGActivity",//Flyme7.3.0(7.1.2)
                    "com.meizu.safe/.permission.PermissionMainActivity",//网上的
                    "com.meizu.safe"
            ));

            put("OPPO", Arrays.asList(
                    "com.coloros.safecenter/.startupapp.StartupAppListActivity",
                    "com.coloros.safecenter/.permission.startup.StartupAppListActivity",
                    "com.oppo.safe/.permission.startup.StartupAppListActivity",
                    "com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerUsageModelActivity",
                    "com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity",
                    "com.coloros.safecenter",
                    "com.oppo.safe",
                    "com.coloros.oppoguardelf"
            ));

            put("oneplus", Arrays.asList(
                    "com.oneplus.security/.chainlaunch.view.ChainLaunchAppListActivity",
                    "com.oneplus.security"
            ));
            put("letv", Arrays.asList(
                    "com.letv.android.letvsafe/.AutobootManageActivity",
                    "com.letv.android.letvsafe/.BackgroundAppManageActivity",//应用保护
                    "com.letv.android.letvsafe"
            ));
            put("zte", Arrays.asList(
                    "com.zte.heartyservice/.autorun.AppAutoRunManager",
                    "com.zte.heartyservice"
            ));
            //金立
            put("F", Arrays.asList(
                    "com.gionee.softmanager/.MainActivity",
                    "com.gionee.softmanager"
            ));

            //以下为未确定(厂商名也不确定)
            put("smartisanos", Arrays.asList(
                    "com.smartisanos.security/.invokeHistory.InvokeHistoryActivity",
                    "com.smartisanos.security"
            ));
            //360
            put("360", Arrays.asList(
                    "com.yulong.android.coolsafe/.ui.activity.autorun.AutoRunListActivity",
                    "com.yulong.android.coolsafe"
            ));
            //360
            put("ulong", Arrays.asList(
                    "com.yulong.android.coolsafe/.ui.activity.autorun.AutoRunListActivity",
                    "com.yulong.android.coolsafe"
            ));
            //酷派
            put("coolpad"/*厂商名称不确定是否正确*/, Arrays.asList(
                    "com.yulong.android.security/com.yulong.android.seccenter.tabbarmain",
                    "com.yulong.android.security"
            ));
            //联想
            put("lenovo"/*厂商名称不确定是否正确*/, Arrays.asList(
                    "com.lenovo.security/.purebackground.PureBackgroundActivity",
                    "com.lenovo.security"
            ));
            put("htc"/*厂商名称不确定是否正确*/, Arrays.asList(
                    "com.htc.pitroad/.landingpage.activity.LandingPageActivity",
                    "com.htc.pitroad"
            ));
            //华硕
            put("asus"/*厂商名称不确定是否正确*/, Arrays.asList(
                    "com.asus.mobilemanager/.MainActivity",
                    "com.asus.mobilemanager"
            ));

        }
    };
    //自启动
    public void gotoJumpStartInterface() {

        MLog.d("当前手机类型：" + getManufacturer());
        Set<Map.Entry<String, List<String>>> entries = hashMap.entrySet();
        boolean has = false;
        for (Map.Entry<String, List<String>> entry : entries) {
            String manufacturer = entry.getKey();
            List<String> actCompatList = entry.getValue();
            if (Build.MANUFACTURER.equalsIgnoreCase(manufacturer)) {
                for (String act : actCompatList) {
                    try {
                        Intent intent;
                        if (act.contains("/")) {
                            intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ComponentName componentName = ComponentName.unflattenFromString(act);
                            intent.setComponent(componentName);
                        } else {
                            //找不到? 网上的做法都是跳转到设置... 这基本上是没意义的 基本上自启动这个功能是第三方厂商自己写的安全管家类app
                            //所以我是直接跳转到对应的安全管家/安全中心
                            intent = activity.getPackageManager().getLaunchIntentForPackage(act);
                        }
                        activity.startActivity(intent);
                        has = true;
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!has) {
            Intent intent = new Intent();
            try {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName componentName = null;
                if (getManufacturer().equals("Letv")) { // 乐视2测试通过
                    intent.setAction("com.letv.android.permissionautoboot");
                } else if (getManufacturer().equals("Xiaomi")) {
                    intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", activity.getPackageName());
                } else if (getManufacturer().equals("vivo")) {
                    if (((Build.MODEL.contains("Y85")) && (!Build.MODEL.contains("Y85A"))) || (Build.MODEL.contains("vivo Y53L"))) {
                        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
                        intent.putExtra("packagename", activity.getPackageName());
                        intent.putExtra("tabId", "1");
                    } else {
                        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
                        intent.setAction("secure.intent.action.softPermissionDetail");
                        intent.putExtra("packagename", activity.getPackageName());
                    }
                } else if (getManufacturer().equals("OPPO")) {
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    componentName = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
                    intent.setComponent(componentName);
                } else {
                    // 将用户引导到系统设置页面
                    if (Build.VERSION.SDK_INT >= 9) {
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                        intent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                    }
                }
                activity.startActivity(intent);

            } catch (Exception e) {//抛出异常就直接打开设置页面
                MLog.e(e.getMessage());
                if (getManufacturer().equals("OPPO")) {
                    try {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("pkg_name", activity.getPackageName());
                        intent.putExtra("app_name", activity.getString(R.string.app_name));
                        intent.putExtra("class_name", "com.welab.notificationdemo.MainActivity");
                        ComponentName comp = new ComponentName("com.coloros.notificationmanager", "com.coloros" + ".notificationmanager.AppDetailPreferenceActivity");
                        intent.setComponent(comp);
                    } catch (Exception e1) {
                        // 否则跳转到应用详情
                        try {
                            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                        } catch (Exception e2) {
                            intent = new Intent(Settings.ACTION_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    }
                } else if (getManufacturer().equals("Xiaomi")) {
                    try {
                        // MIUI 5/6/7
                        intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        intent.putExtra("extra_pkgname", activity.getPackageName());
                    } catch (Exception e1) {
                        // 否则跳转到应用详情
                        try {
                            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                        } catch (Exception e2) {
                            intent = new Intent(Settings.ACTION_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    }

                } else {
                    intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                activity.startActivity(intent);
            }
        }

    }

    /**
     * 去应用详情页
     */
    public void gotoAPPDetails() {
        try {
            Intent intent;
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        } catch (Exception e2) {
            gotoSetting();
        }

    }

    /**
     * 去应用详情页
     */
    public void gotoAPPPremissionDetails() {
        try {
            Intent intent;
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        } catch (Exception e2) {
            gotoSetting();
        }

    }

    /**
     * 设置页面
     */
    public void gotoSetting() {
        Intent intent;
        intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 未知安装包
     */
    public void gotoStartInstallPermissionSettingActivity() {

        Intent intent = new Intent();
        //获取当前apk包URI，并设置到intent中（这一步设置，可让“未知应用权限设置界面”只显示当前应用的设置项）
        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
        intent.setData(packageURI);
        //设置不同版本跳转未知应用的动作
        if (Build.VERSION.SDK_INT >= 26) {
            //intent = new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
            intent.setAction(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        } else {
            intent.setAction(android.provider.Settings.ACTION_SECURITY_SETTINGS);
        }
        activity.startActivityForResult(intent,  ConstantConfig.REQUEST_PERMISSION_UNKNOW_APP);
    }


    /**
     * 悬浮窗
     */
    public void gotoSuspendedWindow() {

        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            activity.startActivityForResult(intent,  ConstantConfig.REQUEST_PERMISSION_OVERLAY);
        } catch (Exception e) {
            gotoAPPDetails();
        }
    }


    /**
     * goto针对N以上的Doze模式
     */
    public void gotoIgnoreBatteryOption() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent();
                String packageName = activity.getPackageName();
                PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                    //intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    activity.startActivityForResult(intent,  ConstantConfig.REQUEST_PERMISSION_IGNORE_BATTERY_CODE);
                }
            } catch (Exception e) {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = ComponentName.unflattenFromString("com.android.settings/.Settings$HighPowerApplicationsActivity");
                    intent.setComponent(cn);
                    activity.startActivity(intent);
                } catch (Exception e1) {
                    gotoHousekeeper();
                }
            }
        } else {
            ToastUtil.showCenter("版本太低无需进入该模式");
        }
    }

    /**
     * 后台高耗电保护
     */
    public void gotoHighConsumption() {
//        try {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName cn = ComponentName.unflattenFromString("com.android.settings/.Settings$HighPowerApplicationsActivity");
//            intent.setComponent(cn);
//            activity.startActivity(intent);
//        } catch (Exception e) {
        gotoHousekeeper();
//        }
        if (!gotoHousekeeper()){
            gotoAPPDetails();
        }
    }

    /**
     * 进入手机管家
     * @return
     */
    public boolean gotoHousekeeper() {
        Set<Map.Entry<String, List<String>>> entries = hashMap.entrySet();
        boolean b=true;
        for (Map.Entry<String, List<String>> entry : entries) {
            String manufacturer = entry.getKey();
            List<String> actCompatList = entry.getValue();
            if (Build.MANUFACTURER.equalsIgnoreCase(manufacturer)) {
                for (String act : actCompatList) {
                    try {
                        Intent intent;
                        //找不到? 网上的做法都是跳转到设置... 这基本上是没意义的 基本上自启动这个功能是第三方厂商自己写的安全管家类app
                        //所以我是直接跳转到对应的安全管家/安全中心
                        intent = activity.getPackageManager().getLaunchIntentForPackage(act);
                        activity.startActivity(intent);
                        b= true;
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        b= false;
                    }
                }
            }else {
                b=false;
            }
        }
        return b;
    }

    /**
     * 唤醒权限，后台弹出界面、锁屏显示。暂时没方法.
     */
    public void gotoBackgroundStart() {

    }

    /**
     * 修改系统权限
     */
    public void gotoModifySys(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //大于等于23 请求权限
            if ( !Settings.System.canWrite(activity.getApplicationContext())) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    activity.startActivityForResult(intent,  ConstantConfig.REQUEST_PERMISSION_CODE_WRITE_SETTINGS);
                }catch (Exception e){
                    gotoAPPDetails();
                }
            }
        }
    }

    /**
     * 免打扰权限
     *
     * @return
     */
    public void gotoNotificationPolicyAccessGranted() {

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            try {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                activity.startActivityForResult(intent,  ConstantConfig.REQUEST_PERMISSION_NOTIFICATION_POLICY_ACCESS);
            }catch (Exception e){
                gotoAPPDetails();
            }
        }
    }
}
