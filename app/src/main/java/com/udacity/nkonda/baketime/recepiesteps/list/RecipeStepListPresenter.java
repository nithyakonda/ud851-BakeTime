package com.udacity.nkonda.baketime.recepiesteps.list;

import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.data.Recipe;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipeStepListPresenter implements RecipeStepListContract.Presenter{
    private RecipeStepListContract.View mView;

    public RecipeStepListPresenter(RecipeStepListContract.View view) {
        mView = view;
    }

    @Override
    public void loadRecipeDetails(Recipe recipe) {
        mView.showRecipeDetails(recipe);
    }

    @Override
    public void onStepSelected(int recipeStepId) {

    }

    @Override
    public void start(BaseState state) {

    }

    @Override
    public BaseState getState() {
        return null;
    }
}
