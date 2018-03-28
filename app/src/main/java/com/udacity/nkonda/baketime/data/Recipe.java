package com.udacity.nkonda.baketime.data;

import com.udacity.nkonda.baketime.R;

import java.util.List;

/**
 * Created by nkonda on 3/26/18.
 */

public class Recipe {
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
}
