package indi.ayun.mingwork_all.smartrefresh.member;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import indi.ayun.mingwork_all.smartrefresh.api.RefreshInternal;
import indi.ayun.mingwork_all.smartrefresh.api.RefreshKernel;
import indi.ayun.mingwork_all.smartrefresh.api.RefreshLayout;
import indi.ayun.mingwork_all.smartrefresh.constant.SpinnerStyle;
import indi.ayun.mingwork_all.smartrefresh.internal.InternalAbstract;
import indi.ayun.mingwork_all.smartrefresh.util.DensityUtil;
import indi.ayun.mingwork_all.smartrefresh.util.SmartUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 会员界面组件
 * Created by SCWANG on 2017/5/28.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class InternalMember<T extends InternalMember> extends InternalAbstract implements RefreshInternal {

    public static final byte ID_TEXT_TITLE = 1;
    public static final byte ID_IMAGE_ARROW = 2;
    public static final byte ID_IMAGE_PROGRESS = 3;

    protected LinearLayout mCenterLayout;
    protected RefreshKernel mRefreshKernel;
    protected Drawable cusDrawable;
    protected Drawable errDrawable;
    //    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    protected Integer mAccentColor;
    protected Integer mPrimaryColor;
    protected int mBackgroundColor;
    protected int mFinishDuration = 0;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;

    //<editor-fold desc="RelativeLayout">

    public InternalMember(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSpinnerStyle = SpinnerStyle.Translate;
        mCenterLayout = new LinearLayout(context);
        mCenterLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mCenterLayout.setOrientation(LinearLayout.VERTICAL);

        final View thisView = this;
        final ViewGroup thisGroup = this;
        final ViewGroup centerLayout = mCenterLayout;
        final DensityUtil density = new DensityUtil();

        centerLayout.setId(android.R.id.widget_frame);

        LayoutParams lpHeaderLayout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        thisGroup.addView(centerLayout,lpHeaderLayout);

        if (thisView.getPaddingTop() == 0) {
            if (thisView.getPaddingBottom() == 0) {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = density.dip2px(20), thisView.getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = density.dip2px(20), thisView.getPaddingRight(), mPaddingBottom = thisView.getPaddingBottom());
            }
        } else {
            if (thisView.getPaddingBottom() == 0) {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = thisView.getPaddingTop(), thisView.getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                mPaddingTop = thisView.getPaddingTop();
                mPaddingBottom = thisView.getPaddingBottom();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final View thisView = this;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            thisView.setPadding(thisView.getPaddingLeft(), 0, thisView.getPaddingRight(), 0);
        } else {
            thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop, thisView.getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected T self() {
        //noinspection unchecked
        return (T) this;
    }

    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        onStartAnimator(refreshLayout, height, maxDragHeight);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return mFinishDuration;//延迟500毫秒之后再弹回
    }

    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int ... colors) {
        if (colors.length > 0) {
            final View thisView = this;
            if (!(thisView.getBackground() instanceof BitmapDrawable) && mPrimaryColor == null) {
                setPrimaryColor(colors[0]);
                mPrimaryColor = null;
            }
            if (mAccentColor == null) {
                if (colors.length > 1) {
                    setAccentColor(colors[1]);
                }
                mAccentColor = null;
            }
        }
    }


    public T setProgressDrawable(Drawable drawable) {
        return self();
    }
    public T setProgressResource(@DrawableRes int resId) {
        return self();
    }

    public T setArrowDrawable(Drawable drawable) {
        return self();
    }
    public T setArrowResource(@DrawableRes int resId) {
        return self();
    }

    public T setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return self();
    }

    public T setPrimaryColor(@ColorInt int primaryColor) {
        mBackgroundColor = mPrimaryColor = primaryColor;
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundFor(this, mPrimaryColor);

        }
        return self();
    }

    public T setAccentColor(@ColorInt int accentColor) {
        mAccentColor = accentColor;
        return self();
    }

    public T setPrimaryColorId(@ColorRes int colorId) {
        final View thisView = this;
        setPrimaryColor(SmartUtil.getColor(thisView.getContext(), colorId));
        return self();
    }

    public T setAccentColorId(@ColorRes int colorId) {
        final View thisView = this;
        setAccentColor(SmartUtil.getColor(thisView.getContext(), colorId));
        return self();
    }

    public T setFinishDuration(int delay) {
        mFinishDuration = delay;
        return self();
    }

    public T setTextSizeTitle(float size) {
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return self();
    }


    public T setDrawableMarginRight(float dp) {
        return self();
    }

    public T setDrawableSize(float dp) {
        return self();
    }

    public T setDrawableArrowSize(float dp) {
        return self();
    }

    public T setDrawableProgressSize(float dp) {
        return self();
    }



}
