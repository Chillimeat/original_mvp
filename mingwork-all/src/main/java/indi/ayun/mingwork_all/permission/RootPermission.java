package indi.ayun.mingwork_all.permission;

/**
 * @Description: 主要功能:判断手机是否具有root权限工具类
 */

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;

public class RootPermission {

    /**
     * 根据/system/bin/或/system/xbin目录下是否存在su文件判断是否已ROOT
     * @return true：已ROOT
     */
    public static boolean isRoot() {
        try {
            return new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得root权限
     * @param context
     * @return
     */
    public static boolean getRootPermission(Context context) {
        String packageCodePath = context.getPackageCodePath();
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + packageCodePath;
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
