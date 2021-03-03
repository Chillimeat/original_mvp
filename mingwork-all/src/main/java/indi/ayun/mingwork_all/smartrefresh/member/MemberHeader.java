package indi.ayun.mingwork_all.smartrefresh.member;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import indi.ayun.mingwork_all.smartrefresh.api.RefreshHeader;
import indi.ayun.mingwork_all.smartrefresh.api.RefreshLayout;
import indi.ayun.mingwork_all.smartrefresh.constant.RefreshState;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 我的页面下拉头部
 * Created by SCWANG on 2017/5/28.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class MemberHeader extends InternalMember<MemberHeader> implements RefreshHeader {

    public static final byte ID_TEXT_UPDATE = 4;

    protected String KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

    protected SharedPreferences mShared;

    //<editor-fold desc="RelativeLayout">
    public MemberHeader(Context context) {
        this(context, null);
    }

    public MemberHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemberHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        try {//try 不能删除-否则会出现兼容性问题
            if (context instanceof FragmentActivity) {
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                if (manager != null) {
                    @SuppressLint("RestrictedApi")
                    List<Fragment> fragments = manager.getFragments();
                    if (fragments != null && fragments.size() > 0) {
                        setLastUpdateTime(new Date());
                        return;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        KEY_LAST_UPDATE_TIME += context.getClass().getName();
        mShared = context.getSharedPreferences("MemberHeader", Context.MODE_PRIVATE);
        setLastUpdateTime(new Date(mShared.getLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));

    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        return super.onFinish(layout, success);//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
    }
    //</editor-fold>

    //<editor-fold desc="API">

    public MemberHeader setLastUpdateTime(Date time) {
        final View thisView = this;
        return this;
    }

    public MemberHeader setTimeFormat(DateFormat format) {
        return this;
    }

    public MemberHeader setLastUpdateText(CharSequence text) {
        return this;
    }

    public MemberHeader setAccentColor(@ColorInt int accentColor) {
        return super.setAccentColor(accentColor);
    }

    public MemberHeader setEnableLastTime(boolean enable) {
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
//            mRefreshKernel.requestRemeasureHeightForHeader();
        }
        return this;
    }

    public MemberHeader setTextSizeTime(float size) {
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
//            mRefreshKernel.requestRemeasureHeightForHeader();
        }
        return this;
    }


    public MemberHeader setTextTimeMarginTop(float dp) {
        return this;
    }


}
