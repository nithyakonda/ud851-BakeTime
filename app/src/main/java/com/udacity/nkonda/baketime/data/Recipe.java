package com.udacity.nkonda.baketime.data;

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
    private final String mImage;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> recipeSteps, int servings, String image) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
        mServings = servings;
        mImage = image;
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

    public String getImage() {
        return mImage;
    }
}
