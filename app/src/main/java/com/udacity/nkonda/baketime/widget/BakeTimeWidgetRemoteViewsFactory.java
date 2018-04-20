package com.udacity.nkonda.baketime.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;

import java.util.List;

/**
 * Created by nkonda on 4/19/18.
 */

public class BakeTimeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Recipe> mRecipes;
    private RecipesRepository mRepository;
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
        mRecipes = mRepository.getRecipes();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                position >= mRecipes.size()) {
            return null;
        }
        Recipe recipe = mRecipes.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_list_item_widget);
        rv.setTextViewText(R.id.tv_recipe_name, recipe.getName());
        rv.setImageViewResource(R.id.iv_recipe_image, recipe.getImageResId());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(BakeTimeWidget.ARGKEY_RECIPE_ID, recipe.getId());
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
        return position >= mRecipes.size() ? mRecipes.get(position).getId() : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
