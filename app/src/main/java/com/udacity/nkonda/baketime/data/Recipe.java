package com.udacity.nkonda.baketime.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.nkonda.baketime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nkonda on 3/26/18.
 */

public class Recipe implements Parcelable {
    private final int mId;
    private final String mName;
    private final List<Ingredient> mIngredients;
    private final List<RecipeStep> mRecipeSteps;
    private final int mServings;
    private final int mImageResId;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> recipeSteps, int servings) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
        mServings = servings;
        mImageResId = getImageRes();
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<RecipeStep> getRecipeSteps() {
        return mRecipeSteps;
    }

    public int getServings() {
        return mServings;
    }

    public int getImageResId() {
        return mImageResId;
    }

    private int getImageRes() {
        switch (mName) {
            case "Nutella Pie":
                return R.drawable.nutella_pie;
            case "Brownies":
                return R.drawable.brownies;
            case "Yellow Cake":
                return R.drawable.yellow_cake;
            case "Cheesecake":
                return R.drawable.cheese_cake;
            default:
                    return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeList(this.mIngredients);
        dest.writeList(this.mRecipeSteps);
        dest.writeInt(this.mServings);
        dest.writeInt(this.mImageResId);
    }

    protected Recipe(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mIngredients = new ArrayList<Ingredient>();
        in.readList(this.mIngredients, Ingredient.class.getClassLoader());
        this.mRecipeSteps = new ArrayList<RecipeStep>();
        in.readList(this.mRecipeSteps, RecipeStep.class.getClassLoader());
        this.mServings = in.readInt();
        this.mImageResId = in.readInt();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
