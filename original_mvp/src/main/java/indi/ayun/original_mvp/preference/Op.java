package indi.ayun.original_mvp.preference;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by ayun on 2015/4/13.
 */
public class Op {
    private Context context;
    private String name;
    public Op(Context context, String name){
        this.context=context;
        this.name=name;
    }

    public Set<String> getStringSet(String key, Set<String> value) {
        return getSharedPreferences().getStringSet(key,value);
    }

    public float getFloat(String key, float value) {
        return getSharedPreferences().getFloat(key,value);
    }

    public boolean getBoolean(String key, boolean value) {
        return getSharedPreferences().getBoolean(key, value);
    }

    public long getLong(String key, long value) {
        return getSharedPreferences().getLong(key, value);
    }

    public int getInt(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    public String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public void saveStringSet(String key, Set<String> value) {
        getSharedPreferences().edit().putStringSet(key,value).apply();
    }

    public void saveFloat(String key, float value) {
        getSharedPreferences().edit().putFloat(key,value).apply();
    }

    public void saveBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key,value).apply();
    }

    public void saveLong(String key, long value) {
        getSharedPreferences().edit().putLong(key,value).apply();
    }

    public void saveInt(String key, int value) {
        getSharedPreferences().edit().putInt(key,value).apply();
    }

    public void saveString(String key, String value) {
        getSharedPreferences().edit().putString(key,value).apply();
    }

    public void clear() {
        getSharedPreferences().edit().clear().apply();
    }

    public void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return  PreferenceUtil.getSharedPreference(context, name);
    }
}
