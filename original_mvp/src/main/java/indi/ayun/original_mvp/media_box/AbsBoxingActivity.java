

package indi.ayun.original_mvp.media_box;

import android.content.Intent;
import android.os.Bundle;

import indi.ayun.original_mvp.media_box.model.BoxingManager;
import indi.ayun.original_mvp.media_box.model.config.BoxingConfig;
import indi.ayun.original_mvp.media_box.model.entity.BaseMedia;
import indi.ayun.original_mvp.media_box.presenter.PickerPresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbsBoxingActivity extends AppCompatActivity implements Boxing.OnBoxingFinishListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AbsBoxingViewFragment view = onCreateBoxingView(getSelectedMedias(getIntent()));
        BoxingConfig pickerConfig = BoxingManager.getInstance().getBoxingConfig();
        view.setPresenter(new PickerPresenter(view));
        view.setPickerConfig(pickerConfig);
        Boxing.get().setupFragment(view, this);
    }

    private ArrayList<BaseMedia> getSelectedMedias(Intent intent) {
        return intent.getParcelableArrayListExtra(Boxing.EXTRA_SELECTED_MEDIA);
    }

    public BoxingConfig getBoxingConfig() {
        return BoxingManager.getInstance().getBoxingConfig();
    }

    @NonNull
    public abstract AbsBoxingViewFragment onCreateBoxingView(ArrayList<BaseMedia> medias);

}
