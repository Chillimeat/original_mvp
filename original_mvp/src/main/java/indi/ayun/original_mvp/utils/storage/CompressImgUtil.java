package indi.ayun.original_mvp.utils.storage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import indi.ayun.original_mvp.mlog.MLog;

public class CompressImgUtil {

    public static CompressImgUtil getInstance(){
        return new CompressImgUtil();
    }

    /*
     * 图片的质量压缩 降低图片质量
     * 原理 ：通过算法扣掉（同化）了 图片中的一些某个点附近相近的像素，达到降低质量 减少 文件大小的目的
     * 注意 ： 他其实只能 实现对 file 的影响，对加载这个图片出来的bitmap 内存是无法节省的 ，还是那么大
     *  因为 bitmap 在内存中的大小是按照 像素 计算的 ，也就是width * height ，对于质量压缩，并不会改变图片的真实的像素（像素大小不会变）。
     *  使用场景 ：将图片保存到本地 ，或者将图片上传 到服务器 ，根据实际需求来 。
     * @param bmp  bitmap 输出
     * @param file  文件   输出
     * */
    public Boolean compressImage2File(Bitmap bmp , File file)
    {
        boolean state=false;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,50,bos);
        try {
            FileOutputStream fos = new FileOutputStream(file) ;
            fos.write(bos.toByteArray());
            MLog.d("质量压缩成功");
            state=true;
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * 2.尺寸压缩
     * 原理 ： 通过减少单位尺寸的像素值，真正意义上的降低像素值。
     * 使用场景 ： 缓存缩略图 (头像的处理)
     * @param bmp  bitmap 输出
     * @param file  文件   输出
     */
    public Boolean compressImage2FileBySize(Bitmap bmp , File file)
    {
        boolean state=false;
        //压缩尺寸倍数 值越大 ，图片的尺寸就越小
        int ratio = 4 ;
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() /ratio , bmp.getHeight() / ratio ,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result) ;
        Rect rect = new Rect(0,0,bmp.getWidth() / ratio ,bmp.getHeight() / ratio );
        canvas.drawBitmap(bmp,null,rect,null);

        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        result.compress(Bitmap.CompressFormat.JPEG,100,bos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            MLog.d("尺寸压缩成功");
            state=true;
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
}
