package indi.ayun.original_mvp.preference;

import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OpEntry {
    static final int OP_TYPE_GET = 1;//获取

    static final int OP_TYPE_PUT = 2;//增加

    static final int OP_TYPE_CLEAR = 3;//清除

    static final int OP_TYPE_REMOVE = 4;//转移

    static final int OP_TYPE_COMMIT = 5;//提交

    static final int OP_TYPE_APPLY = 6;//应用


    static final int VALUE_TYPE_STRING = 1;//值-string

    static final int VALUE_TYPE_INT = 2;//值-int

    static final int VALUE_TYPE_LONG = 3;//值-long

    static final int VALUE_TYPE_FLOAT = 4;//值-float

    static final int VALUE_TYPE_BOOLEAN = 5;//值-boolean

    static final int VALUE_TYPE_STRING_SET = 6;//值-set


    static final String KEY_KEY = "key_key";//key-钥匙

    static final String KEY_VALUE = "key_value";//key-值


    static final String KEY_VALUE_TYPE = "key_value_type";//key-值类型

    static final String KEY_OP_TYPE = "key_op_type";//key-op类型

    @NonNull
    private Bundle bundle;
    //初始化
    private OpEntry() {
        this.bundle = new Bundle();
    }
    //传入初始化
    public OpEntry(@NonNull Bundle bundle) {
        this.bundle = bundle;
    }
    //获取key
    public String getKey() {
        return bundle.getString(KEY_KEY, null);
    }
    //存储key
    public OpEntry setKey(String key) {
        bundle.putString(KEY_KEY, key);
        return this;
    }
    //获取值
    public int getValueType() {
        return bundle.getInt(KEY_VALUE_TYPE, 0);
    }
    //存储值
    public OpEntry setValueType(int valueType) {
        bundle.putInt(KEY_VALUE_TYPE, valueType);
        return this;
    }
    //获取op类型
    public int getOpType() {
        return bundle.getInt(KEY_OP_TYPE, 0);
    }
    //存储op类型
    public OpEntry setOpType(int opType) {
        bundle.putInt(KEY_OP_TYPE, opType);
        return this;
    }

    public String getStringValue(String defValue) {
        return bundle.getString(KEY_VALUE, defValue);
    }

    public OpEntry setStringValue(String value) {
        bundle.putInt(KEY_VALUE_TYPE, VALUE_TYPE_STRING);
        bundle.putString(KEY_VALUE, value);
        return this;
    }

    public int getIntValue(int defValue) {
        return bundle.getInt(KEY_VALUE, defValue);
    }

    public OpEntry setIntValue(int value) {
        bundle.putInt(KEY_VALUE_TYPE, VALUE_TYPE_INT);
        bundle.putInt(KEY_VALUE, value);
        return this;
    }

    public long getLongValue(long defValue) {
        return bundle.getLong(KEY_VALUE, defValue);
    }

    public OpEntry setLongValue(long value) {
        bundle.putInt(KEY_VALUE_TYPE, VALUE_TYPE_LONG);
        bundle.putLong(KEY_VALUE, value);
        return this;
    }

    public float getFloatValue(float defValue) {
        return bundle.getFloat(KEY_VALUE);
    }

    public OpEntry setFloatValue(float value) {
        bundle.putInt(KEY_VALUE_TYPE, VALUE_TYPE_FLOAT);
        bundle.putFloat(KEY_VALUE, value);
        return this;
    }


    public boolean getBooleanValue(boolean defValue) {
        return bundle.getBoolean(KEY_VALUE, defValue);
    }

    public OpEntry setBooleanValue(boolean value) {
        bundle.putInt(KEY_VALUE_TYPE, VALUE_TYPE_BOOLEAN);
        bundle.putBoolean(KEY_VALUE, value);
        return this;
    }

    public Set<String> getStringSet() {
        ArrayList<String> list = bundle.getStringArrayList(KEY_VALUE);
        return list == null ? null : new HashSet<>(list);
    }


    public Bundle getBundle() {
        return bundle;
    }

    public OpEntry setStringSettingsValue(Set<String> value) {
        bundle.putInt(KEY_VALUE_TYPE, VALUE_TYPE_STRING_SET);
        bundle.putStringArrayList(KEY_VALUE, value == null ? null : new ArrayList<>(value));
        return this;
    }


    static OpEntry obtainGetOperation(String key) {
        return new OpEntry()
                .setKey(key)
                .setOpType(OP_TYPE_GET);
    }

    static OpEntry obtainPutOperation(String key) {
        return new OpEntry()
                .setKey(key)
                .setOpType(OP_TYPE_PUT);
    }

    static OpEntry obtainRemoveOperation(String key) {
        return new OpEntry()
                .setKey(key)
                .setOpType(OP_TYPE_REMOVE);
    }

    static OpEntry obtainClear() {
        return new OpEntry()
                .setOpType(OP_TYPE_CLEAR);
    }
}
