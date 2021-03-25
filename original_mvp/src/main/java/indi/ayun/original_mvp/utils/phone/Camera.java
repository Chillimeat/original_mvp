package indi.ayun.original_mvp.utils.phone;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.utils.network.URIUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera {
    private static Camera camera;
    private Context context;
    public static Camera getInstance() {
        if (camera == null) {
            synchronized (Camera.class) {
                if (camera == null) {
                    camera = new Camera();
                }
            }
        }
        return camera;
    }
    private Camera(){

    }

    public void init(Context application){
        context=application;
    }

    /**
     * 单纯的跳转至拍照程序界面,不设置路径
     * @param mContext 上下文
     * @param requestCode 请求返回Result区分代码
     */
    public void toCameraActivity(Activity mContext, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mContext.startActivityForResult(intent, requestCode);
    }

    //requestCode=打开相册
    private final int REQUEST_OPEN_GALLERY = 0x022;
    /**
     * 打开相册
     */
    public void openGallery(Activity mContext) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mContext.startActivityForResult(intent, REQUEST_OPEN_GALLERY);
    }

    /**
     * 获得选中相册的图片
     *
     * @param mContext 上下文
     * @param data onActivityResult返回的Intent
     * @return
     */
    @SuppressWarnings({ "deprecation", "unused" })
    public Bitmap getChoosedImage(Activity mContext, Intent data) {
        if (data == null) {
            return null;
        }
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = mContext.getContentResolver();

        // 此处的用于判断接收的Activity是不是你想要的那个
        try {
            Uri originalUri = data.getData(); // 获得图片的uri
            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
            // 这里开始的第二部分，获取图片的路径：
            String[] proj = { MediaStore.Images.Media.DATA };
            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = mContext.managedQuery(originalUri, proj, null,
                null, null);
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            String path = cursor.getString(column_index);
            // 不用了关闭游标
            cursor.close();
        } catch (Exception e) {
            MLog.e("ToolPhone", e.getMessage());
        }

        return bm;
    }


    /**
     * onActivityResult方法处理,使用相机的处理
     */
    public void onRequestCodeCamera(Activity mContext){
        addPicToGallery(mContext,imgPathOri);
        cropPhoto(mContext,imgUriOri);
        MLog.i("openCameraResult_imgPathOri:" + imgPathOri);
        MLog.i( "openCameraResult_imgUriOri:" + imgUriOri.toString());
    }

    /**
     * onActivityResult方法处理,使用相册的处理
     * @param mContext
     * @param data onActivityResult返回的Intent
     */
    public void onRequestCodeGallery(Activity mContext,Intent data){
        if (data != null) {
            Uri imgUriSel = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //打开相册会返回一个经过图像选择器安全化的Uri，直接放入裁剪程序会不识别，抛出[暂不支持此类型：华为7.0]
                //formatUri会返回根据Uri解析出的真实路径
                String imgPathSel = URIUtil.formatUri(mContext, imgUriSel);
                //根据真实路径转成File,然后通过应用程序重新安全化，再放入裁剪程序中才可以识别
                cropPhoto(mContext, FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", new File(imgPathSel)));
                MLog.i("Kit_sel_path:" + imgPathSel);
                MLog.i("Kit_sel_uri:" + Uri.fromFile(new File(imgPathSel)));
            } else {
                cropPhoto(mContext, imgUriSel);
            }
            MLog.i("openGalleryResult_imgUriSel:" + imgUriSel);
        }
    }

    /**
     *  onActivityResult方法处理,图片剪裁后的处理，所有的步骤最终这是最后一步。获得最后的图片
     * @param mContext
     * @return
     */
    public Uri onRequestCodeCrop(Activity mContext){
        addPicToGallery(mContext,imgPathCrop);
        //ImageUtils.imageViewSetPic(ivImage, imgPathCrop);
        mContext.revokeUriPermission(imgUriCrop, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        MLog.i("cropPhotoResult_imgPathCrop:" + imgPathCrop);
        MLog.i( "cropPhotoResult_imgUriCrop:" + imgUriCrop.toString());
        return imgUriCrop;
    }
    //原图像 路径
    private String imgPathOri;
    //原图像 URI
    private Uri imgUriOri;
    //requestCode=请求打开摄像头
    private final int REQUEST_OPEN_CAMERA = 0x011;
    /**
     * 打开相机拍照，并设置原图片存储路径
     */
    public void openCamera(Activity mContext){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
        File oriPhotoFile = null;
        try {
            oriPhotoFile = createOriImageFile();//设置原图片存储路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (oriPhotoFile != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imgUriOri = Uri.fromFile(oriPhotoFile);
            } else {
                imgUriOri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", oriPhotoFile);
            }
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri);
            mContext.startActivityForResult(intent, REQUEST_OPEN_CAMERA);

            MLog.i("openCamera_imgPathOri:" + imgPathOri);
            MLog.i( "openCamera_imgUriOri:" + imgUriOri.toString());
        }
    }
    /**
     * 创建原图像保存的文件
     * android7.0以上需要照片中转一下，否则报错，部分机型如华为小米也需要。
     * @return
     * @throws IOException
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "HomePic_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pictureDirOri = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/OriPicture");
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(
                imgNameOri,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirOri       /* directory */
        );
        imgPathOri = image.getAbsolutePath();
        return image;
    }


    //裁剪图像 路径
    private static String imgPathCrop;
    //裁剪图像 URI
    private Uri imgUriCrop;
    //requestCode=剪裁图片
    private final int REQUEST_CROP_PHOTO = 0x033;

    /**
     * 创建裁剪图像保存的文件
     * android7.0以上需要照片中转一下，否则报错，部分机型如华为小米也需要。
     * @return
     * @throws IOException
     */
    private File createCropImageFile() throws IOException {
        String imgNameCrop = "HomePic_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pictureDirCrop = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/CropPicture");
        if (!pictureDirCrop.exists()) {
            pictureDirCrop.mkdirs();
        }
        File image = File.createTempFile(
                imgNameCrop,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirCrop      /* directory */
        );
        imgPathCrop = image.getAbsolutePath();
        return image;
    }

    /**
     * 裁剪图像
     * @param mContext
     * @param uri
     */
    private void cropPhoto(Activity mContext,Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        File cropPhotoFile = null;
        try {
            cropPhotoFile = createCropImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cropPhotoFile != null) {

            //7.0 安全机制下不允许保存裁剪后的图片
            //所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
            imgUriCrop = Uri.fromFile(cropPhotoFile);

            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", true);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriCrop);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            mContext.startActivityForResult(intent, REQUEST_CROP_PHOTO);

            MLog.i("cropPhoto_imgPathCrop:" + imgPathCrop.toString());
            MLog.i( "cropPhoto_imgUriCrop:" + imgUriCrop.toString());
        }
    }

    /**
     * 把图像添加进系统相册
     * @param imgPath 图像路径
     */
    private void addPicToGallery(Activity mContext,String imgPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imgPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }
}
