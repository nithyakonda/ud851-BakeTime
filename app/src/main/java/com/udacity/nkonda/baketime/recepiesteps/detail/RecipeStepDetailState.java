package com.udacity.nkonda.baketime.recepiesteps.detail;

/**
 * Created by nkonda on 4/6/18.
 */

public class RecipeStepDetailState implements RecipeStepDetailContract.State {
    private int mLastRecipeId;
    private int mLastSelectedStepId;

    public RecipeStepDetailState(int lastRecipeId, int lastSelectedStepId) {
        mLastRecipeId = lastRecipeId;
        mLastSelectedStepId = lastSelectedStepId;
    }

    @Override
    public int getLastRecipeId() {
        return mLastRecipeId;
    }

    @Override
    public int getLastSelectedStepId() {
        return mLastSelectedStepId;
    }
}
