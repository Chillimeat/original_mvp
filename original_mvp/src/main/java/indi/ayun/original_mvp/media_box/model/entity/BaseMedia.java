

package indi.ayun.original_mvp.media_box.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import indi.ayun.original_mvp.base.BaseBean;


/**
 * The base entity for media.
 *
 * @author ChenSL
 */
public abstract class BaseMedia extends BaseBean implements Parcelable {
    @Override
    protected Class onClass() {
        return this.getClass();
    }

    protected enum TYPE {
        IMAGE, VIDEO
    }

    protected String mPath;
    protected String mId;
    protected String mSize;

    public BaseMedia() {
    }

    public BaseMedia(String id, String path) {
        mId = id;
        mPath = path;
    }

    public abstract TYPE getType();

    public String getId() {
        return mId;
    }

    public long getSize() {
        try {
            long result = Long.parseLong(mSize);
            return result > 0 ? result : 0;
        }catch (NumberFormatException size) {
            return 0;
        }
    }

    public void setId(String id) {
        mId = id;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getPath(){
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPath);
        dest.writeString(this.mId);
        dest.writeString(this.mSize);
    }

    protected BaseMedia(Parcel in) {
        this.mPath = in.readString();
        this.mId = in.readString();
        this.mSize = in.readString();
    }

}
