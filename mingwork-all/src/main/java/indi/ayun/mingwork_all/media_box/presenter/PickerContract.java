

package indi.ayun.mingwork_all.media_box.presenter;

import android.content.ContentResolver;

import indi.ayun.mingwork_all.media_box.model.config.BoxingConfig;
import indi.ayun.mingwork_all.media_box.model.entity.AlbumEntity;
import indi.ayun.mingwork_all.media_box.model.entity.BaseMedia;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author ChenSL
 */
public interface PickerContract {

    /**
     * define the functions of the view, interacting with presenter
     */
    interface View {
        /**
         * set the presenter attaching to the view
         */
        void setPresenter(@NonNull Presenter presenter);

        /**
         * show a list  the {@link BaseMedia} in the view
         */
        void showMedia(@Nullable List<BaseMedia> medias, int allCount);

        /**
         * show all the {@link AlbumEntity} in the view
         */
        void showAlbum(@Nullable List<AlbumEntity> albums);

        /**
         * get the {@link ContentResolver} in the view
         */
        @NonNull
        ContentResolver getAppCr();

        /**
         * call when the view should be finished or the process is finished
         *
         * @param medias the selection of medias.
         */
        void onFinish(@NonNull List<BaseMedia> medias);

        /**
         * clear all the {@link BaseMedia} in the view
         */
        void clearMedia();

        /**
         * start crop the {@link BaseMedia} in the single media mode
         */
        void startCrop(@NonNull BaseMedia media, int requestCode);

        /**
         * set or update the config.
         *
         * @param config {@link BoxingConfig}
         */
        void setPickerConfig(BoxingConfig config);

    }

    /**
     * define the function of presenter, to control the module to load data and to tell view to displayRaw the data
     */
    interface Presenter {
        /**
         * load the specify data from {@link ContentResolver}
         *
         * @param page    the page need to load
         * @param albumId album albumId
         */
        void loadMedias(int page, String albumId);

        /**
         * load all the album from {@link ContentResolver}
         */
        void loadAlbums();

        /**
         * destroy the presenter and set the view null
         */
        void destroy();

        /**
         * has more data to load
         *
         * @return true, have more
         */
        boolean hasNextPage();

        boolean canLoadNextPage();

        /**
         * load next page
         */
        void onLoadNextPage();

        /**
         * Determine the selected allMedias according to mSelectedMedias
         *
         * @param allMedias      all medias
         * @param selectedMedias the medias to be selected
         */
        void checkSelectedMedia(List<BaseMedia> allMedias, List<BaseMedia> selectedMedias);
    }
}
