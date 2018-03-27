package com.udacity.nkonda.baketime.data;

/**
 * Created by nkonda on 3/26/18.
 */

public class Ingredient {
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
}
