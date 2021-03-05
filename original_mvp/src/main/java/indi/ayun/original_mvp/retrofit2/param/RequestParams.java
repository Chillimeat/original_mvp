package indi.ayun.original_mvp.retrofit2.param;

import indi.ayun.original_mvp.retrofit2.okhttp.OKHttpRequestCall;
import indi.ayun.original_mvp.retrofit2.okhttp.RetrofitCall;

public class RequestParams extends BaseParams<RequestParams> {
    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;
    private String uri;
    private String saveFilePath;

    public RequestParams() {
    }

    public String getUri() {
        return this.uri;
    }

    public RequestParams url(String uri) {
        this.uri = uri;
        return this;
    }

    public String getSaveFilePath() {
        return this.saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public Long getReadTimeOut() {
        return this.readTimeOut;
    }

    public Long getWriteTimeOut() {
        return this.writeTimeOut;
    }

    public Long getConnTimeOut() {
        return this.connTimeOut;
    }

    public OKHttpRequestCall buildOkhttp() {
        return new OKHttpRequestCall(this);
    }

    public RetrofitCall buildRetrofit() {
        return null;
    }
}
