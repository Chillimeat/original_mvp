package indi.ayun.original_mvp.utils.network;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.IOException;

import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.preference.OpBaseSetting;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class URIUtil {

    /**
     * 获取隐藏的网址中的文件名
     * @param url
     * @return
     */
    public static String getRedirectURLName(String url){
        String fileName = null;
        if (!TextUtils.isEmpty(url)) {
            try {
                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                Request request = new Request.Builder()
                        .url(url)//请求接口。如果需要传参拼接到接口后面。
                        .build();//创建Request 对象
                Response response = client.newCall(request).execute();//得到Response 对象
                HttpUrl realUrl = response.request().url();
                MLog.e("zmm", "real:" + realUrl);
                if (realUrl != null) {
                    String temp = realUrl.toString();
                    fileName = temp.substring(temp.lastIndexOf("/") + 1);

                }
            } catch (IOException e) {
                e.printStackTrace();
                MLog.e("zmm", "Get File Name:error" + e);
            }
        }
        MLog.e("zmm", "fileName--->" + fileName);
        return fileName;

    }

    /**
     * 通过传进来得值计算出后缀
     * @param s 值：网址，本地地址，文件名，后缀
     * @return
     */
    public static StringBuilder calculationFileName(String s){
        StringBuilder name= new StringBuilder("default");
        boolean isFileName=false,isFileAllName=false,isUri_after=false,isUri_in=false;
        String[] ss =s.split("/");
        MLog.d("FileName:"+ss.length+"; last:"+ss[ss.length-1]);
        if (ss.length==0||!s.contains("/")){//"/"和“xxxxx”
            int dotIndex = s.lastIndexOf(".");
            if(dotIndex < 0 ){
                isFileName=true;
            }
            if (dotIndex > 0 ){
                isFileAllName = true;
            }
        }
        else {
            int dotIndex = ss[ss.length-1].lastIndexOf(".");
            if(dotIndex < 0 ){//文件名在中间
                isUri_in=true;
            }
            if (dotIndex > 0 ){//文件名在最后
                isUri_after = true;
            }
        }

        if (isUri_in){
            for (int i=ss.length-1;i>=0;i--){
                if (!ss[i].contains(OpBaseSetting.getInstance().getDomain())) {//不是域名
                    int dotIndex = ss[i].lastIndexOf(".");
                    if (dotIndex > 0) {
                        name= new StringBuilder(ss[i].substring(0,dotIndex));
                    }
                }
            }
        }

        if (isUri_after){
            int dotIndex = s.lastIndexOf(".");
            int slashIndex = s.lastIndexOf("/");
            name= new StringBuilder(s.substring(slashIndex+1,dotIndex));
        }
        if (isFileAllName){
            name= new StringBuilder(ss[0]);
        }
        if (isFileName) {
            name= new StringBuilder(s);
        }
        return name;
    }

    /**
     * 通过传进来得值计算出后缀
     * @param defaults 默认后缀
     * @param s 值：网址，本地地址，文件名，后缀
     * @return
     */
    public static String calculationSuffix(String s,String defaults){
        String suffix= defaults;
        boolean isFileName=false,isFileAllName=false,isUri_after=false,isUri_in=false;
        String[] ss =s.split("/");
        if (ss.length==0||!s.contains("/")){//"/"和“xxxxx”
            int dotIndex = s.lastIndexOf(".");
            if(dotIndex < 0 ){
                isFileName=true;
            }
            if (dotIndex > 0 ){
                isFileAllName = true;
            }
        }
        else {
            int dotIndex = ss[ss.length-1].lastIndexOf(".");
            if(dotIndex < 0 ){//文件名在中间
                isUri_in=true;
            }
            if (dotIndex > 0 ){//文件名在最后
                isUri_after = true;
            }
        }

        if (isUri_in){
            for (int i=ss.length-1;i>=0;i--){
                if (!ss[i].contains(OpBaseSetting.getInstance().getDomain())) {//不是域名
                    int dotIndex = ss[i].lastIndexOf(".");
                    if (dotIndex > 0) {//文件名在最后
                        suffix= ss[i].substring(dotIndex+1,ss[i].length());
                    }
                }
            }
        }

        if (isUri_after){
            int dotIndex = s.lastIndexOf(".");
            suffix= s.substring(dotIndex+1,s.length());
        }
        if (isFileAllName){
            suffix= ss[1];
        }
        if (isFileName) {
            suffix= defaults;
        }
        return suffix;
    }
    /**
     * 根据Uri的不同Scheme解析出在本机的路径
     * @param context
     * @param uri
     * @return Uri的真实路径
     */
    @TargetApi(19)
    public static String formatUri(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
    /**
     * 图片uri转path
     * //
     *
     * @param uri
     * @param activity
     * @return
     */
    public static String getPicPathFromUri(Uri uri, Activity activity) {
        String value = uri.getPath();

        try {
            if (value.startsWith("/external")) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
                // Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    final String picUri = cursor.getString(column_index);
                    //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
                    if(Integer.parseInt(Build.VERSION.SDK) < 14)
                    {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    return picUri;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return value;
    }
}
