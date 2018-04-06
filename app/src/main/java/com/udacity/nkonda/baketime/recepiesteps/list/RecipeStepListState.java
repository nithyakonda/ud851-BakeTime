package com.udacity.nkonda.baketime.recepiesteps.list;

import com.udacity.nkonda.baketime.data.Recipe;

/**
 * Created by nkonda on 4/5/18.
 */

public class RecipeStepListState implements RecipeStepListContract.State{
    private int mLastSelectedStepId;
    private Recipe mRecipe;

    public RecipeStepListState(int lastSelectedStepId, Recipe recipe) {
        mLastSelectedStepId = lastSelectedStepId;
        mRecipe = recipe;
    }

    @Override
    public int getLastSelectedStepId() {
        return mLastSelectedStepId;
    }

    @Override
    public Recipe getLastRecipe() {
        return mRecipe;
    }
}
