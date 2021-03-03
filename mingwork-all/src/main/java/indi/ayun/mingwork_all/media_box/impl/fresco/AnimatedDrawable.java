package indi.ayun.mingwork_all.media_box.impl.fresco;

import java.util.concurrent.ScheduledExecutorService;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.animation.LinearInterpolator;

import com.facebook.common.time.MonotonicClock;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AnimatedDrawable extends AbstractAnimatedDrawable implements AnimatableDrawable {

    public AnimatedDrawable(
            ScheduledExecutorService scheduledExecutorServiceForUiThread,
            AnimatedDrawableCachingBackend animatedDrawableBackend,
            AnimatedDrawableDiagnostics animatedDrawableDiagnostics,
            MonotonicClock monotonicClock) {
        super(scheduledExecutorServiceForUiThread,
                animatedDrawableBackend,
                animatedDrawableDiagnostics,
                monotonicClock);
    }

    @Override
    public ValueAnimator createValueAnimator(int maxDurationMs) {
        ValueAnimator animator = createValueAnimator();
        int repeatCount = Math.max((maxDurationMs / getAnimatedDrawableBackend().getDurationMs()), 1);
        animator.setRepeatCount(repeatCount);
        return animator;
    }

    @Override
    public ValueAnimator createValueAnimator() {
        int loopCount = getAnimatedDrawableBackend().getLoopCount();
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0, getDuration());
        animator.setDuration(getDuration());
        animator.setRepeatCount(
                loopCount != AnimatedImage.LOOP_COUNT_INFINITE ? loopCount : ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(createAnimatorUpdateListener());
        return animator;
    }

    @Override
    public ValueAnimator.AnimatorUpdateListener createAnimatorUpdateListener() {
        return new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setLevel((Integer) animation.getAnimatedValue());
            }
        };
    }

}
