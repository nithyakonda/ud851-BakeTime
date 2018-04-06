package com.udacity.nkonda.baketime.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by nkonda on 3/26/18.
 */

public class RecipeStep implements Parcelable {
    private final int mId;
    private final String mShortDesc;
    private final String mDesc;
    private final String mVideoUrl;
    private final String mThumbnailUrl;

    public RecipeStep(int id, String shortDesc, String desc, String videoUrl, String thumbnailUrl) {
        mId = id;
        mShortDesc = shortDesc;
        mDesc = desc;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return mId;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public String getDesc() {
        return mDesc;
    }

    @Nullable
    public URL getVideoUrl() {
        try {
            return new URL(Uri.parse(mVideoUrl).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public URL getThumbnailUrl() {
        try {
            return new URL(Uri.parse(mThumbnailUrl).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mShortDesc);
        dest.writeString(this.mDesc);
        dest.writeString(this.mVideoUrl);
        dest.writeString(this.mThumbnailUrl);
    }

    protected RecipeStep(Parcel in) {
        this.mId = in.readInt();
        this.mShortDesc = in.readString();
        this.mDesc = in.readString();
        this.mVideoUrl = in.readString();
        this.mThumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel source) {
            return new RecipeStep(source);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };
}
