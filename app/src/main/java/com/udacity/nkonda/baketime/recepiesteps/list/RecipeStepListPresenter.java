package com.udacity.nkonda.baketime.recepiesteps.list;

import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipeStepListPresenter implements RecipeStepListContract.Presenter{
    private RecipeStepListContract.View mView;
    private RecipesRepository mRepository;

    private static int sLastSelectedStepId = -1;
    private static int sLastRecipeId = -1;
    private static Recipe sRecipe;

    public RecipeStepListPresenter(RecipeStepListContract.View view, RecipesRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void loadRecipeDetails() {
        sRecipe = mRepository.getRecipe(sLastRecipeId);
        mView.showRecipeDetails(sRecipe);
    }

    @Override
    public void onStepSelected(int recipeStepId) {

    }

    @Override
    public void start(RecipeStepListContract.State state) {
        if (state != null) {
            sLastSelectedStepId = state.getLastSelectedStepId();
            sLastRecipeId = state.getLastRecipeId();
        }
        loadRecipeDetails();
    }

    @Override
    public RecipeStepListContract.State getState() {
        return new RecipeStepListState(sLastSelectedStepId, sLastRecipeId);
    }
}
