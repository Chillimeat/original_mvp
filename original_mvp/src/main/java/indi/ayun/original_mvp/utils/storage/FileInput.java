package indi.ayun.original_mvp.utils.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;

import indi.ayun.original_mvp.mlog.MLog;

import static android.content.Context.MODE_PRIVATE;

public class FileInput {
    private FileInput() {
        throw new UnsupportedOperationException("cann't be instantiated");
    }
    private static final String TAG = FileInput.class.getSimpleName();
    /**
     * 写入应用程序包files目录下文件 string
     * @param context 上下文
     * @param fileName 文件名称
     * @param data 文件内容
     */
    public static void writeThis(Context context, String fileName, String data,int mode) {
        FileOutputStream outputStream;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = context.openFileOutput(fileName,mode );
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null)
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 写入应用程序包files目录下文件 byte
     * @param context 上下文
     * @param fileName 文件名称
     * @param content 文件内容
     */
    public static void writeThis(Context context, String fileName, byte[] content) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName, MODE_PRIVATE);
            outStream.write(content);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件 byte 指定写入模式
     * @param context 上下文
     * @param fileName 文件名称
     * @param modeType 文件写入模式（Context.MODE_PRIVATE、Context.MODE_APPEND、Context.
     *            MODE_WORLD_READABLE、Context.MODE_WORLD_WRITEABLE）
     * @param content 文件内容
     */
    public static void writeThis(Context context, String fileName, byte[] content, int modeType) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName, modeType);
            outStream.write(content);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存Bitmap到指定目录
     * @param dir      目录
     * @param fileName 文件名
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(File dir, String fileName, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File file = new File(dir, fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 指定编码保存内容到指定路径
     * @param filePath 文件路径
     * @param content 保存的内容
     * @param encoding 写文件编码  System.getProperty("file.encoding")
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content, String encoding) throws IOException {
        BufferedWriter writer = null;
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, false), encoding));
            writer.write(content);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 追加文本
     * @param content 需要追加的内容
     * @param file 待追加文件源
     * @param encoding  文件编码  System.getProperty("file.encoding")
     * @throws IOException
     */
    public static void appendToFile(String content, File file, String encoding) throws IOException {
        BufferedWriter writer = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true), encoding));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 写入SharedPreferences文件内容
     * @param context 上下文
     * @param fileNameNoExt 文件名称（不用带后缀名）
     * @param values 需要写入的数据Map(String,Boolean,Float,Long,Integer)
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void writeShrePerface(Context context, String fileNameNoExt, Map<String, ?> values) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(
                    fileNameNoExt, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            for (Iterator iterator = values.entrySet().iterator(); iterator
                    .hasNext();) {
                Map.Entry<String, ?> entry = (Map.Entry<String, ?>) iterator
                        .next();
                if (entry.getValue() instanceof String) {
                    editor.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Boolean) {
                    editor.putBoolean(entry.getKey(),
                            (Boolean) entry.getValue());
                } else if (entry.getValue() instanceof Float) {
                    editor.putFloat(entry.getKey(), (Float) entry.getValue());
                } else if (entry.getValue() instanceof Long) {
                    editor.putLong(entry.getKey(), (Long) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    editor.putInt(entry.getKey(), (Integer) entry.getValue());
                }
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 字节流对象写入指定文件
     *
     * @param inputStream 下载文件的字节流对象
     * @param filePath 文件的存放路径
     *            (带文件名称)
     * @throws IOException
     */
    public static File writeToFile(InputStream inputStream, String filePath) throws IOException {
        OutputStream outputStream = null;
        // 在指定目录创建一个空文件并获取文件对象
        File mFile = new File(filePath);
        if (!mFile.getParentFile().exists())
            mFile.getParentFile().mkdirs();
        try {
            outputStream = new FileOutputStream(mFile);
            byte buffer[] = new byte[4 * 1024];
            int lenght = 0;
            while ((lenght = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, lenght);
            }
            outputStream.flush();
            return mFile;
        } catch (IOException e) {
            MLog.e(TAG, "写入文件失败，原因：" + e.getMessage());
            throw e;
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }

            } catch (IOException e) {
            }
        }
    }


}
