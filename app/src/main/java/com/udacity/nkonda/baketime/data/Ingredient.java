package com.udacity.nkonda.baketime.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nkonda on 3/26/18.
 */

public class Ingredient implements Parcelable {
    private final String mName;
    private final int mQuantity;
    private final String mMeasureUnit;

    public Ingredient(String name, int quantity, String measureUnit) {
        mName = name;
        mQuantity = quantity;
        mMeasureUnit = measureUnit;
    }

    public String getName() {
        return mName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getMeasureUnit() {
        return mMeasureUnit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mQuantity);
        dest.writeString(this.mMeasureUnit);
    }

    protected Ingredient(Parcel in) {
        this.mName = in.readString();
        this.mQuantity = in.readInt();
        this.mMeasureUnit = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
