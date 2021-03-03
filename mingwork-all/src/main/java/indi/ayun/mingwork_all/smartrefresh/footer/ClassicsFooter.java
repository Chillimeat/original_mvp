package indi.ayun.mingwork_all.smartrefresh.footer;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.smartrefresh.api.RefreshFooter;
import indi.ayun.mingwork_all.smartrefresh.api.RefreshLayout;
import indi.ayun.mingwork_all.smartrefresh.constant.RefreshState;
import indi.ayun.mingwork_all.smartrefresh.internal.ArrowDrawable;
import indi.ayun.mingwork_all.smartrefresh.internal.InternalClassicsFooter;
import indi.ayun.mingwork_all.smartrefresh.internal.ProgressDrawable;
import indi.ayun.mingwork_all.smartrefresh.util.DensityUtil;
import indi.ayun.mingwork_all.smartrefresh.constant.SpinnerStyle;


/**
 * 经典上拉底部组件
 * Created by SCWANG on 2017/5/28.
 */

@SuppressWarnings({"unused", "UnusedReturnValue", "deprecation"})
public class ClassicsFooter extends InternalClassicsFooter<ClassicsFooter> implements RefreshFooter {

    public static String REFRESH_FOOTER_PULLING = null;//"上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = null;//"释放立即加载";
    public static String REFRESH_FOOTER_LOADING = null;//"正在加载...";
    public static String REFRESH_FOOTER_REFRESHING = null;//"正在刷新...";
    public static String REFRESH_FOOTER_FINISH = null;//"加载完成";
    public static String REFRESH_FOOTER_FAILED = null;//"加载失败";
    public static String REFRESH_FOOTER_NOTHING = null;//"没有更多数据了";

    protected boolean mNoMoreData = false;

    //<editor-fold desc="LinearLayout">
    public ClassicsFooter(Context context) {
        this(context, null);
    }

    public ClassicsFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicsFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (REFRESH_FOOTER_PULLING == null) {
            REFRESH_FOOTER_PULLING = context.getString(R.string.srl_footer_pulling);
        }

        if (REFRESH_FOOTER_RELEASE == null) {
            REFRESH_FOOTER_RELEASE = context.getString(R.string.srl_footer_release);
        }

        if (REFRESH_FOOTER_LOADING == null) {
            REFRESH_FOOTER_LOADING = context.getString(R.string.srl_footer_loading);
        }

        if (REFRESH_FOOTER_REFRESHING == null) {
            REFRESH_FOOTER_REFRESHING = context.getString(R.string.srl_footer_refreshing);
        }

        if (REFRESH_FOOTER_FINISH == null) {
            REFRESH_FOOTER_FINISH = context.getString(R.string.srl_footer_finish);
        }

        if (REFRESH_FOOTER_FAILED == null) {
            REFRESH_FOOTER_FAILED = context.getString(R.string.srl_footer_failed);
        }

        if (REFRESH_FOOTER_NOTHING == null) {
            REFRESH_FOOTER_NOTHING = context.getString(R.string.srl_footer_pulling);
        }

        final View thisView = this;
        final View lineViewLeft = mLineViewLeft;
        final View arrowView = mArrowView;
        final View progressView = mProgressView;
        final DensityUtil density = new DensityUtil();

        mTitleText.setTextColor(0xff666666);
        mTitleText.setText(thisView.isInEditMode() ? REFRESH_FOOTER_LOADING : REFRESH_FOOTER_PULLING);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsFooter);

        RelativeLayout.LayoutParams lpArrow = (RelativeLayout.LayoutParams) arrowView.getLayoutParams();
        RelativeLayout.LayoutParams lpProgress = (RelativeLayout.LayoutParams) progressView.getLayoutParams();

        lpProgress.rightMargin = ta.getDimensionPixelSize(R.styleable.ClassicsFooter_srlDrawableMarginRight, density.dip2px(20));
        lpArrow.rightMargin = lpProgress.rightMargin;

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableArrowSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableArrowSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableProgressSize, lpProgress.height);

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_srlDrawableSize, lpProgress.height);

        mFinishDuration = ta.getInt(R.styleable.ClassicsFooter_srlFinishDuration, mFinishDuration);
        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ClassicsFooter_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal())];

        if (ta.hasValue(R.styleable.ClassicsFooter_srlDrawableArrow)) {
            mArrowView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsFooter_srlDrawableArrow));
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

        if (ta.hasValue(R.styleable.ClassicsFooter_srlTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.ClassicsFooter_srlTextSizeTitle, DensityUtil.dp2px(16)));
        } else {
            mTitleText.setTextSize(16);
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.ClassicsFooter_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsFooter_srlAccentColor, 0));
        }
        mLineView.setVisibility(INVISIBLE);
        ta.recycle();

    }

//    @Override
//    protected ClassicsFooter self() {
//        return this;
//    }

    //</editor-fold>

    //<editor-fold desc="RefreshFooter">

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (!mNoMoreData) {
            super.onStartAnimator(refreshLayout, height, maxDragHeight);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (!success && errDrawable != null) {
            mProgressView.setImageDrawable(errDrawable);
        }
        if (!mNoMoreData) {
            mTitleText.setText(success ? REFRESH_FOOTER_FINISH : REFRESH_FOOTER_FAILED);
            return super.onFinish(layout, success);
        }
        return 0;
    }

    /**
     * ClassicsFooter 在(SpinnerStyle.FixedBehind)时才有主题色
     */
    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(colors);
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            final View arrowView = mArrowView;
            final View progressView = mProgressView;
            if (noMoreData) {
                mTitleText.setText(REFRESH_FOOTER_NOTHING);
                arrowView.setVisibility(GONE);
                progressView.setVisibility(GONE);
                mLineView.setVisibility(VISIBLE);
            } else {
                mTitleText.setText(REFRESH_FOOTER_PULLING);
                arrowView.setVisibility(VISIBLE);
                mLineView.setVisibility(INVISIBLE);
            }
        }
        return true;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        final View progressView = mProgressView;
        if (!mNoMoreData) {
            switch (newState) {
                case None:
                    arrowView.setVisibility(VISIBLE);
                case PullUpToLoad:
                    mTitleText.setText(REFRESH_FOOTER_PULLING);
                    arrowView.setVisibility(VISIBLE);
                    progressView.setVisibility(INVISIBLE);
                    //arrowView.animate().rotation(180);
                    break;
                case Loading:
                case LoadReleased:
                    arrowView.setVisibility(INVISIBLE);
                    progressView.setVisibility(VISIBLE);
                    mTitleText.setText(REFRESH_FOOTER_LOADING);
                    break;
                case ReleaseToLoad:
                    mTitleText.setText(REFRESH_FOOTER_RELEASE);
                    arrowView.setVisibility(VISIBLE);
                    progressView.setVisibility(INVISIBLE);
                    // arrowView.animate().rotation(0);
                    break;
                case Refreshing:
                    mTitleText.setText(REFRESH_FOOTER_REFRESHING);
                    arrowView.setVisibility(INVISIBLE);
                    progressView.setVisibility(VISIBLE);
                    break;
            }
        }
    }
    //</editor-fold>

}
