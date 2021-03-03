package indi.ayun.mingwork_all.notice;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import static indi.ayun.mingwork_all.MingWork_All.getContext;


/**
 * Created by ayun On 2018/01/23
 */
public class ToastUtil extends Toast {
    private static Context context = getContext();
    private static Toast toast = new Toast(getContext());

    public ToastUtil() {
        super(context);
    }

    /**
     * showShortToast
     *
     * @param message
     */
    public static void showShortToast(String message) {
        toast.makeText(context, message, toast.LENGTH_SHORT).show();
    }

    /**
     * showLongToast
     *
     * @param message
     */
    public static void showLongToast(String message) {
        toast.makeText(context, message, toast.LENGTH_LONG).show();
    }

    /**
     * 屏幕底部中间位置显示短时间Toast
     *
     * @param context
     * @param message
     */
    public static void showBottomCenter(Context context, String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 屏幕底部左边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showBottomLeft(Context context, String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        toast.show();
    }

    /**
     * 屏幕底部右边位置短时间显示Toast
     *
     * @param message
     */
    public static void showBottomRight(String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        toast.show();
    }

    /**
     * 屏幕中心位置短时间显示Toast
     *
     * @param message
     *
     */
    public static void showCenter(String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 屏幕中心左边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showCenterLeft(Context context, String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast.show();
    }

    /**
     * 屏幕中心右边位置短时间显示Toast
     *
     * @param message
     */
    public static void showCenterRight(String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 0, 0);
        toast.show();
    }

    /**
     * 屏幕顶部中心位置短时间显示Toast
     *
     * @param message
     */
    public static void showTopCenter(String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    /**
     * 屏幕顶部左边位置短时间显示Toast
     *
     * @param message
     */
    public static void showTopLeft(String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.show();
    }

    /**
     * 屏幕顶部右边位置短时间显示Toast
     *
     * @param message
     */
    public static void showTopRight(String message) {

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
        toast.show();
    }


}
