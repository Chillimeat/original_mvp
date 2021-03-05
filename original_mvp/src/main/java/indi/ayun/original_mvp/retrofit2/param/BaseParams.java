package indi.ayun.original_mvp.retrofit2.param;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import indi.ayun.original_mvp.retrofit2.HttpMethod;
import indi.ayun.original_mvp.retrofit2.bean.KeyValue;
import indi.ayun.original_mvp.retrofit2.okhttp.OKHttpRequestCall;
import indi.ayun.original_mvp.retrofit2.okhttp.RetrofitCall;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Interceptor;

public abstract class BaseParams<T extends BaseParams> {
    private String charset = "UTF-8";
    private HttpMethod method;
    private String bodyContent;
    private boolean multipart = false;
    private boolean json;
    protected List<Header> headers = new ArrayList();
    private final List<KeyValue> queryStringParams = new ArrayList();
    private final List<KeyValue> bodyParams = new ArrayList();
    private final List<KeyValue> fileParams = new ArrayList();
    private Interceptor interceptor;

    public BaseParams() {
    }

    public void setCharset(String charset) {
        if (!TextUtils.isEmpty(charset)) {
            this.charset = charset;
        }

    }

    public Interceptor getInterceptor() {
        return this.interceptor;
    }

    public T setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
        return (T) this;
    }

    public String getCharset() {
        return this.charset;
    }

    public T setMethod(HttpMethod method) {
        this.method = method;
        return (T) this;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public boolean isMultipart() {
        return this.multipart;
    }

    public T setMultipart(boolean multipart) {
        this.multipart = multipart;
        return (T) this;
    }

    public void clearParams() {
        this.queryStringParams.clear();
        this.bodyParams.clear();
        this.fileParams.clear();
        this.bodyContent = null;
    }

    public T setHeader(String name, String value) {
        BaseParams.Header header = new BaseParams.Header(name, value, true);
        Iterator it = this.headers.iterator();

        while(it.hasNext()) {
            KeyValue kv = (KeyValue)it.next();
            if (name.equals(kv.key)) {
                it.remove();
            }
        }

        this.headers.add(header);
        return (T) this;
    }

    public T addHeader(String name, String value) {
        this.headers.add(new BaseParams.Header(name, value, false));
        return (T) this;
    }

    public T setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
        return (T) this;
    }

    public T addParameter(String name, Object value) {
        if (value == null) {
            return (T) this;
        } else {
            Iterator var3;
            Object item;
            int len;
            int i;
            if (this.method != null && !HttpMethod.permitsRequestBody(this.method)) {
                if (!TextUtils.isEmpty(name)) {
                    if (value instanceof List) {
                        var3 = ((List)value).iterator();

                        while(var3.hasNext()) {
                            item = var3.next();
                            this.queryStringParams.add(new BaseParams.ArrayItem(name, item));
                        }
                    } else if (value.getClass().isArray()) {
                        len = Array.getLength(value);

                        for(i = 0; i < len; ++i) {
                            this.queryStringParams.add(new BaseParams.ArrayItem(name, Array.get(value, i)));
                        }
                    } else {
                        this.queryStringParams.add(new KeyValue(name, value));
                    }
                }
            } else if (!TextUtils.isEmpty(name)) {
                if (!(value instanceof File) && !(value instanceof InputStream) && !(value instanceof byte[])) {
                    if (value instanceof List) {
                        var3 = ((List)value).iterator();

                        while(var3.hasNext()) {
                            item = var3.next();
                            this.bodyParams.add(new BaseParams.ArrayItem(name, item));
                        }
                    } else if (value.getClass().isArray()) {
                        len = Array.getLength(value);

                        for(i = 0; i < len; ++i) {
                            this.bodyParams.add(new BaseParams.ArrayItem(name, Array.get(value, i)));
                        }
                    } else {
                        this.bodyParams.add(new KeyValue(name, value));
                    }
                } else {
                    this.fileParams.add(new KeyValue(name, value));
                }
            } else {
                this.bodyContent = value.toString();
            }

            return (T) this;
        }
    }

    public T addQueryStringParameter(String name, String value) {
        if (!TextUtils.isEmpty(name)) {
            this.queryStringParams.add(new KeyValue(name, value));
        }

        return (T) this;
    }

    public T addBodyParameter(String name, String value) {
        if (!TextUtils.isEmpty(name)) {
            this.bodyParams.add(new KeyValue(name, value));
        } else {
            this.bodyContent = value;
        }

        return (T) this;
    }

    public T addBodyParameter(String name, File value) {
        this.fileParams.add(new KeyValue(name, value));
        return (T) this;
    }

    public T addBodyParameter(String name, Object value) {
        this.fileParams.add(new KeyValue(name, value));
        return (T) this;
    }

    public T addJsonBodyParameter(String name, Object value) {
        String string = JSON.toJSONString(value);
        if (!TextUtils.isEmpty(name)) {
            this.bodyParams.add(new KeyValue(name, string));
        } else {
            this.bodyContent = string;
        }

        this.fileParams.add(new KeyValue(name, value));
        return (T) this;
    }

    public List<BaseParams.Header> getHeaders() {
        return new ArrayList(this.headers);
    }

    public List<KeyValue> getQueryStringParams() {
        this.checkBodyParams();
        return new ArrayList(this.queryStringParams);
    }

    public String getBodyContent() {
        return this.bodyContent;
    }

    public boolean isJson() {
        return this.json;
    }

    public T setJson(boolean json) {
        this.json = json;
        return (T) this;
    }

    public List<KeyValue> getBodyParams() {
        return new ArrayList(this.bodyParams);
    }

    public List<KeyValue> getFileParams() {
        this.checkBodyParams();
        return new ArrayList(this.fileParams);
    }

    public List<KeyValue> getStringParams() {
        List<KeyValue> result = new ArrayList(this.queryStringParams.size() + this.bodyParams.size());
        result.addAll(this.queryStringParams);
        result.addAll(this.bodyParams);
        return result;
    }

    public String getStringParameter(String name) {
        Iterator var2 = this.queryStringParams.iterator();

        KeyValue kv;
        do {
            if (!var2.hasNext()) {
                var2 = this.bodyParams.iterator();

                do {
                    if (!var2.hasNext()) {
                        return null;
                    }

                    kv = (KeyValue)var2.next();
                    if (name == null && kv.key == null) {
                        return kv.getValueStr();
                    }
                } while(name == null || !name.equals(kv.key));

                return kv.getValueStr();
            }

            kv = (KeyValue)var2.next();
            if (name == null && kv.key == null) {
                return kv.getValueStr();
            }
        } while(name == null || !name.equals(kv.key));

        return kv.getValueStr();
    }

    public List<KeyValue> getParams(String name) {
        List<KeyValue> result = new ArrayList();
        Iterator var3 = this.queryStringParams.iterator();

        while(true) {
            KeyValue kv;
            while(var3.hasNext()) {
                kv = (KeyValue)var3.next();
                if (name == null && kv.key == null) {
                    result.add(kv);
                } else if (name != null && name.equals(kv.key)) {
                    result.add(kv);
                }
            }

            var3 = this.bodyParams.iterator();

            while(true) {
                while(var3.hasNext()) {
                    kv = (KeyValue)var3.next();
                    if (name == null && kv.key == null) {
                        result.add(kv);
                    } else if (name != null && name.equals(kv.key)) {
                        result.add(kv);
                    }
                }

                var3 = this.fileParams.iterator();

                while(true) {
                    while(var3.hasNext()) {
                        kv = (KeyValue)var3.next();
                        if (name == null && kv.key == null) {
                            result.add(kv);
                        } else if (name != null && name.equals(kv.key)) {
                            result.add(kv);
                        }
                    }

                    return result;
                }
            }
        }
    }

    public abstract OKHttpRequestCall buildOkhttp();

    public abstract RetrofitCall buildRetrofit();

    protected void checkBodyParams() {
        if (!TextUtils.isEmpty(this.bodyContent)) {
            this.bodyParams.clear();
            this.fileParams.clear();
        }

        if (!this.bodyParams.isEmpty() && (!HttpMethod.permitsRequestBody(this.method) || !TextUtils.isEmpty(this.bodyContent))) {
            this.queryStringParams.addAll(this.bodyParams);
            this.bodyParams.clear();
            this.fileParams.clear();
        }

        if (!this.bodyParams.isEmpty() && (this.multipart || this.fileParams.size() > 0)) {
            this.fileParams.addAll(this.bodyParams);
            this.bodyParams.clear();
        }

    }

    public static final class Header extends KeyValue {
        public final boolean setHeader;

        public Header(String key, String value, boolean setHeader) {
            super(key, value);
            this.setHeader = setHeader;
        }
    }

    public static final class ArrayItem extends KeyValue {
        public ArrayItem(String key, Object value) {
            super(key, value);
        }
    }
}
