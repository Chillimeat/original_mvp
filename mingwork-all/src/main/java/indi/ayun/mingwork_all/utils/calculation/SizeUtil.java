package indi.ayun.mingwork_all.utils.calculation;

import android.os.Environment;
import android.os.StatFs;

import indi.ayun.mingwork_all.utils.storage.FileUtils;

import java.io.File;
import java.math.BigDecimal;

import static indi.ayun.mingwork_all.config.FilePathConfig.getEnvDataPath;


/**
 * Created by ayun on 2018/1/28.
 */

public class SizeUtil {
    /**
     *通过size计算大小
     * @param size
     * @return 大小
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
    /**获取文件size
     * @param filepath
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File filepath) throws Exception {
        long size = 0;
        try {
            File[] fileList = filepath.listFiles();
            int size2 = 0;
            if (fileList != null) {
                size2 = fileList.length;
                for (int i = 0; i < size2; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    /**
     * 获取SD卡剩余容量（单位Byte）
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDFreeSize() {
        if (FileUtils.isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();

            // 返回SD卡空闲大小
            return freeBlocks * blockSize; // 单位Byte
        } else {
            return 0;
        }
    }
    /**
     * 获取SD卡总容量（单位Byte）
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long gainSDAllSize() {
        if (FileUtils.isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小（Byte）
            return allBlocks * blockSize;
        } else {
            return 0;
        }
    }
    /**
     * 获取可用手机内容容量大小
     *
     * @return long
     */
    @SuppressWarnings("deprecation")
    public static long getMobileEnableRAM() {
        StatFs stat = new StatFs(getEnvDataPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks() - 4;
        ;
        return availableBlocks * blockSize;
    }


    /**
     * 获取手机内内存总容量大小
     *
     * @return long
     */
    @SuppressWarnings("deprecation")
    public static long getMobileAllRAM() {
        StatFs stat = new StatFs(getEnvDataPath());
        return stat.getBlockSize() * stat.getBlockCount();
    }

    /**
     * 获取大小的描述.
     *
     * @param size
     *            字节个数
     * @return 大小的描述
     */
    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024) {
            suffix = "K";
            size = size >> 10;
            if (size >= 1024) {
                suffix = "M";
                // size /= 1024;
                size = size >> 10;
                if (size >= 1024) {
                    suffix = "G";
                    size = size >> 10;
                    // size /= 1024;
                }
            }
        }
        return size + suffix;
    }
}
