package indi.ayun.original_mvp.media;

import android.app.Activity;
import android.view.WindowManager;

/**
 * @Description TODO
 * @Author Created by ayun on 2021/3/30 15:42.
 */
public class MediaPlayUtil {
    /**
     * 播放的时候不熄灭屏幕
     * @param context
     */
    public static void playStateScreenON(Activity context){
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
