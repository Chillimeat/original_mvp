package indi.ayun.mingwork_all.mlog.base;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import indi.ayun.mingwork_all.MingWork_All;
import indi.ayun.mingwork_all.preference.CredentialPreferences;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

public class FileLog {

    private static final String FILE_PREFIX = "MLog_";
    private static final String FILE_FORMAT = ".log";

    public static void printFile(String tag, File targetDirectory, @Nullable String fileName, String headString, String msg) {

        fileName = (fileName == null) ? getFileName() : fileName;
        String s="未登录用户";
        if (IsNothing.onAnything(CredentialPreferences.getUserAccount())) {
            s = "用户"+CredentialPreferences.getUserAccount();
        }
        s=s+"=>"+headString+"  "+msg+"  ("+(new Date(System.currentTimeMillis())).toString() +")\n";
        if (save(targetDirectory, fileName, s)) {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else {
            Log.e(tag, headString + "save log fails !");
        }
    }

    private static boolean save(File dic, @NonNull String fileName, String msg) {

        File file = new File(dic, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(file,true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static String getFileName() {
        Random random = new Random();
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT;
    }

}
