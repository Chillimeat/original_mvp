package indi.ayun.original_mvp.retrofit2.okhttp;

import indi.ayun.original_mvp.retrofit2.annotation.BodyType;
import indi.ayun.original_mvp.retrofit2.param.RetrofitParam;
import indi.ayun.original_mvp.retrofit2.utils.Exceptions;
import indi.ayun.original_mvp.retrofit2.utils.Platform;
import indi.ayun.original_mvp.retrofit2.callback.Callback.CommonCallback;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitCall {
    public static boolean isTest;
    private static volatile OkHttpClient okHttpClient;
    private static volatile Platform platform;
    private Retrofit retrofit;
    private RetrofitParam retrofitParam;

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((KeyManager[])null, new TrustManager[]{new RetrofitCall.TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception var2) {
            ;
        }

        return sSLSocketFactory;
    }

    public RetrofitCall(RetrofitParam retrofitParam) {
        this.retrofitParam = retrofitParam;
        if (okHttpClient == null) {
            Class var2 = OKHttpRequestCall.class;
            synchronized(OKHttpRequestCall.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
                    mBuilder.sslSocketFactory(createSSLSocketFactory());
                    mBuilder.hostnameVerifier(new RetrofitCall.TrustAllHostnameVerifier());
                    okHttpClient = mBuilder.build();
                    platform = Platform.get();
                }
            }
        }

    }

    public <T> T conver(Class<T> monthClass) {
        BodyType bodyType = (BodyType)monthClass.getAnnotation(BodyType.class);
        GsonConverterFactory converterFactory = null;
        if (bodyType != null) {
            converterFactory = GsonConverterFactory.create((new GsonBuilder()).setDateFormat(bodyType.bodyDateFormat()).create());
        } else {
            converterFactory = GsonConverterFactory.create();
        }

        if (this.retrofitParam.getInterceptor() != null && okHttpClient.interceptors().size() == 0) {
            okHttpClient = okHttpClient.newBuilder().addInterceptor(this.retrofitParam.getInterceptor()).build();
        }

        Retrofit retrofit = (new retrofit2.Retrofit.Builder()).baseUrl(this.retrofitParam.getBaseUrl()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(converterFactory).client(okHttpClient).build();
        return retrofit.create(monthClass);
    }

    public <T> void call(Call<T> call, final CommonCallback<T> callback) {
        if (isTest) {
            try {
                callback.onSuccess(this.call(call));
            } catch (Exception var4) {
                callback.onError(var4, (String)null, (String)null);
            }
        } else {
            call.enqueue(new Callback<T>() {
                public void onResponse(Call<T> call, Response<T> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError((Throwable)null, response.code() + "", response.message());
                    }

                }

                public void onFailure(Call<T> call, Throwable t) {
                    callback.onError(t, (String)null, (String)null);
                }
            });
        }

    }


    public <T> T call(Call<T> call) throws Exception {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Exceptions.RequestException("request fail code: %d   message: %s", new Object[]{response.code(), response.message()});
                return null;
            }
        } catch (IOException var3) {
            throw var3;
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
