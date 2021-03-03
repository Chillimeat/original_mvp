package indi.ayun.mingwork_all.media_box.impl;

public class BoxingUcrop
//        implements IBoxingCrop
{

//    @Override
//    public void onStartCrop(Context context, Fragment fragment, @NonNull BoxingCropOption cropConfig,
//                            @NonNull String path, int requestCode) {
//        Uri uri = new Uri.Builder()
//                .scheme("file")
//                .appendPath(path)
//                .build();
//        UCrop.Options crop = new UCrop.Options();
//        // do not copy exif information to crop pictures
//        // because png do not have exif and png is not Distinguishable
//        crop.setCompressionFormat(Bitmap.CompressFormat.PNG);
//        crop.withMaxResultSize(cropConfig.getMaxWidth(), cropConfig.getMaxHeight());
//        crop.withAspectRatio(cropConfig.getAspectRatioX(), cropConfig.getAspectRatioY());
//
//        UCrop.of(uri, cropConfig.getDestination())
//                .withOptions(crop)
//                .start(context, fragment, requestCode);
//    }
//
//    @Override
//    public Uri onCropFinish(int resultCode, Intent data) {
//        if (data == null) {
//            return null;
//        }
//        Throwable throwable = UCrop.getError(data);
//        if (throwable != null) {
//            return null;
//        }
//        return UCrop.getOutput(data);
//    }
}
