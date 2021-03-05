package indi.ayun.original_mvp.retrofit2.okhttp;

import okhttp3.RequestBody;

public class ProgressHelper {
    public ProgressHelper() {
    }

    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody, ProgressRequestListener progressRequestListener) {
        return new ProgressRequestBody(requestBody, progressRequestListener);
    }
}

