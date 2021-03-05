package indi.ayun.original_mvp.media_box.impl.fresco;

public class AnimatedDrawableFrameInfo {
    /**
     * How to dispose of the current frame before rendering the next frame.
     */
    public enum DisposalMethod {

        /** Do not dipose the frame. Leave as-is. */
        DISPOSE_DO_NOT,

        /** Dispose to the background color */
        DISPOSE_TO_BACKGROUND,

        /** Dispose to the previous frame */
        DISPOSE_TO_PREVIOUS
    }

    /**
     * Indicates how transparent pixels of the current frame are blended
     * with those of the previous canvas.
     */
    public enum BlendOperation {
        /** Blend **/
        BLEND_WITH_PREVIOUS,
        /** Do not blend **/
        NO_BLEND,
    }

    public final int frameNumber;
    public final int xOffset;
    public final int yOffset;
    public final int width;
    public final int height;
    public final BlendOperation blendOperation;
    public final DisposalMethod disposalMethod;

    public AnimatedDrawableFrameInfo(
            int frameNumber,
            int xOffset,
            int yOffset,
            int width,
            int height,
            BlendOperation blendOperation,
            DisposalMethod disposalMethod) {
        this.frameNumber = frameNumber;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.blendOperation = blendOperation;
        this.disposalMethod = disposalMethod;
    }
}
