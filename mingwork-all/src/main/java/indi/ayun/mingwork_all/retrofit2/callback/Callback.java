package indi.ayun.mingwork_all.retrofit2.callback;


import indi.ayun.mingwork_all.retrofit2.utils.ClassTypeReflect;

import java.lang.reflect.Type;

public interface Callback {
    //
    public abstract static class ProgressCallback<T> extends Callback.ProgressDownCallback<T> {
        public ProgressCallback() {

        }

        public abstract void onUpLoading(long var1, long var3, boolean var5);
    }

    public abstract static class ProgressUpCallback<T> extends Callback.CommonCallback<T> {
        public ProgressUpCallback() {
        }

        public abstract void onUpLoading(long var1, long var3, boolean var5);
    }

    public abstract static class ProgressDownCallback<T> extends Callback.CommonCallback<T> {
        public ProgressDownCallback() {
        }

        public abstract void onDownLoading(long var1, long var3, boolean var5);
    }

    public abstract static class CommonCallback<T> {
        public Type type = ClassTypeReflect.getModelClazz(this.getClass());

        public CommonCallback() {
        }

        public abstract void onSuccess(T var1);

        public abstract void onError(Throwable var1, String var2, String var3);
    }
}

