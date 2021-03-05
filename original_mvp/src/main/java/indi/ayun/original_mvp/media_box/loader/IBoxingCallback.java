

package indi.ayun.original_mvp.media_box.loader;

/**
 * Simple callback only cares about success/fail.
 *
 * @author ChenSL
 */
public interface IBoxingCallback {

    /**
     * Successfully handle a task;
     */
    void onSuccess();

    /**
     * Error happened when running a task;
     */
    void onFail(Throwable t);
}
