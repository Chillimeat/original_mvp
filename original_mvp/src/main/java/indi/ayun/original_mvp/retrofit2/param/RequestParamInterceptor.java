package indi.ayun.original_mvp.retrofit2.param;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.preference.OpCredential;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestParamInterceptor implements Interceptor {
    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> bodyParams = new HashMap<>();

    public RequestParamInterceptor() {

    }

    public RequestParamInterceptor addHead(String key, String value) {
        MLog.d(value);
        headers.put(key, value);
        MLog.d("RequestParamInterceptor:"+headers.get("ApiAuth"));
        return this;
    }

    public RequestParamInterceptor addBodyParam(String key, String value) {
        bodyParams.put(key, value);
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 新的参数
        RequestBody body = oldRequest.body();
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int i = 0; i < formBody.size(); i++) {
                formBuilder.add(formBody.name(i), formBody.value(i));
            }
        }
        for (Map.Entry<String, String> stringStringEntry : bodyParams.entrySet()) {
            formBuilder.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }

        Request.Builder builder = oldRequest.newBuilder().method(oldRequest.method(), formBuilder.build());
        for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
            MLog.d("RequestParamInterceptor:"+stringStringEntry.getKey()+";"+stringStringEntry.getValue());
            MLog.d("RequestParamInterceptor_ApiAuth:"+headers.get("ApiAuth"));
           //builder.addHeader("ApiAuth", headers.get("ApiAuth"));
//            FIXME:这里的问题是，不能获取即时更新的headers
            builder.addHeader("ApiAuth", OpCredential.getInstance().getUserToken());
        }
        return chain.proceed(builder.build());
    }

}
