
package indi.ayun.mingwork_all.media_box;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import indi.ayun.mingwork_all.media_box.model.BoxingManager;
import indi.ayun.mingwork_all.media_box.model.config.BoxingConfig;
import indi.ayun.mingwork_all.media_box.model.entity.BaseMedia;
import indi.ayun.mingwork_all.media_box.presenter.PickerPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Boxing {
    public static final String EXTRA_SELECTED_MEDIA = "com.example.ayun.newproject.common.boxing.Boxing.selected_media";
    public static final String EXTRA_ALBUM_ID = "com.example.ayun.newproject.common.boxing.Boxing.album_id";

    static final String EXTRA_CONFIG = "com.example.ayun.newproject.common.boxing.Boxing.config";
    static final String EXTRA_RESULT = "com.example.ayun.newproject.common.boxing.Boxing.result";
    static final String EXTRA_START_POS = "com.example.ayun.newproject.common.boxing.Boxing.start_pos";

    private Intent mIntent;

    private Boxing(BoxingConfig config) {
        BoxingManager.getInstance().setBoxingConfig(config);
        this.mIntent = new Intent();
    }

    /**
     * get the media result.
     */
    @Nullable
    public static ArrayList<BaseMedia> getResult(Intent data) {
        if (data != null) {
            return data.getParcelableArrayListExtra(EXTRA_RESULT);
        }
        return null;
    }

    /**
     * call {@link #of(BoxingConfig)} first to specify the mode otherwise {@link BoxingConfig.Mode#MULTI_IMG} is used.<br/>
     */
    public static Boxing get() {
        BoxingConfig config = BoxingManager.getInstance().getBoxingConfig();
        if (config == null) {
            config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG).needGif();
            BoxingManager.getInstance().setBoxingConfig(config);
        }
        return new Boxing(config);
    }

    /**
     * create a boxing entry.
     *
     * @param config {@link BoxingConfig}
     */
    public static Boxing of(BoxingConfig config) {
        return new Boxing(config);
    }

    /**
     * create a boxing entry.
     *
     * @param mode {@link BoxingConfig.Mode}
     */
    public static Boxing of(BoxingConfig.Mode mode) {
        return new Boxing(new BoxingConfig(mode));
    }

    /**
     * create a boxing entry. use {@link BoxingConfig.Mode#MULTI_IMG}.
     */
    public static Boxing of() {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG).needGif();
        return new Boxing(config);
    }

    /**
     * get the intent build by boxing after call {@link #withIntent}.
     */
    public Intent getIntent() {
        return mIntent;
    }

    /**
     * same as {@link Intent#setClass(Context, Class)}
     */
    public Boxing withIntent(Context context, Class<?> cls) {
        return withIntent(context, cls, null);
    }

    /**
     * {@link Intent#setClass(Context, Class)} with input medias.
     */
    public Boxing withIntent(Context context, Class<?> cls, ArrayList<? extends BaseMedia> selectedMedias) {
        mIntent.setClass(context, cls);
        if (selectedMedias != null && !selectedMedias.isEmpty()) {
            mIntent.putExtra(EXTRA_SELECTED_MEDIA, selectedMedias);
        }
        return this;
    }

    /**
     * use to start image viewer.
     *
     * @param medias selected medias.
     * @param pos    the start position.
     */
    public Boxing withIntent(Context context, Class<?> cls, ArrayList<? extends BaseMedia> medias, int pos) {
        withIntent(context, cls, medias, pos, "");
        return this;
    }


    /**
     * use to start image viewer.
     *
     * @param medias  selected medias.
     * @param pos     the start position.
     * @param albumId the specify album id.
     */
    public Boxing withIntent(Context context, Class<?> cls, ArrayList<? extends BaseMedia> medias, int pos, String albumId) {
        mIntent.setClass(context, cls);
        if (medias != null && !medias.isEmpty()) {
            mIntent.putExtra(EXTRA_SELECTED_MEDIA, medias);
        }
        if (pos >= 0) {
            mIntent.putExtra(EXTRA_START_POS, pos);
        }
        if (albumId != null) {
            mIntent.putExtra(EXTRA_ALBUM_ID, albumId);
        }
        return this;
    }


    /**
     * same as {@link Activity#startActivity(Intent)}
     */
    public void start(@NonNull Activity activity) {
        activity.startActivity(mIntent);
    }

    /**
     * use to start raw image viewer.
     *
     * @param viewMode {@link BoxingConfig.ViewMode}
     */
    public void start(@NonNull Activity activity, BoxingConfig.ViewMode viewMode) {
        BoxingManager.getInstance().getBoxingConfig().withViewer(viewMode);
        activity.startActivity(mIntent);
    }

    /**
     * same as {@link Activity#startActivityForResult(Intent, int, Bundle)}
     */
    public void start(@NonNull Activity activity, int requestCode) {
        activity.startActivityForResult(mIntent, requestCode);
    }

    /**
     * same as {@link Fragment#startActivityForResult(Intent, int, Bundle)}
     */
    public void start(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(mIntent, requestCode);
    }

    /**
     * use to start raw image viewer.
     *
     * @param viewMode {@link BoxingConfig.ViewMode}
     */
    public void start(@NonNull Fragment fragment, int requestCode, BoxingConfig.ViewMode viewMode) {
        BoxingManager.getInstance().getBoxingConfig().withViewer(viewMode);
        fragment.startActivityForResult(mIntent, requestCode);
    }

    /**
     * same as {@link android.app.Fragment#startActivityForResult(Intent, int, Bundle)}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void start(@NonNull android.app.Fragment fragment, int requestCode) {
        fragment.startActivityForResult(mIntent, requestCode);
    }

    /**
     * use to start raw image viewer.
     *
     * @param viewMode {@link BoxingConfig.ViewMode}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void start(@NonNull android.app.Fragment fragment, int requestCode, BoxingConfig.ViewMode viewMode) {
        BoxingManager.getInstance().getBoxingConfig().withViewer(viewMode);
        fragment.startActivityForResult(mIntent, requestCode);
    }

    /**
     * set up a subclass of {@link AbsBoxingViewFragment} without a {@link AbsBoxingActivity}.
     *
     * @param fragment         subclass of {@link AbsBoxingViewFragment}
     * @param onFinishListener a listener fo media result
     */
    public void setupFragment(@NonNull AbsBoxingViewFragment fragment, OnBoxingFinishListener onFinishListener) {
        fragment.setPresenter(new PickerPresenter(fragment));
        fragment.setOnFinishListener(onFinishListener);
    }

    /**
     * work with a subclass of {@link AbsBoxingViewFragment} without a {@link AbsBoxingActivity}.
     */
    public interface OnBoxingFinishListener {


        void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias);
    }

}
