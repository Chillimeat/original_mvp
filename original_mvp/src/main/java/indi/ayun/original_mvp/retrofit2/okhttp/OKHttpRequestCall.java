package indi.ayun.original_mvp.retrofit2.okhttp;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import indi.ayun.original_mvp.retrofit2.param.RequestParams;
import indi.ayun.original_mvp.retrofit2.utils.Platform;
import indi.ayun.original_mvp.retrofit2.callback.Callback.ProgressDownCallback;
import indi.ayun.original_mvp.retrofit2.bean.KeyValue;
import indi.ayun.original_mvp.retrofit2.callback.Callback.CommonCallback;
import indi.ayun.original_mvp.retrofit2.callback.Callback.ProgressCallback;
import indi.ayun.original_mvp.retrofit2.callback.Callback.ProgressUpCallback;
import indi.ayun.original_mvp.retrofit2.param.BaseParams.Header;
import indi.ayun.original_mvp.retrofit2.utils.Exceptions;
import indi.ayun.original_mvp.retrofit2.utils.HttpTool;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class OKHttpRequestCall {
    private static volatile OkHttpClient okHttpClient;
    private static volatile Platform platform;
    private Retrofit retrofit;
    private RequestParams requestParams;

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((KeyManager[])null, new TrustManager[]{new OKHttpRequestCall.TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception var2) {
            ;
        }

        return sSLSocketFactory;
    }

    public OKHttpRequestCall(RequestParams requestParams) {
        this.requestParams = requestParams;
        if (okHttpClient == null) {
            Class var2 = OKHttpRequestCall.class;
            synchronized(OKHttpRequestCall.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
                    mBuilder.sslSocketFactory(createSSLSocketFactory());
                    mBuilder.hostnameVerifier(new OKHttpRequestCall.TrustAllHostnameVerifier());
                    okHttpClient = mBuilder.build();
                    platform = Platform.get();
                }
            }
        }

    }

    public void downLoad(ProgressDownCallback<Void> callback) {
        Call call = this.getCall((CommonCallback)null);
        (new FileDownloadTask(call, callback, platform)).start();
    }

    public InputStream downLoad() throws IOException {
        Call call = this.getCall((CommonCallback)null);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().byteStream();
        } else {
            //MLog.e("响应错误:" + response.code());
            throw new IOException(response.body().string());
        }
    }

    public <T> void call(final CommonCallback<T> callback) {
        Call call = this.getCall(callback);
        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                callback.onError(e, (String)null, (String)null);
            }

            public void onResponse(Call call, Response response) {
                try {
                    final T t = OKHttpRequestCall.this.handleResponse(response, callback.type);
                    OKHttpRequestCall.platform.execute(new Runnable() {
                        public void run() {
                            callback.onSuccess(t);
                        }
                    });
                } catch (final Exception var4) {
                    OKHttpRequestCall.platform.execute(new Runnable() {
                        public void run() {
                            callback.onError(var4, (String)null, (String)null);
                        }
                    });
                }

            }
        });
    }

    public <T> T call(Class<T> resultType) throws Exception {
        Call call = this.getCall((CommonCallback)null);
        Response response = call.execute();
        return (T) (resultType == Response.class ? response : this.handleResponse(response, resultType));
    }

    public <T> List<T> callList(Class<T> resultType) throws Exception {
        Call call = this.getCall((CommonCallback)null);
        Response response = call.execute();

        try {
            if (response.isSuccessful()) {
                String result = null;
                result = response.body().string();
                List<T> list = JSON.parseArray(result, resultType);
                return list;
            } else {
                Exceptions.RequestException("request fail code: %d   message: %s", new Object[]{response.code(), response.message()});
                return null;
            }
        } catch (IOException var6) {
            throw var6;
        }
    }

    private <T> T handleResponse(Response response, Type type) throws Exception {
        try {
            if (response.isSuccessful()) {
                String result = null;
                result = response.body().string();
                if (type == Object.class) {
                    return (T) result;
                }

                if (type == String.class) {
                    return (T) result;
                }

                if (type == InputStream.class) {
                    return (T) response.body().byteStream();
                }

                if (type == JSONObject.class) {
                    JSONObject jsonObject = null;

                    try {
                        jsonObject = JSON.parseObject(result);
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }

                    if (jsonObject != null) {
                        return (T) jsonObject;
                    }
                } else if (type == JSONArray.class) {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = JSON.parseArray(result);
                    } catch (Exception var7) {
                        var7.printStackTrace();
                    }

                    if (jsonArray != null) {
                        return (T) jsonArray;
                    }
                } else {
                    Object obj = null;

                    try {
                        obj = JSON.parseObject(result, type, new Feature[0]);
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                    if (obj != null) {
                        return (T) obj;
                    }
                }
            } else {
                Exceptions.RequestException("request fail code: %d   message: %s", new Object[]{response.code(), response.message()});
            }

            return null;
        } catch (IOException var9) {
            throw var9;
        }
    }

    private <T> Call getCall(CommonCallback<T> callback) {
        long readTimeOut = this.requestParams.getReadTimeOut();
        long writeTimeOut = this.requestParams.getWriteTimeOut();
        long connTimeOut = this.requestParams.getConnTimeOut();
        OkHttpClient clone = okHttpClient;
        Call call = null;
        if (readTimeOut > 0L || writeTimeOut > 0L || connTimeOut > 0L) {
            readTimeOut = readTimeOut > 0L ? readTimeOut : 10000L;
            writeTimeOut = writeTimeOut > 0L ? writeTimeOut : 10000L;
            connTimeOut = connTimeOut > 0L ? connTimeOut : 10000L;
            clone = okHttpClient.newBuilder().readTimeout(readTimeOut, TimeUnit.MILLISECONDS).writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).connectTimeout(connTimeOut, TimeUnit.MILLISECONDS).build();
        }

        call = clone.newCall(this.getRequest(callback));
        return call;
    }

    private Request getRequest(final CommonCallback callback) {
        new okhttp3.Request.Builder();
        okhttp3.Request.Builder builder = (new okhttp3.Request.Builder()).url(HttpTool.appendUrlParams(this.requestParams.getUri(), this.requestParams.getQueryStringParams(), this.requestParams.getCharset()));
        this.addHead(builder);
        switch(this.requestParams.getMethod()) {
            case POST:
                RequestBody requestBody = null;
                if (!TextUtils.isEmpty(this.requestParams.getBodyContent())) {
                    requestBody = RequestBody.create(this.requestParams.isJson() ? MediaType.parse("application/json") : null, this.requestParams.getBodyContent());
                } else if (!this.requestParams.isMultipart() && this.requestParams.getFileParams().size() <= 0) {
                    if (this.requestParams.getBodyParams().size() > 0) {
                        okhttp3.FormBody.Builder formBuilder = new okhttp3.FormBody.Builder();
                        this.addFormParam(formBuilder);
                        requestBody = formBuilder.build();
                    } else {
                        requestBody = RequestBody.create(this.requestParams.isJson() ? MediaType.parse("application/json") : null, "");
                    }
                } else {
                    okhttp3.MultipartBody.Builder mulBuilder = (new okhttp3.MultipartBody.Builder()).setType(MultipartBody.FORM);
                    this.addMulParam(mulBuilder);
                    requestBody = mulBuilder.build();
                }

                if (callback instanceof ProgressUpCallback || callback instanceof ProgressCallback) {
                    requestBody = ProgressHelper.addProgressRequestListener((RequestBody)requestBody, new ProgressRequestListener() {
                        public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                            if (callback instanceof ProgressUpCallback) {
                                ProgressUpCallback progressUpCallback = (ProgressUpCallback)callback;
                                progressUpCallback.onUpLoading(contentLength, bytesWritten, done);
                            } else if (callback instanceof ProgressCallback) {
                                ProgressCallback progressUpCallbackx = (ProgressCallback)callback;
                                progressUpCallbackx.onUpLoading(contentLength, bytesWritten, done);
                            }

                        }
                    });
                }

                builder.post((RequestBody)requestBody);
            case GET:
            default:
                return builder.build();
        }
    }

    private void addHead(okhttp3.Request.Builder builder) {
        Iterator var2 = this.requestParams.getHeaders().iterator();

        while(var2.hasNext()) {
            Header header = (Header)var2.next();
            if (header.setHeader) {
                builder.header(header.key, header.value.toString());
            } else {
                builder.addHeader(header.key, header.value.toString());
            }
        }

    }

    private void addFormParam(okhttp3.FormBody.Builder formBuilder) {
        Iterator var2 = this.requestParams.getBodyParams().iterator();

        while(var2.hasNext()) {
            KeyValue keyValue = (KeyValue)var2.next();
            formBuilder.add(keyValue.key, keyValue.value.toString());
        }

    }

    private void addMulParam(okhttp3.MultipartBody.Builder builder) {
        Iterator var2 = this.requestParams.getFileParams().iterator();

        while(var2.hasNext()) {
            KeyValue keyValue = (KeyValue)var2.next();
            if (keyValue.value instanceof File) {
                File file = (File)keyValue.value;
                //MLog.d(file.getName());
                builder.addFormDataPart(keyValue.key, file.getName(), RequestBody.create(MediaType.parse(HttpTool.getContentType(file)), file));
            } else if (keyValue.value instanceof byte[]) {
                builder.addFormDataPart(keyValue.key, (String)null, RequestBody.create(MediaType.parse(HttpTool.getContentType((File)null)), (byte[])((byte[])keyValue.value)));
            } else if (keyValue.value instanceof String) {
                builder.addPart(Headers.of(new String[]{"Content-Disposition", "form-data; name=\"" + keyValue.key + "\""}), RequestBody.create((MediaType)null, keyValue.value.toString()));
            }
        }

    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        private TrustAllHostnameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class TrustAllManager implements X509TrustManager {
        private TrustAllManager() {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    }
}
