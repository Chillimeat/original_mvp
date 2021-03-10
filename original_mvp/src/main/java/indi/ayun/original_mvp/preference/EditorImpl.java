package indi.ayun.original_mvp.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Set;

public class EditorImpl implements SharedPreferences.Editor {
    private ArrayList<OpEntry> mModified = new ArrayList<>();

    private Context ctx;
    private String preferName;

    public EditorImpl(Context ctx, String name) {
        this.ctx = ctx.getApplicationContext();
        this.preferName = name;
    }

    @Override
    public SharedPreferences.Editor putString(String key, @Nullable String value) {
        OpEntry entry = OpEntry.obtainPutOperation(key).setStringValue(value);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, @Nullable Set<String> values) {
        OpEntry entry = OpEntry.obtainPutOperation(key).setStringSettingsValue(values);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        OpEntry entry = OpEntry.obtainPutOperation(key).setIntValue(value);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        OpEntry entry = OpEntry.obtainPutOperation(key).setLongValue(value);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        OpEntry entry = OpEntry.obtainPutOperation(key).setFloatValue(value);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        OpEntry entry = OpEntry.obtainPutOperation(key).setBooleanValue(value);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        OpEntry entry = OpEntry.obtainRemoveOperation(key);
        return addOps(entry);
    }

    @Override
    public SharedPreferences.Editor clear() {
        return addOps(OpEntry.obtainClear());
    }

    @Override
    public boolean commit() {
        Bundle input = new Bundle();
        input.putParcelableArrayList(PreferenceUtil.KEY_VALUES, convertBundleList());
        input.putInt(OpEntry.KEY_OP_TYPE, OpEntry.OP_TYPE_COMMIT);
        Bundle res = null;
        try {
            res = ctx.getContentResolver().call(PreferenceUtil.URI, PreferenceUtil.METHOD_EIDIT_VALUE, preferName, input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res == null) {
            return false;
        }
        return res.getBoolean(PreferenceUtil.KEY_VALUES, false);
    }

    @Override
    public void apply() {
        Bundle intput = new Bundle();
        intput.putParcelableArrayList(PreferenceUtil.KEY_VALUES, convertBundleList());
        intput.putInt(OpEntry.KEY_OP_TYPE, OpEntry.OP_TYPE_APPLY);
        try {
            ctx.getContentResolver().call(PreferenceUtil.URI, PreferenceUtil.METHOD_EIDIT_VALUE, preferName, intput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SharedPreferences.Editor addOps(OpEntry op) {
        synchronized (this) {
            mModified.add(op);
            return this;
        }
    }

    private ArrayList<Bundle> convertBundleList() {
        synchronized (this) {
            ArrayList<Bundle> bundleList = new ArrayList<>(mModified.size());
            for (OpEntry entry : mModified) {
                bundleList.add(entry.getBundle());
            }
            return bundleList;
        }
    }
}
