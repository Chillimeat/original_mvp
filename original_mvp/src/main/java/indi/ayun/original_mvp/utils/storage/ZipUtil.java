package indi.ayun.original_mvp.utils.storage;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * @Description: 主要功能: 实现的Zip工具
 */

public class ZipUtil{
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
    private static boolean stopZipFlag;
    public static boolean isStopZipFlag() {
        return stopZipFlag;
    }
    public static void setStopZipFlag(boolean stopZipFlag) {
        ZipUtil.stopZipFlag = stopZipFlag;
    }

    /**
     * 批量压缩文件（夹）,文件列表压缩，有监听
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile 生成的压缩文件
     * @param zipListener     zipListener
     */
    public static void zipFiles(Collection<File> resFileList, File zipFile,ZipListener zipListener)  {

        ZipOutputStream zipout = null;
        try {
            zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
            for (File resFile : resFileList) {
                if(stopZipFlag){
                    break;
                }
                zipFile(resFile, zipout, "",zipListener);
            }
            zipout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量压缩文件（夹）文件列表压缩，有监听有注释
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile 生成的压缩文件
     * @param comment 压缩文件的注释
     * @param zipListener    zipListener
     */
    public static void zipFiles(Collection<File> resFileList, File zipFile, String comment,ZipListener zipListener) {
        ZipOutputStream zipout = null;
        try {
            zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
            for (File resFile : resFileList) {
                zipFile(resFile, zipout, "",zipListener);
            }
            zipout.setComment(comment);
            zipout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 压缩文件
     *
     * @param resFile 需要压缩的文件（夹）
     * @param zipout 压缩的目的文件
     * @param rootpath 压缩的文件路径
     */
    private static void zipFile(File resFile, ZipOutputStream zipout, String rootpath,ZipListener zipListener) {
        try {
            rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator)
                    + resFile.getName();
            rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
            if (resFile.isDirectory()) {
                File[] fileList = resFile.listFiles();
                int length=fileList.length;
                // Log.e("zipprogress", (int)((1 / (float) (length+1))*100)+"%");
                zipListener.zipProgress((int)((1 / (float) (length+1))*100));
                for (int i=0;i<length;i++) {
                    if(stopZipFlag){
                        break;
                    }
                    File file=fileList[i];
                    zipFile(file, zipout, rootpath,zipListener);
                    // Log.e("zipprogress", (int)(((i+2) / (float) (length+1))*100)+"%");
                    zipListener.zipProgress((int)(((i+2) / (float) (length+1))*100));
                }
            } else {
                byte buffer[] = new byte[BUFF_SIZE];
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile),
                        BUFF_SIZE);
                zipout.putNextEntry(new ZipEntry(rootpath));
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    if(stopZipFlag){
                        break;
                    }
                    zipout.write(buffer, 0, realLength);
                }
                in.close();
                zipout.flush();
                zipout.closeEntry();
            }
        }catch (Exception e){

        }

    }


    /**
     * 解压缩一个文件
     *
     * @param zipFile 压缩文件
     * @param folderPath 解压缩的目标目录
     */
    public static void upZipFile(File zipFile, String folderPath) {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = null;
        try {
            zf = new ZipFile(zipFile);
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
                ZipEntry entry = ((ZipEntry)entries.nextElement());
                InputStream in = zf.getInputStream(entry);
                String str = folderPath + File.separator + entry.getName();
                str = new String(str.getBytes("8859_1"), "GB2312");
                File desFile = new File(str);
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                OutputStream out = new FileOutputStream(desFile);
                byte buffer[] = new byte[BUFF_SIZE];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解压文件名包含传入文字的文件
     *
     * @param zipFile 压缩文件
     * @param folderPath 目标文件夹
     * @param nameContains 传入的文件匹配名
     * @return   返回的集合
     */
    public static ArrayList<File> upZipSelectedFile(File zipFile, String folderPath, String nameContains) {

        ArrayList<File> fileList = new ArrayList<File>();

        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdir();
        }

        ZipFile zf = null;
        try {
            zf = new ZipFile(zipFile);
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
                ZipEntry entry = ((ZipEntry)entries.nextElement());
                if (entry.getName().contains(nameContains)) {
                    InputStream in = zf.getInputStream(entry);
                    String str = folderPath + File.separator + entry.getName();
                    str = new String(str.getBytes("8859_1"), "GB2312");
                    // str.getBytes("GB2312"),"8859_1" 输出
                    // str.getBytes("8859_1"),"GB2312" 输入
                    File desFile = new File(str);
                    if (!desFile.exists()) {
                        File fileParentDir = desFile.getParentFile();
                        if (!fileParentDir.exists()) {
                            fileParentDir.mkdirs();
                        }
                        desFile.createNewFile();
                    }
                    OutputStream out = new FileOutputStream(desFile);
                    byte buffer[] = new byte[BUFF_SIZE];
                    int realLength;
                    while ((realLength = in.read(buffer)) > 0) {
                        out.write(buffer, 0, realLength);
                    }
                    in.close();
                    out.close();
                    fileList.add(desFile);
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     */
    public static ArrayList<String> getEntriesNames(File zipFile) {

        ArrayList<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = null;
        try {
            entries = getEntriesEnumeration(zipFile);
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry)entries.nextElement());
                entryNames.add(new String(getEntryName(entry).getBytes("GB2312"), "8859_1"));
            }
            return entryNames;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     */
    public static Enumeration<?> getEntriesEnumeration(File zipFile) {
        ZipFile zf = null;
        try {
            zf = new ZipFile(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zf.entries();

    }

    /**
     * 取得压缩文件对象的注释
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的注释
     */
    public static String getEntryComment(ZipEntry entry)  {
        try {
            return new String(entry.getComment().getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     */
    public static String getEntryName(ZipEntry entry)  {
        try {
            return new String(entry.getName().getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 压缩byte[]
     * @param data
     * @return byte[] 压缩后的数据
     */
    public static byte[] zipByte(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 压缩Byte[]
     *
     * @param data 待压缩数据
     * @param os 输出流
     */
    public static void zipByte(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);
        try {
            dos.write(data, 0, data.length);
            dos.finish();
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩byte[]
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] upZipByte(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }

    /**
     * 解压缩byte[]
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] upZipByte(byte[] data,int offset,int len) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data,offset,len);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }

    /**
     * 解压缩byte[]
     *
     * @param is 输入流
     * @return byte[] 解压缩后的数据
     */
    public static byte[] upZipByte(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];
            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.toByteArray();
    }
    /**
     * 解压assets的zip压缩文件到指定目录
     *
     * @param context 上下文对象
     * @param assetName 压缩文件名
     * @param outputDirectory 输出目录
     * @param isReWrite 是否覆盖
     * @throws IOException
     */
    public static void unAssetsZip(Context context, String assetName, String outputDirectory, boolean isReWrite)
            throws IOException {
        File file = new File(outputDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 打开压缩文件
        InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        byte[] buffer = new byte[1024 * 1024];
        int count = 0;
        while (zipEntry != null) {
            // 如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者是文件不存在
                if (isReWrite || !file.exists()) {
                    file.mkdir();
                }
            } else {
                // 如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者文件不存在，则解压文件
                if (isReWrite || !file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    public interface ZipListener{
        void zipProgress(int zipProgress);
    }
}