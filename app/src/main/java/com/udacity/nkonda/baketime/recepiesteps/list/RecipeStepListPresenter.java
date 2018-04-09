package com.udacity.nkonda.baketime.recepiesteps.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailActivity;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailFragment;

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
    public void onStepSelected(Context context, int recipeStepId, boolean isTwoPane) {
        sLastSelectedStepId = recipeStepId;
        if (isTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, sLastSelectedStepId);
            arguments.putInt(RecipeStepDetailFragment.ARG_RECIPE_ID, sRecipe.getId());
            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
            fragment.setArguments(arguments);
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipestep_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(context, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, sLastSelectedStepId);
            intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, sRecipe.getId());

            context.startActivity(intent);
        }
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
