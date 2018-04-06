package com.udacity.nkonda.baketime.recepiesteps.list;

import com.udacity.nkonda.baketime.data.Recipe;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipeStepListPresenter implements RecipeStepListContract.Presenter{
    private RecipeStepListContract.View mView;

    private static int sLastSelectedStepId = 0;
    private static Recipe sRecipe;

    public RecipeStepListPresenter(RecipeStepListContract.View view) {
        mView = view;
    }

    @Override
    public void loadRecipeDetails() {
        mView.showRecipeDetails(sRecipe);
    }

    @Override
    public void onStepSelected(int recipeStepId) {

    }

    @Override
    public void start(RecipeStepListContract.State state) {
        if (state != null) {
            sLastSelectedStepId = state.getLastSelectedStepId();
            sRecipe = state.getLastRecipe();
        }
        loadRecipeDetails();
    }

    @Override
    public RecipeStepListContract.State getState() {
        return new RecipeStepListState(sLastSelectedStepId, sRecipe);
    }
}
