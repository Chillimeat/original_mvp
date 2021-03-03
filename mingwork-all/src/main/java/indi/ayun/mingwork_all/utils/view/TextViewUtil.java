package indi.ayun.mingwork_all.utils.view;

import android.graphics.Paint;
import android.widget.TextView;

public class TextViewUtil {
    /**
     * 给TextView设置下划线
     * @param textView
     */
    public static void setTVUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }


}
