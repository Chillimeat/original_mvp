package indi.ayun.original_mvp.ui;

import android.Manifest;
import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import indi.ayun.original_mvp.config.ConstantConfig;
import indi.ayun.original_mvp.config.FilePathConfig;
import indi.ayun.original_mvp.permission.MPermission;
import indi.ayun.original_mvp.utils.storage.FileUtils;

public class SplashHelper {
    private static final String TAG= SplashHelper.class.getSimpleName();

    private Activity mContext;

    public SplashHelper(Activity context){
        this.mContext=context;
    }

    /**
     * 建立文件夹
     */
    public void setAppFile() {

        if (!FileUtils.isExsit(FilePathConfig.ENV_SAVE_IMAGE)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_SAVE_IMAGE, true);
            setNomeida(FilePathConfig.ENV_SAVE_IMAGE);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_SAVE_VIDEO)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_SAVE_VIDEO, true);
            setNomeida(FilePathConfig.ENV_SAVE_VIDEO);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_SAVE_ADV)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_SAVE_ADV, true);
            setNomeida(FilePathConfig.ENV_SAVE_ADV);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_SAVE_MUSIC)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_SAVE_MUSIC, true);
            setNomeida(FilePathConfig.ENV_SAVE_MUSIC);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_DOWNLOAD)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_DOWNLOAD, true);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_DOWNLOAD_ADV)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_DOWNLOAD_ADV, true);
            setNomeida(FilePathConfig.ENV_DOWNLOAD_ADV);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_DOWNLOAD_VIDEO)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_DOWNLOAD_VIDEO, true);
            setNomeida(FilePathConfig.ENV_DOWNLOAD_VIDEO);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_DOWNLOAD_IMG)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_DOWNLOAD_IMG, true);
            setNomeida(FilePathConfig.ENV_DOWNLOAD_IMG);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_DOWNLOAD_MUSIC)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_DOWNLOAD_MUSIC, true);
            setNomeida(FilePathConfig.ENV_DOWNLOAD_MUSIC);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_CACHE)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_CACHE, true);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_CACHE_VIDEO)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_CACHE_VIDEO, true);
            setNomeida(FilePathConfig.ENV_CACHE_VIDEO);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_CACHE_IMG)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_CACHE_IMG, true);
            setNomeida(FilePathConfig.ENV_CACHE_IMG);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_CACHE_ADV)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_CACHE_ADV, true);
            setNomeida(FilePathConfig.ENV_CACHE_ADV);
        }
        if (!FileUtils.isExsit(FilePathConfig.ENV_CACHE_MUSIC)) {
            FileUtils.onCreateFolder(FilePathConfig.ENV_CACHE_MUSIC, true);
            setNomeida(FilePathConfig.ENV_CACHE_MUSIC);
        }
    }

    private void setNomeida(String path) {
        File nomedia = new File(path, ".nomedia");
        try {
            if (!nomedia.exists())
                nomedia.createNewFile();
            FileOutputStream nomediaFos = new FileOutputStream(nomedia);
            nomediaFos.flush();
            nomediaFos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 基础得权限
     */
    public void firstBasePermission() {
        MPermission.with(mContext)
                .setRequestCode(ConstantConfig.PERMISSION_SPLASH)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                        //Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        //Manifest.permission.READ_PHONE_STATE//
                        // ……
                )
                .request();
    }

    /**
     * 需要实现得自定义文件排除相册检测方法
     * @param paths
     */
    public void setCustomAppFile(List<String> paths){
        for (int i=0;i<paths.size();i++){
            if (!FileUtils.isExsit(paths.get(i))) {
                FileUtils.onCreateFolder(paths.get(i), true);
                setNomeida(paths.get(i));
            }
        }
    }
}
