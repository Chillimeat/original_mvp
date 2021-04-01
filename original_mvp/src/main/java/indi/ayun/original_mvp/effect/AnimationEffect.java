package indi.ayun.original_mvp.effect;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import java.util.HashMap;
import java.util.Objects;

import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.utils.verification.IsNothing;

public class AnimationEffect extends Thread{
    private static HashMap<Integer,Animation> animationInList=new HashMap<>();
    private static HashMap<Integer,Animation> animationOutList=new HashMap<>();
    private static HashMap<Integer,String> animationSwitch=new HashMap<>();

    public static void BrightCrystal(View view) {
        view.setVisibility(View.VISIBLE);
        animationSwitch.put(view.getId(),"true");
        animationInList.put(view.getId(),new AlphaAnimation(1.0f, 0));
        Animation alphaAnimationIn = Objects.requireNonNull(animationInList.get(view.getId()));
        //设置动画插值器 被用来修饰动画效果,定义动画的变化率
        alphaAnimationIn.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间
        alphaAnimationIn.setDuration(1000);

        animationOutList.put(view.getId(),new AlphaAnimation(0, 1.0f));
        Animation alphaAnimationOut = Objects.requireNonNull(animationOutList.get(view.getId()));
        //设置动画插值器 被用来修饰动画效果,定义动画的变化率
        alphaAnimationOut.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间
        alphaAnimationOut.setDuration(1000);

        view.setAnimation(alphaAnimationOut);
        view.setAnimation(alphaAnimationIn);
        /**
         * 监听动画实现动画间的切换
         */
        alphaAnimationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isOpen=false;
                if (IsNothing.onAnything(animationSwitch.get(view.getId())))isOpen=true;
                if (isOpen)view.startAnimation(alphaAnimationIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimationIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isOpen=false;
                if (IsNothing.onAnything(animationSwitch.get(view.getId())))isOpen=true;
                if (isOpen)view.startAnimation(alphaAnimationOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 多个动画，取消不了
     * @param view
     */
    public static void closeBrightCrystal(View view,boolean isShow){
        if (IsNothing.onAnything(animationSwitch.get(view.getId())))animationSwitch.remove(view.getId());
        if (null!=animationInList.get(view.getId())){
            MLog.d("Power");
            Objects.requireNonNull(animationInList.get(view.getId())).cancel();
            animationInList.remove(view.getId());
        }
        if (null!=animationOutList.get(view.getId())){
            MLog.d("Power");
            Objects.requireNonNull(animationOutList.get(view.getId())).cancel();
            animationOutList.remove(view.getId());
        }
        if (null!=view){
            if (!isShow)view.setVisibility(View.INVISIBLE);
        }
    }
}
