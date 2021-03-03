package indi.ayun.mingwork_all.notice;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayun.mingwork_all.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * @Description: 主要功能:SnackBar工具类
 */

public class SnackBarUtil {
    public static final  int Info = 1;
    public static final  int Confirm = 2;
    public static final  int Warning = 3;
    public static final  int Alert = 4;


    private static  int red = 0xfff44336;
    private static  int green = 0xff4caf50;
    private static  int blue = 0xff2195f3;
    private static  int orange = 0xffffc107;
    /**
     * 短显示Snackbar，自定义颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static void onShortSnack(View view, String message, int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
        setSnackColor(snackbar,messageColor,backgroundColor);
        snackbar.show();
    }

    /**
     * 长显示Snackbar，自定义颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static void onLongSnack(View view, String message, int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        setSnackColor(snackbar,messageColor,backgroundColor);
        snackbar.show();
    }

    /**
     * 自定义时常显示Snackbar，自定义颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static void onIndefiniteSnack(View view, String message,int duration,int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        setSnackColor(snackbar,messageColor,backgroundColor);
        snackbar.show();
    }

    /**
     * 短显示Snackbar，可选预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static void onShortSnack(View view, String message, int type){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
        switchType(snackbar,type);
        snackbar.show();
    }

    /**
     * 长显示Snackbar，可选预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static void onLongSnack(View view, String message,int type){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        switchType(snackbar,type);
        snackbar.show();
    }

    /**
     * 自定义时常显示Snackbar，可选预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static void onIndefiniteSnack(View view, String message,int duration,int type){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        switchType(snackbar,type);
        snackbar.show();
    }

    /**
     * 选择类型的按钮类型
     * @param view
     * @param message
     * @param type
     * @param butMes
     * @param listener
     */
    public static void onSnackButton(View view, String message, int type, String butMes, final OnSnackBarListener listener){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        switchType(snackbar,type);
        snackbar.setAction(butMes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSnackBarAction();
            }
        });
        snackbar.show();
    }

    /**
     * 其他扩展
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar extension(View view, String message, int messageColor, int backgroundColor){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        setSnackColor(snackbar,messageColor,backgroundColor);
        return snackbar;
    }

    //选择预设类型
    private static void switchType(Snackbar snackbar,int type){
        switch (type){
            case Info:
                setSnackColor(snackbar,blue);
                break;
            case Confirm:
                setSnackColor(snackbar,green);
                break;
            case Warning:
                setSnackColor(snackbar,orange);
                break;
            case Alert:
                setSnackColor(snackbar, Color.YELLOW,red);
                break;
        }
    }

    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    private static void setSnackColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    private static void setSnackColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

}
