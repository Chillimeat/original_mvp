

package indi.ayun.mingwork_all.media_box.model.entity;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.List;

import indi.ayun.mingwork_all.base.BaseBean;


/**
 * An entity for album.
 *
 * @author ChenSL
 */
public class AlbumEntity extends BaseBean implements Parcelable {
    @Override
    protected Class onClass() {
        return this.getClass();
    }
    public static final String DEFAULT_NAME = "";

    public int mCount;
    public boolean mIsSelected;

    public String mBucketId;
    public String mBucketName;
    public List<BaseMedia> mImageList;

    public AlbumEntity() {
        mCount = 0;
        mImageList = new ArrayList<>();
        mIsSelected = false;
    }

    public static AlbumEntity createDefaultAlbum() {
        AlbumEntity result = new AlbumEntity();
        result.mBucketId = DEFAULT_NAME;
        result.mIsSelected = true;
        return result;
    }

    public boolean hasImages() {
        return mImageList != null && mImageList.size() > 0;
    }

    @Override
    public String toString() {
        return "AlbumEntity{" +
                "mCount=" + mCount +
                ", mBucketName='" + mBucketName + '\'' +
                ", mImageList=" + mImageList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBucketId);
        dest.writeInt(this.mCount);
        dest.writeString(this.mBucketName);
        dest.writeList(this.mImageList);
        dest.writeByte(this.mIsSelected ? (byte) 1 : (byte) 0);
    }

    protected AlbumEntity(Parcel in) {
        this.mBucketId = in.readString();
        this.mCount = in.readInt();
        this.mBucketName = in.readString();
        this.mImageList = new ArrayList<>();
        in.readList(this.mImageList, BaseMedia.class.getClassLoader());
        this.mIsSelected = in.readByte() != 0;
    }

    public static final Creator<AlbumEntity> CREATOR = new Creator<AlbumEntity>() {
        @Override
        public AlbumEntity createFromParcel(Parcel source) {
            return new AlbumEntity(source);
        }

        @Override
        public AlbumEntity[] newArray(int size) {
            return new AlbumEntity[size];
        }
    };
}
