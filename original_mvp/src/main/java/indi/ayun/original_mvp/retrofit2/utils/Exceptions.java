package indi.ayun.original_mvp.retrofit2.utils;

public class Exceptions {
    public Exceptions() {
    }

    public static void RequestException(String msg, Object... params) throws RuntimeException {
        throw new RuntimeException(String.format(msg, params));
    }
}
