

package indi.ayun.mingwork_all.media_box.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import java.io.File;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ayun.mingwork_all.R;

/**
 * A file helper to make thing easier.
 *
 * @author ChenSL
 */
public class BoxingFileHelper {
    public static final String DEFAULT_SUB_DIR = File.separator+ R.string.app_name+ File.separator+"boxing";

    public static boolean createFile(String path) throws ExecutionException, InterruptedException {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        final File file = new File(path);
        return file.exists() || file.mkdirs();

    }

    @Nullable
    public static String getCacheDir(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        context = context.getApplicationContext();
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String result = cacheDir.getAbsolutePath() + "/boxing";
        try {
            BoxingFileHelper.createFile(result);
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
        return result;
    }

    @Nullable
    public static String getBoxingPathInDCIM() {
        return getExternalDCIM(null);
    }

    @Nullable
    public static String getExternalDCIM(String subDir) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (file == null) {
                return null;
            }
            String dir = DEFAULT_SUB_DIR;
            if (!TextUtils.isEmpty(subDir)) {
                dir = subDir;
            }
            String result = file.getAbsolutePath() + dir;
            return result;
        }
        return null;
    }

    public static boolean isFileValid(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return isFileValid(file);
    }

    static boolean isFileValid(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0 && file.canRead();
    }
}
