package indi.ayun.original_mvp.media_box.impl.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import indi.ayun.original_mvp.R;

import indi.ayun.original_mvp.media_box.model.BoxingManager;
import indi.ayun.original_mvp.media_box.model.config.BoxingConfig;
import indi.ayun.original_mvp.media_box.model.config.BoxingCropOption;
import indi.ayun.original_mvp.media_box.model.entity.BaseMedia;
import indi.ayun.original_mvp.media_box.utils.BoxingFileHelper;

import java.util.List;
import java.util.Locale;

public class IntentFilterActivity extends BoxingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // in DCIM/bili/boxing
        String cropPath = BoxingFileHelper.getBoxingPathInDCIM();
        if (TextUtils.isEmpty(cropPath)) {
            Toast.makeText(getApplicationContext(), R.string.boxing_storage_deny, Toast.LENGTH_SHORT).show();
            return;
        }
        Uri destUri = new Uri.Builder()
                .scheme("file")
                .appendPath(cropPath)
                .appendPath(String.format(Locale.US, "%s.jpg", System.currentTimeMillis()))
                .build();
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).needCamera(R.drawable.ic_boxing_camera_white).withCropOption(new BoxingCropOption(destUri));
        BoxingManager.getInstance().setBoxingConfig(config);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias) {
        if (medias != null && medias.size() > 0) {
            intent.setData(Uri.parse(medias.get(0).getPath()));
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, null);
        }
        finish();
    }
}


