package indi.ayun.mingwork_all.utils.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import indi.ayun.mingwork_all.MingWork_All;
import indi.ayun.mingwork_all.mlog.MLog;


/**
 * 主要功能:获取App应用版本信息
 */
@SuppressWarnings("rawtypes")
public class App {

    /**
     * 获取本地apk的名称
     * @param context 上下文
     * @return String
     */
    public static String getAppName(Context context) {
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
            return "unKnown";
        }
    }

    /**
     * 获取本地Apk版本名称
     * @return String
     */
    public static String getVersionName() {
        String verName = "";
        try {
            verName = MingWork_All.getContext().getPackageManager().getPackageInfo(MingWork_All.getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //MLog.e("ApplicationUtil-->>getVerName()", e.getMessage() + "获取本地Apk版本名称失败！");
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取本地Apk版本号
     * @return int
     */
    public static int getVersionCode() {
        int verCode = -1;
        try {
            verCode = MingWork_All.getContext().getPackageManager().getPackageInfo(MingWork_All.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //MLog.e("ApplicationUtil-->>getVerCode()", e.getMessage() + "获取本地Apk版本号失败！");
            e.printStackTrace();
        }
        return verCode;
    }
    /**
     * 获得包名
     * @return 包名
     */
    public static String getPackageName() {
        try {
            return MingWork_All.getContext().getPackageManager().getPackageInfo(MingWork_All.getContext().getPackageName(), 0).packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取application层级的metadata
     *
     * @param context 上下文
     * @param key     key
     * @return value
     */
    public static String getApplicationMetaData(Context context, String key) {
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            return metaData.get(key).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得应用申明的所有权限列表
     * @param context 上下文
     * @return 获得应用申明的所有权限列表
     */
    public static List<String> getAppPermissions(Context context){
        List<String> permissions=new ArrayList<String>();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            permissions.addAll(Arrays.asList(packageInfo.requestedPermissions));

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return permissions;
    }
    /**
     * 获取当前APP应用的SampleSize大小
     * @param options BitmapFactory.Options对象
     * @param minSideLength  计算最小值
     * @param maxNumOfPixels 计算最大值
     * @return Integer  返回SampleSize大小
     */
    public static Integer getSysSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        Integer initialSize = calculateSysInitialSampleSize(options, minSideLength, maxNumOfPixels);
        Integer roundedSize;
        if (initialSize <= 8 ) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        }else{
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        //MLog.i("SysInfoUtils-->>getSysSampleSize",  roundedSize + "");
        return roundedSize;
    }


    /**
     * 计算公式
     * @param options BitmapFactory.Options对象
     * @param minSideLength  计算最小值
     * @param maxNumOfPixels 计算最大值
     * @return Integer
     */
    private static Integer calculateSysInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        Integer lowerBound = (maxNumOfPixels == -1) ? 1 :  (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        Integer upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    /**
     * 需要打包安装后才能获取
     * @param page
     */
    public static void getMD5(String page) {
        try {   //BuildConfig.APPLICATION_ID   当前应用包名
            PackageInfo packageInfo = MingWork_All.getContext().getPackageManager().getPackageInfo(page,
                    PackageManager.GET_SIGNATURES);
            String signValidString = getSignValidString(packageInfo.signatures[0].toByteArray());
            MLog.e("获取应用签名", page + ":" + signValidString);
        } catch (Exception e) {
            MLog.e("获取应用签名", "异常:" + e);
        }
    }
    private static String getSignValidString(byte[] paramArrayOfByte) throws NoSuchAlgorithmException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramArrayOfByte);
        return toHexString(localMessageDigest.digest());
    }

    private static String toHexString(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfByte.length) {
                return localStringBuilder.toString();
            }
            String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
            if (str.length() == 1) {
                str = "0" + str;
            }
            localStringBuilder.append(str);
        }
    }
}
