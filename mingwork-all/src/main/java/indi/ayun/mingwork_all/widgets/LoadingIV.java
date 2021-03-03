package indi.ayun.mingwork_all.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.mlog.MLog;

@SuppressLint("AppCompatCustomView")
public class LoadingIV extends ImageView {
    protected Drawable cusDrawable;
    protected Drawable turnDrawable;
    protected ImageView turnDrawableIV;
    public LoadingIV(Context context) {
        super(context);
    }

    public LoadingIV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        turnDrawableIV = new ImageView(context);//TODO:这里是空，研究一下自定义控件的组合控件
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingIV);
        if (ta.hasValue(R.styleable.LoadingIV_LoadingProgressFormat)) {
            cusDrawable = ta.getDrawable(R.styleable.LoadingIV_LoadingProgressFormat);
            MLog.d("Loading:cusDrawable");
        }
        if (ta.hasValue(R.styleable.LoadingIV_LoadingProgressTurn)) {
            turnDrawable = ta.getDrawable(R.styleable.LoadingIV_LoadingProgressTurn);
            MLog.d("Loading:turnDrawable");
        }

        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.weight_loading_iv);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (turnDrawable!=null){
            MLog.d("Loading:turnDrawable");
            turnDrawableIV.setImageDrawable(turnDrawable);
            turnDrawableIV.startAnimation(operatingAnim);
        }
        MLog.d("Loading");
    }

    public LoadingIV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        turnDrawableIV = new ImageView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.LoadingIV);
        if (ta.hasValue(R.styleable.LoadingIV_LoadingProgressFormat)) {
            cusDrawable = ta.getDrawable(R.styleable.LoadingIV_LoadingProgressFormat);
            MLog.d("Loading:cusDrawable");
        }
        if (ta.hasValue(R.styleable.LoadingIV_LoadingProgressTurn)) {
            turnDrawable = ta.getDrawable(R.styleable.LoadingIV_LoadingProgressTurn);
            MLog.d("Loading:turnDrawable");
        }

        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.weight_loading_iv);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (turnDrawable!=null){
            MLog.d("Loading:turnDrawable");
            turnDrawableIV.setImageDrawable(turnDrawable);
            turnDrawableIV.startAnimation(operatingAnim);
        }
        MLog.d("Loading");
    }

    @SuppressLint("NewApi")
    public LoadingIV(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        turnDrawableIV = new ImageView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.LoadingIV);
        if (ta.hasValue(R.styleable.LoadingIV_LoadingProgressFormat)) {
            cusDrawable = ta.getDrawable(R.styleable.LoadingIV_LoadingProgressFormat);
            MLog.d("Loading:cusDrawable");
        }
        if (ta.hasValue(R.styleable.LoadingIV_LoadingProgressTurn)) {
            turnDrawable = ta.getDrawable(R.styleable.LoadingIV_LoadingProgressTurn);
            MLog.d("Loading:turnDrawable");
        }

        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.weight_loading_iv);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (turnDrawable!=null){
            MLog.d("Loading:turnDrawable");
            turnDrawableIV.setImageDrawable(turnDrawable);
            turnDrawableIV.startAnimation(operatingAnim);
        }
        MLog.d("Loading");
    }

    //转动在横屏(被设置为了不重绘activity)时会出现问题，即旋转中心偏移，导致动画旋转偏离原旋转中心。解决如下
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        if (operatingAnim != null && infoOperatingIV != null && operatingAnim.hasStarted()) {
//            infoOperatingIV.clearAnimation();
//            infoOperatingIV.startAnimation(operatingAnim);
//        }
    }
}
