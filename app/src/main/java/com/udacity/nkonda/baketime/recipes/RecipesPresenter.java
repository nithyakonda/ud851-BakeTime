package com.udacity.nkonda.baketime.recipes;

import android.content.Context;
import android.content.Intent;

import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListActivity;

import java.util.List;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipesPresenter implements RecipesContract.Presenter{

    private RecipesContract.View mView;
    private RecipesRepository mRepository;

    private static List<Recipe> sRecipes;

    public RecipesPresenter(RecipesContract.View view, RecipesRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void start(BaseState state) {
        // no op
    }

    @Override
    public BaseState getState() {
        // no op
        return null;
    }

    @Override
    public void load() {
        sRecipes = mRepository.getRecipes();
        mView.showResults(sRecipes);
    }

    @Override
    public void recipeSelected(Context context, int pos) {
        Intent intent = new Intent();
        intent.setClass(context, RecipeStepListActivity.class);
        intent.putExtra(RecipeStepListActivity.ARGKEY_RECIPE, sRecipes.get(pos));
        context.startActivity(intent);
    }
}
