package indi.ayun.mingwork_all.retrofit2.bean;

public class KeyValue {
    public final String key;
    public final Object value;

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getValueStr() {
        return this.value == null ? null : this.value.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KeyValue keyValue = (KeyValue)o;
            return this.key == null ? keyValue.key == null : this.key.equals(keyValue.key);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public String toString() {
        return "KeyValue{key='" + this.key + '\'' + ", value=" + this.value + '}';
    }
}
