package indi.ayun.mingwork_all.config;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;


import com.ayun.mingwork_all.R;

import java.io.File;

import indi.ayun.mingwork_all.MingWork_All;

import static android.os.Environment.DIRECTORY_ALARMS;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_MOVIES;
import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_NOTIFICATIONS;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.DIRECTORY_PODCASTS;
import static android.os.Environment.DIRECTORY_RINGTONES;
import static indi.ayun.mingwork_all.utils.storage.FileUtils.isMountedSDCard;
import static indi.ayun.mingwork_all.utils.storage.FileUtils.isRootPath;

/**
 * Created by ayun on 2018/6/6.
 */

public class FilePathConfig {
    //安卓路径大致分为两种：第一种是独立路径（公共路径）；第二种是专属路径（私人路径）；
    //第一种：通过Environment获得。
    //第二种：通过Context获得，带包名的路径。

    //第一种：首先分为四大类
    //File f1 = Environment.getDataDirectory();
    //File f2 = Environment.getDownloadCacheDirectory();
    //File f3 = Environment.getRootDirectory();
    //File f4 = Environment.getExternalStorageDirectory();
    //f1.getPath()； /data
    //f2.getPath()； /cache
    //f3.getPath()； /system
    //f4.getPath()； /mnt/sdcard
    //我们常用的是第四小类，获得sd卡路径，基于第四小类Google提供了九大公共路径，如下：
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_ALARMS)	/storage/sdcard0/Alarms警报
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM)	/storage/sdcard0/DCIM相机
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)	/storage/sdcard0/Download下载
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_MOVIES)	/storage/sdcard0/Movies视频
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC)	/storage/sdcard0/Music音乐
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_NOTIFICATIONS)	/storage/sdcard0/Notifications通知
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)	/storage/sdcard0/Pictures图片
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_PODCASTS)	/storage/sdcard0/Podcasts播客
    //Environment.getExternalStoragePublicDirectory(DIRECTORY_RINGTONES)	/storage/sdcard0/Ringtones铃声

    //第二种：常用的大致有两种
    //第一种：在SD卡中有一个app的专属文件；第二种：在手机内存中（data路径）有个带包名的文件。
    //第一种：当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。
    //context.getExternalFilesDir:SDCard/Android/data/你的应用的包名/files/ 目录.一般放一些长时间保存的数据
    //context.getExternalCacheDir:SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    //第二种：当你用户卸载时，会留下来作为垃圾文件。
    //context.getCacheDir()方法用于获取/data/data/包名/cache目录
    //context.getFilesDir()方法用于获取/data/data/包名/files目录

    //另外：
    //文件路径.getAbsolutePath()：获取该文件的绝对路径。
    //文件路径.getPath()：获取该文件的相对路径。


    private final static String TAG = "FilePathConfig";

    /**
     * 获取SDCard的绝对路径
     *
     * @return String
     */
    public static String getEnvAbsolutePath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
                //MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        }
        return "";
    }


    /**
     * 获取SdCard的相对路径
     *
     * @return String
     */
    public static String getEnvPath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
                //MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getExternalStorageDirectory().getPath() + File.separator;
        }
        return "";
    }


    /**
     * 获取系统绝对存储路径
     *
     * @return String
     */
    public static String getEnvRootAbsolutePath() {
        if (isRootPath()) {
            File sdcardDir = Environment.getRootDirectory();
            if (!sdcardDir.canWrite()) {
                // MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getRootDirectory().getAbsolutePath() + File.separator;
        }
        return "";

    }


    /**
     * 获取系统相对存储路径
     *
     * @return String
     */
    public static String getEnvRootPath() {
        if (isRootPath()) {
            File sdcardDir = Environment.getRootDirectory();
            if (!sdcardDir.canWrite()) {
                //    MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getRootDirectory().getPath() + File.separator;
        }
        return "";

    }


    /**
     * 获取手机内存Path存储路径
     *
     * @return String
     */
    public static String getEnvDataPath() {
        if (isRootPath()) {
            File sdcardDir = Environment.getDataDirectory();
            if (!sdcardDir.canWrite()) {
                // MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getDataDirectory().getPath() + File.separator;
        }
        return "";
    }


    /**
     * 获取手机内存AbsolutePath存储路径
     *
     * @return String
     */
    public static String getEnvDataAbsolutePath() {
        if (isRootPath()) {
            File sdcardDir = Environment.getDataDirectory();
            if (!sdcardDir.canWrite()) {
                // MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getDataDirectory().getAbsolutePath() + File.separator;
        }
        return "";
    }

    /**
     * 获取手机缓存Path存储路径
     *
     * @return String
     */
    public static String getEnvCachePath() {
        if (isRootPath()) {
            File sdcardDir = Environment.getDownloadCacheDirectory();
            if (!sdcardDir.canWrite()) {
                // MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getDownloadCacheDirectory().getPath() + File.separator;
        }
        return "";
    }


    /**
     * 获取手机缓存AbsolutePath存储路径
     *
     * @return String
     */
    public static String getEnvCacheAbsolutePath() {
        if (isRootPath()) {
            File sdcardDir = Environment.getDownloadCacheDirectory();
            if (!sdcardDir.canWrite()) {
                // MLog.w(TAG, "SDCARD can not write !");
            }
            return Environment.getDownloadCacheDirectory().getAbsolutePath() + File.separator;
        }
        return "";
    }


    //bug库文件
    public static String BUGFILE__NAME = "crash";
    public static String BUGFILE__SUFFIX = ".trace";
    //bug库文件存储路径 获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/crash
    public static String BUGFILE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "crash"
            + File.separator;

    /**
     * 九大公共路径
     */
    public static String EnvAlarms = Environment.getExternalStoragePublicDirectory(DIRECTORY_ALARMS).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Alarms警报
    public static String EnvDCIM = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getAbsolutePath() + File.separator;    //  /storage/sdcard0/DCIM相机
    public static String EnvDownload = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Download下载
    public static String EnvMovies = Environment.getExternalStoragePublicDirectory(DIRECTORY_MOVIES).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Movies视频
    public static String EnvMusic = Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Music音乐
    public static String EnvNotifications = Environment.getExternalStoragePublicDirectory(DIRECTORY_NOTIFICATIONS).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Notifications通知
    public static String EnvPictures = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Pictures图片
    public static String EnvPodcasts = Environment.getExternalStoragePublicDirectory(DIRECTORY_PODCASTS).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Podcasts播客
    public static String EnvRingtones = Environment.getExternalStoragePublicDirectory(DIRECTORY_RINGTONES).getAbsolutePath() + File.separator;    //  /storage/sdcard0/Ringtones铃声


    /*文件存储路径
     *app独立文件
     */
    //独立文件的根目录
    public static String ENV_ROOT = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator;

    // 默认存放图片的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/img
    public static String ENV_SAVE_IMAGE = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "img" + File.separator;

    // 默认存放视频的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/video
    public static String ENV_SAVE_VIDEO = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "video" + File.separator;

    // 默认存放广告的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/adv
    public static String ENV_SAVE_ADV = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "adv" + File.separator;

    // 默认存放音乐的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/music
    public static String ENV_SAVE_MUSIC = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "music" + File.separator;


    // 默认存放文件下载的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/download
    public static String ENV_DOWNLOAD = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "download" + File.separator;

    //下载默认存放广告页面的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/download/adv
    public static String ENV_DOWNLOAD_ADV = ENV_DOWNLOAD + "adv" + File
            .separator;

    //下载默认视频存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/download/video
    public static String ENV_DOWNLOAD_VIDEO = ENV_DOWNLOAD + "video" + File
            .separator;

    //下载默认视频存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/download/img
    public static String ENV_DOWNLOAD_IMG = ENV_DOWNLOAD + "img" + File
            .separator;

    //下载默认视频存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/download/music
    public static String ENV_DOWNLOAD_MUSIC = ENV_DOWNLOAD + "music" + File
            .separator;

    // 缓存的路径 获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/cache
    public static String ENV_CACHE = Environment.getExternalStorageDirectory()
            + File.separator
            + MingWork_All.getContext().getString(R.string.app_name)
            + File.separator + "cache" + File.separator;

    //缓存视频存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/cache/video
    public static String ENV_CACHE_VIDEO = ENV_CACHE + "video" + File
            .separator;

    //缓存图片存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/cache/img
    public static String ENV_CACHE_IMG = ENV_CACHE + "img" + File
            .separator;

    //缓存广告存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/cache/adv
    public static String ENV_CACHE_ADV = ENV_CACHE + "adv" + File
            .separator;

    //缓存广告存放的路径  获得sd卡路径（或者虚拟sd卡路径）/mnt/sdcard/newproject/cache/music
    public static String ENV_CACHE_MUSIC = ENV_CACHE + "music" + File
            .separator;


    /*文件存储路径
     *app专属文件
     */

    //当你用户卸载时，不会留下来作为垃圾文件。/Android/data/包名/cache目录
    public static String CON_CACHE = MingWork_All.getContext().getExternalCacheDir().getPath();

    //当你用户卸载时，不会留下来作为垃圾文件。/Android/data/包名/files目录
    public static String CON_FIle = MingWork_All.getContext().getExternalFilesDir(null).getPath();

    // 默认存放图片的路径  获得sd卡路径（或者虚拟sd卡路径）/Android/data/你的应用包名/img
    public static String CON_FIle_IMAGE = CON_FIle+ File.separator
            + "img" + File.separator;

    // 默认存放视频的路径  获得sd卡路径（或者虚拟sd卡路径）/Android/data/你的应用包名/video
    public static String CON_FIle_VODEO = CON_FIle+ File.separator
            + "video" + File.separator;

    // 默认存放文件下载的路径  获得sd卡路径（或者虚拟sd卡路径）/Android/data/你的应用包名/download
    public static String CON_FIle_DOWNLOAD =CON_FIle+ File.separator
            + "download" + File.separator;


    public static Uri getFileUri(Context context, File filePath) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, "com.fingergame.ayun.livingclock.fileProvider", filePath);
        } else {
            uri = Uri.fromFile(filePath);
        }
        return uri;
    }
}
