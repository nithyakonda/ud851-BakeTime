package com.udacity.nkonda.baketime.recipes;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListActivity;
import com.udacity.nkonda.baketime.util.SharedPrefHelper;
import com.udacity.nkonda.baketime.widget.BakeTimeWidget;

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
    public void recipeSelected(Context context, int recipeId) {
        Intent intent = new Intent();
        intent.setClass(context, RecipeStepListActivity.class);
        intent.putExtra(RecipeStepListActivity.ARGKEY_RECIPE_ID, recipeId);
        context.startActivity(intent);
        updateWidget(context, recipeId);
    }

    private void updateWidget(Context context, int recipeId) {
        SharedPrefHelper.setDesiredRecipe(context, recipeId, mRepository.getRecipe(recipeId).getName());
        Intent widgetIntent = new Intent(context, BakeTimeWidget.class);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakeTimeWidget.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(widgetIntent);
    }
}
