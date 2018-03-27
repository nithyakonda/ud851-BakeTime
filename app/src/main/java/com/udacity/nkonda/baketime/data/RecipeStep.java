package com.udacity.nkonda.baketime.data;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by nkonda on 3/26/18.
 */

public class RecipeStep {
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
}
