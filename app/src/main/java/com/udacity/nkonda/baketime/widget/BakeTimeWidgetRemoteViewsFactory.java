package com.udacity.nkonda.baketime.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.Ingredient;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.util.SharedPrefHelper;

import java.util.List;

/**
 * Created by nkonda on 4/19/18.
 */

public class BakeTimeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private RecipesRepository mRepository;
    private List<Ingredient> mIngredients;
    private int mRecipeId;
    private Cursor mCursor;

    public BakeTimeWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mRepository = new RecipesRepository(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipeId = SharedPrefHelper.getRecipeId(mContext);
        mIngredients = mRepository.getIngredients(mRecipeId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients != null ? mIngredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                position >= mIngredients.size()) {
            return null;
        }
        Ingredient ingredient = mIngredients.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item_layout);
        rv.setTextViewText(R.id.tv_ingredient_name, ingredient.getName());
        rv.setTextViewText(R.id.tv_ingredient_qty, String.valueOf(ingredient.getQuantity()));
        rv.setTextViewText(R.id.tv_ingredient_measure, ingredient.getMeasureUnit());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(BakeTimeWidget.ARGKEY_RECIPE_ID, mRecipeId);
        rv.setOnClickFillInIntent(R.id.widget_item_container, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
