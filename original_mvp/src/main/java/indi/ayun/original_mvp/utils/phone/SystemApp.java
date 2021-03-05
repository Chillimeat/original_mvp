package indi.ayun.original_mvp.utils.phone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebView;


public class SystemApp {
    //--------------------------------------浏览器-----------
    /**
     * 调用本地浏览器打开一个网页
     *
     * @param mContext
     *            上下文
     * @param strSiteUrl
     *            网页地址
     */
    public static void openWebSite(Context mContext, String strSiteUrl) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strSiteUrl));
        mContext.startActivity(webIntent);
    }
    /**
     * 获取的浏览器指纹(User-Agent)
     * @param ctx
     * @return
     */
    public static String getUA(Context ctx) {
        final String system_ua = System.getProperty("http.agent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return new WebView(ctx).getSettings().getDefaultUserAgent(ctx) + "__" + system_ua;
        } else {
            return new WebView(ctx).getSettings().getUserAgentString() + "__" + system_ua;
        }
    }

}
