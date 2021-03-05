package indi.ayun.original_mvp.retrofit2;

import indi.ayun.original_mvp.retrofit2.param.RequestParams;
import indi.ayun.original_mvp.retrofit2.param.RetrofitParam;

public class HttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10000L;
    private static volatile HttpUtils instance;

    public HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            Class var0 = HttpUtils.class;
            synchronized(HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }

        return instance;
    }

    public static RequestParams get() {
        return (RequestParams)(new RequestParams()).setMethod(HttpMethod.GET);
    }

    public static RequestParams post() {
        return (RequestParams)(new RequestParams()).setMethod(HttpMethod.POST);
    }

    public static RetrofitParam Retrofit() {
        return new RetrofitParam();
    }
}
