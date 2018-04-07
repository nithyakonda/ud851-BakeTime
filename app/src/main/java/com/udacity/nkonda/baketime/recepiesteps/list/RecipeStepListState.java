package com.udacity.nkonda.baketime.recepiesteps.list;

/**
 * Created by nkonda on 4/5/18.
 */

public class RecipeStepListState implements RecipeStepListContract.State{
    private int mLastSelectedStepId;
    private int mRecipeId;

    public RecipeStepListState(int lastSelectedStepId, int recipeId) {
        mLastSelectedStepId = lastSelectedStepId;
        mRecipeId = recipeId;
    }

    @Override
    public int getLastSelectedStepId() {
        return mLastSelectedStepId;
    }

    @Override
    public int getLastRecipeId() {
        return mRecipeId;
    }
}
