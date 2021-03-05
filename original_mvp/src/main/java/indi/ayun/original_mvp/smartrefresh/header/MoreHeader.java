package indi.ayun.original_mvp.smartrefresh.header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import indi.ayun.original_mvp.R;

import indi.ayun.original_mvp.smartrefresh.api.RefreshHeader;
import indi.ayun.original_mvp.smartrefresh.api.RefreshLayout;
import indi.ayun.original_mvp.smartrefresh.constant.RefreshState;
import indi.ayun.original_mvp.smartrefresh.constant.SpinnerStyle;
import indi.ayun.original_mvp.smartrefresh.internal.ArrowDrawable;
import indi.ayun.original_mvp.smartrefresh.internal.InternalClassics;
import indi.ayun.original_mvp.smartrefresh.internal.ProgressDrawable;
import indi.ayun.original_mvp.smartrefresh.util.DensityUtil;

import java.util.List;

/**
 * 头部加载更多
 * Created by SCWANG on 2017/5/28.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class MoreHeader extends InternalClassics<MoreHeader> implements RefreshHeader {

    public static final byte ID_TEXT_UPDATE = 4;

    protected String KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

    protected SharedPreferences mShared;
    protected boolean mEnableLastTime = true;

    //<editor-fold desc="RelativeLayout">
    public MoreHeader(Context context) {
        this(context, null);
    }

    public MoreHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoreHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final View thisView = this;
        final View arrowView = mArrowView;
        final View progressView = mProgressView;
        final ViewGroup centerLayout = mCenterLayout;
        final DensityUtil density = new DensityUtil();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader);

        RelativeLayout.LayoutParams lpArrow = (RelativeLayout.LayoutParams) arrowView.getLayoutParams();
        RelativeLayout.LayoutParams lpProgress = (RelativeLayout.LayoutParams) progressView.getLayoutParams();

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, lpArrow.height);

        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, lpProgress.height);

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.height);

        mFinishDuration = ta.getInt(R.styleable.ClassicsHeader_srlFinishDuration, mFinishDuration);
        mEnableLastTime = ta.getBoolean(R.styleable.ClassicsHeader_srlEnableLastTime, mEnableLastTime);
        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal())];

        if (ta.hasValue(R.styleable.ClassicsHeader_srlDrawableArrow)) {
            mArrowView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsHeader_srlDrawableArrow));
        } else {
            mArrowDrawable = new ArrowDrawable();
            mArrowDrawable.setColor(0xff666666);
            mArrowView.setImageDrawable(mArrowDrawable);
        }

        if (ta.hasValue(R.styleable.ClassicsHeader_srlDrawableProgress)) {
            cusDrawable = ta.getDrawable(R.styleable.ClassicsHeader_srlDrawableProgress);
            mProgressView.setImageDrawable(cusDrawable);
        } else {
            mProgressDrawable = new ProgressDrawable();
            mProgressDrawable.setColor(0xff666666);
            mProgressView.setImageDrawable(mProgressDrawable);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlDrawableError)) {
            errDrawable = ta.getDrawable(R.styleable.ClassicsHeader_srlDrawableError);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTitle, DensityUtil.dp2px(16)));
        } else {
            mTitleText.setTextSize(16);
        }


        if (ta.hasValue(R.styleable.ClassicsHeader_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.ClassicsHeader_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsHeader_srlAccentColor, 0));
        }

        ta.recycle();


        try {//try 不能删除-否则会出现兼容性问题
            if (context instanceof FragmentActivity) {
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                if (manager != null) {
                    @SuppressLint("RestrictedApi")
                    List<Fragment> fragments = manager.getFragments();
                    if (fragments != null && fragments.size() > 0) {
                        return;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        KEY_LAST_UPDATE_TIME += context.getClass().getName();
        mShared = context.getSharedPreferences("ClassicsHeader", Context.MODE_PRIVATE);

    }


    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        return 0;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        final View progressView = mProgressView;
        switch (newState) {
            case None:
            case PullDownToRefresh:
                arrowView.setVisibility(VISIBLE);
                progressView.setVisibility(INVISIBLE);
                //arrowView.animate().rotation(0);
                break;
            case Refreshing:
            case RefreshReleased:
                arrowView.setVisibility(INVISIBLE);
                progressView.setVisibility(VISIBLE);
                break;
            case ReleaseToRefresh:
                break;
            case ReleaseToTwoLevel:
                arrowView.animate().rotation(0);
                break;
            case Loading:
                arrowView.setVisibility(GONE);
                break;
        }
    }

}
