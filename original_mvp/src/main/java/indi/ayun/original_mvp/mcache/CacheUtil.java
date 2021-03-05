package indi.ayun.original_mvp.mcache;

import android.content.Context;
import android.os.Environment;

import indi.ayun.original_mvp.config.FilePathConfig;
import indi.ayun.original_mvp.utils.calculation.SizeUtil;
import indi.ayun.original_mvp.utils.storage.FileUtils;

import java.io.File;

/**
 * Created by ayun on 2018/1/28.
 */

public class CacheUtil {

    /**获取当前缓存量
     * @param context
     * @return String
     * @throws Exception
     * context.getCacheDir()  /data/data/<application package>/cache
     * context.getExternalCacheDir() SDCard/Android/data/你的应用包名/cache/目录
     */
    public static String getAppClearSize(Context context) {
        long clearSize = 0;
        String fileSizeStr = "";
        //获得应用内部缓存大小
        try {
            //获得应用SharedPreference缓存数据大小
            clearSize += SizeUtil.getFolderSize(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
            clearSize +=SizeUtil.getFolderSize(new File(FilePathConfig.CON_CACHE));
            clearSize +=SizeUtil.getFolderSize(new File(FilePathConfig.CON_FIle));
            clearSize +=SizeUtil.getFolderSize(new File(FilePathConfig.CON_CACHE));
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                clearSize += SizeUtil.getFolderSize(new File(FilePathConfig.ENV_CACHE));
                clearSize += SizeUtil.getFolderSize(new File(FilePathConfig.getEnvCachePath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(clearSize > 5000)  {
            //转换缓存大小Byte为MB
            fileSizeStr = SizeUtil.getSizeDesc(clearSize);
        }

        return fileSizeStr;
    }

    /** 删除缓存
     * @param context
     */
   public static void clearCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.delete(FilePathConfig.getEnvCachePath());
        }

        FileUtils.delete(FilePathConfig.CON_CACHE);
        FileUtils.delete(FilePathConfig.CON_FIle);


        File fileIND=new File(FilePathConfig.ENV_CACHE);
        if(fileIND.exists()&&fileIND.isDirectory()){
            fileIND.delete();
        }
        File fileEXC=new File(FilePathConfig.CON_CACHE);
        if(fileEXC.exists()&&fileEXC.isDirectory()){
            fileEXC.delete();
        }
       File fileSP=new File("/data/data/" + context.getPackageName() + "/shared_prefs");
       if(fileSP.exists()&&fileSP.isDirectory()){
           fileSP.delete();
       }
    }

}
