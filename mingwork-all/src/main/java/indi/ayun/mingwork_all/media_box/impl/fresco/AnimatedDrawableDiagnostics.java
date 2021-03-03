package indi.ayun.mingwork_all.media_box.impl.fresco;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Diagnostics interface for {@link AnimatedDrawable}.
 */
public interface AnimatedDrawableDiagnostics {

    /**
     * Sets the backend that the {@link AnimatedDrawable} is using.
     *
     * @param animatedDrawableBackend the backend
     */
    void setBackend(AnimatedDrawableCachingBackend animatedDrawableBackend);


    void onStartMethodBegin();


    void onStartMethodEnd();


    void onNextFrameMethodBegin();


    void onNextFrameMethodEnd();

    /**
     * Increments the number of dropped frames for stats purposes.
     *
     * @param droppedFrames the number of dropped frames
     */
    void incrementDroppedFrames(int droppedFrames);

    /**
     * Increments the number of drawn frames for stats purposes.
     *
     * @param drawnFrames the number of drawn frames
     */
    void incrementDrawnFrames(int drawnFrames);

    /**
     * Called when the {@link AnimatedDrawable#draw} method begins.
     */
    void onDrawMethodBegin();

    /**
     * Called when the {@link AnimatedDrawable#draw} method emds.
     */
    void onDrawMethodEnd();

    /**
     * Allows the diagnostics code to draw an overlay that may be useful for debugging.
     *
     * @param canvas the canvas to draw to
     * @param destRect the rectangle bounds to draw to
     */
    void drawDebugOverlay(Canvas canvas, Rect destRect);
}
