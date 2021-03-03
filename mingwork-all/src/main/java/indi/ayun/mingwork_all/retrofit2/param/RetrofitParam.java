package indi.ayun.mingwork_all.retrofit2.param;

import indi.ayun.mingwork_all.retrofit2.okhttp.OKHttpRequestCall;
import indi.ayun.mingwork_all.retrofit2.okhttp.RetrofitCall;

public class RetrofitParam extends BaseParams<RetrofitParam> {
    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;
    private String baseUrl;

    public RetrofitParam() {
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public RetrofitParam baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitCall buildRetrofit() {
        return new RetrofitCall(this);
    }

    public OKHttpRequestCall buildOkhttp() {
        return null;
    }
}
