package indi.ayun.original_mvp.media_box.impl.fresco;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.os.Build;

/**
 * An interface for animatable drawables that can be asked to construct a value animator.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public interface AnimatableDrawable extends Animatable {

    /**
     * An animator that will animate the drawable directly. The loop count and duration will
     * be determined by metadata in the original image. Update listener is attached automatically.
     *
     * @return a new animator
     */
    ValueAnimator createValueAnimator();

    /**
     * An animator that will animate the drawable directly. The loop count will be set based on
     * the specified duration. Update listener is attached automatically.
     *
     * @param maxDurationMs maximum duration animate
     * @return a new animator
     */
    ValueAnimator createValueAnimator(int maxDurationMs);

    /**
     * Creates an animator update listener that will animate the drawable directly. This is useful
     * when the drawable needs to be animated by an existing value animator.
     * @return a new update listener
     */
    ValueAnimator.AnimatorUpdateListener createAnimatorUpdateListener();
}
