

package indi.ayun.mingwork_all.media_box.model.callback;



import indi.ayun.mingwork_all.media_box.model.entity.BaseMedia;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * A callback to load {@link BaseMedia}.
 *
 * @author ChenSL
 */
public interface IMediaTaskCallback<T extends BaseMedia> {
    /**
     * get a page of medias in a album
     *
     * @param medias page of medias
     * @param count  the count for the photo in album
     */
    void postMedia(@Nullable List<T> medias, int count);

    /**
     * judge the path needing filer
     *
     * @param path photo path
     * @return true:be filter
     */
    boolean needFilter(String path);
}
