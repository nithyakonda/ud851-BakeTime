package com.udacity.nkonda.baketime.recepiesteps.detail;

import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;

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
        if (sRecipe == null || (sRecipe != null && sRecipe.getId() != sLastRecipeId))
            sRecipe = mRepository.getRecipe(sLastRecipeId);
        RecipeStep step = sRecipe.getRecipeSteps().get(sLastSelectedStepId);
            if (!step.getVideoUrl().isEmpty() && mView.isOnline()) {
                mView.showMedia(step.getVideoUrl());
            } else {
                mView.showNoMedia();
            }
        mView.showDesc(step.getDesc());
        enableNavButtons();
    }

    @Override
    public void onNextButtonClicked() {
        if (sLastSelectedStepId < sRecipe.getRecipeSteps().size() - 1) {
            sLastSelectedStepId++;
            load();
        }
        enableNavButtons();
    }

    @Override
    public void onPrevButtonClicked() {
        if (sLastSelectedStepId > 0) {
            sLastSelectedStepId--;
            load();
        }
        enableNavButtons();
    }

    @Override
    public void start(RecipeStepDetailState state) {
        if (state != null) {
            sLastSelectedStepId = state.getLastSelectedStepId();
            sLastRecipeId = state.getLastRecipeId();
        }
        load();
        mView.setTitle(sRecipe.getName());
    }

    @Override
    public RecipeStepDetailState getState() {
        return new RecipeStepDetailState(sLastRecipeId, sLastSelectedStepId);
    }

    private void enableNavButtons() {
        if (sLastSelectedStepId > 0 && sLastSelectedStepId < sRecipe.getRecipeSteps().size() - 1) {
            mView.enableNext(true);
            mView.enablePrev(true);
        } else if (sLastSelectedStepId == 0) {
            mView.enablePrev(false);
            mView.enableNext(true);
        } else if (sLastSelectedStepId == sRecipe.getRecipeSteps().size() - 1) {
            mView.enablePrev(true);
            mView.enableNext(false);
        }
    }
}
