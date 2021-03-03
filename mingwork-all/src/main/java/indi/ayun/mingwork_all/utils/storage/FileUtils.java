package indi.ayun.mingwork_all.utils.storage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import indi.ayun.mingwork_all.base.UtilBase;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

/**
 * @Description: 主要功能:文件操作
 */

public class FileUtils extends UtilBase {
    //是否剪切
    public static boolean CUT_FALG = false;

    //是否复制
    public static boolean COPY_FALG = false;
    private FileUtils() {
        throw new UnsupportedOperationException("cann't be instantiated");
    }
    private static final String TAG = FileUtils.class.getSimpleName();
    /**
     * 判断文件是否存在
     * @param filePath 文件路径
     * @return 是否存在
     * @throws Exception
     */
    public static Boolean isExsit(String filePath) {
        Boolean flag = false;
        if (!IsNothing.onAnything(filePath)){
            return flag;
        }
        try {
            File file = new File(filePath);
            if (file.exists()) {
                flag = true;
            }
        } catch (Exception e) {
            //MLog.e("判断文件失败-->" + e.getMessage());
        }

        return flag;
    }
    /**
     * 检查文件是否存在（有时间戳）
     *
     * @param path      路径
     * @param timestamp 时间戳
     * @return boolean  返回状态
     */
    public static boolean isExsit(String path, String timestamp) {
        if (!IsNothing.onAnything(path)){
            return false;
        }
        if (timestamp == null) {
            if (new File(path).exists()) {
                return true;
            }
        } else {
            File file = new File(path);
            Date fileTime = new Date(file.lastModified());
            long fileTimestamp = fileTime.getTime();
            long newTimestamp = Long.valueOf(timestamp) * 1000;
            long error = Long.valueOf(60000000);
            if (new File(path).exists() && fileTimestamp - error >= newTimestamp) {
                return true;
            }
        }
        return false;
    }
    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static int delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
//            Toast.makeText(HnUiUtils.getContext(), "删除文件失败:" + delFile + "不存在！", Toast.LENGTH_SHORT).show();
            MLog.e("删除文件失败:" + delFile + "不存在！");
            return 2;
        } else {
            if (file.isFile())
                if (deleteSingleFile(delFile)){
                    return 1;
                }else {
                    return 0;
                }
            else
                if (deleteDirectory(delFile)){
                    return 1;
                }else {
                    return 0;
                }
        }
    }

    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                MLog.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                MLog.e("删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            MLog.e("删除单个文件失败：" + filePath$Name + "不存在！");
            return false;
        }
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            MLog.e("删除目录失败：" + filePath + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            MLog.e("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            MLog.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
            MLog.e("删除目录：" + filePath + "失败！");
            return false;
        }
    }
    /**
     * 获取文件夹名称
     * @param filePath
     * @return
     */
    public static String getFolderAllName(String filePath) {
        if (filePath == null || filePath.length() == 0 || filePath.trim().length() == 0) {
            return filePath;
        }
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? "" : filePath.substring(filePos+1, filePath.length());
    }

    /**
     * 获取文件夹名称
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {
        int start=filePath.lastIndexOf("/");
        int end=filePath.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return filePath.substring(start+1,end);
        }else{
            return null;
        }
    }
    /**
     * 创建目录
     * @param context
     * @param dirName 文件夹名称
     * @return
     */
    public static File onCreateFileDir(Context context, String dirName) {
        String filePath;
        // 如SD卡已存在，则存储；反之存在data目录下
        if (isMountedSDCard()) {
            // SD卡路径
            filePath = Environment.getExternalStorageDirectory() + File.separator + dirName;
        } else {
            filePath = context.getCacheDir().getPath() + File.separator + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            boolean isCreate = destDir.mkdirs();
            //MLog.i("FileUtils", filePath + " has created. " + isCreate);
        }
        return destDir;
    }
    /**
     * 创建文件夹(支持覆盖已存在的同名文件夹)
     * @param dirName
     * @param recreate
     * @return
     */
    public static boolean onCreateFolder(String dirName, boolean recreate) {
        if (dirName == null || dirName.length() == 0 || dirName.trim().length() == 0) {
            return false;
        }
        File folder = new File(dirName);
        if (folder.exists()) {
            if (recreate) {
                new File(dirName).delete();
                return folder.mkdirs();
            } else {
                return true;
            }
        } else {
            return folder.mkdirs();
        }
    }
    /**
     * 重命名文件\文件夹
     * @param filepath
     * @param newName
     * @return
     */
    public static boolean rename(String filepath, String newName) {
        File file = new File(filepath);
        return file.exists() && file.renameTo(new File(newName));
    }
    /**
     * 获取文件夹下所有文件
     * @param path
     * @return
     */
    public static ArrayList<File> getFilesArray(String path) {
        File file = new File(path);
        File files[] = file.listFiles();
        ArrayList<File> listFile = new ArrayList<File>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    listFile.add(files[i]);
                }
                if (files[i].isDirectory()) {
                    listFile.addAll(getFilesArray(files[i].toString()));
                }
            }
        }
        return listFile;
    }
    /**
     * 打开图片
     * @param mContext
     * @param imagePath
     */
    public static void openImage(Context mContext, String imagePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(imagePath));
        intent.setDataAndType(uri, "image/*");
        mContext.startActivity(intent);
    }
    /**
     * 打开视频
     * @param mContext
     * @param videoPath
     */
    public static void openVideo(Context mContext, String videoPath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(videoPath));
        intent.setDataAndType(uri, "video/*");
        mContext.startActivity(intent);
    }
    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            //MLog.w(TAG, "SDCARD is not MOUNTED !");
            return false;
        }
    }

    /**
     * 检查系统存储路径
     *
     * @return
     */
    public static boolean isRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getRootDirectory())) {
            return true;
        } else {
            //MLog.w(TAG, "RootPath is not MOUNTED !");
            return false;
        }
    }

    /**
     * 检查手机内存存储路径
     * @return
     */
    public static boolean isDataPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getDataDirectory())) {
            return true;
        } else {
            //MLog.w(TAG, "DataPath is not MOUNTED !");
            return false;
        }
    }


    /**
     * 获取文件大小
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {

        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件夹不存在");
        }

        return s;
    }
    /**
     * 递归获取文件大小
     * */
    public static long getFileSize(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    /**
     * 转换文件大小
     * */
    public static String FormentFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /***
     * 获取文件的创建日期
     * @param file
     * @param formatType
     * @return
     */
    public static String getFileLastModifiedTime(File file,String formatType) {
        Calendar cal = Calendar.getInstance();
        long time = file.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        cal.setTimeInMillis(time);

        // 输出：修改时间[2] 2009-08-17 10:32:38
        return formatter.format(cal.getTime());
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间
    }
}
