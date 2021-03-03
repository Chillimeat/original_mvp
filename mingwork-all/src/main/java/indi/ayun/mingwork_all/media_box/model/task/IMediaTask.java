
package indi.ayun.mingwork_all.media_box.model.task;

import android.content.ContentResolver;

import indi.ayun.mingwork_all.media_box.model.callback.IMediaTaskCallback;
import indi.ayun.mingwork_all.media_box.model.entity.BaseMedia;


/**
 * The interface to load {@link BaseMedia}.
 *
 * @author ChenSL
 */
public interface IMediaTask<T extends BaseMedia> {
    int PAGE_LIMIT = 1000;

    void load(ContentResolver cr, int page, String id, IMediaTaskCallback<T> callback);

}
