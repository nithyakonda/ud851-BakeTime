package com.udacity.nkonda.baketime.recepiesteps.detail;

import com.udacity.nkonda.baketime.BasePresenter;
import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.BaseView;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListContract;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipeStepDetailPresenter implements RecipeStepDetailContract.Presenter{
    private static int sLastRecipeId;
    private static int sLastSelectedStepId;
    private static Recipe sRecipe;

    private RecipeStepDetailContract.View mView;
    private RecipesRepository mRepository;

    public RecipeStepDetailPresenter(RecipeStepDetailContract.View view, RecipesRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void load() {
        if (sRecipe == null)
            sRecipe = mRepository.getRecipe(sLastRecipeId);
        RecipeStep step = sRecipe.getRecipeSteps().get(sLastRecipeId);
        if (step.getVideoUrl() != null) {
            mView.showMedia(step.getVideoUrl());
        }
        mView.showDesc(step.getDesc());
    }

    @Override
    public void onNextButtonClicked() {
        if (sLastSelectedStepId < sRecipe.getRecipeSteps().size()) {
            sLastSelectedStepId++;
            load();
        }
    }

    @Override
    public void onPrevButtonClicked() {
        if (sLastSelectedStepId > 0) {
            sLastSelectedStepId--;
            load();
        }
    }

    @Override
    public void start(RecipeStepDetailState state) {
        if (state != null) {
            sLastSelectedStepId = state.getLastSelectedStepId();
            sLastRecipeId = state.getLastRecipeId();
        }
        load();
    }

    @Override
    public RecipeStepDetailState getState() {
        return new RecipeStepDetailState(sLastRecipeId, sLastSelectedStepId);
    }
}
