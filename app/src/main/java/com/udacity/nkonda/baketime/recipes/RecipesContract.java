package com.udacity.nkonda.baketime.recipes;

import android.content.Context;

import com.udacity.nkonda.baketime.BasePresenter;
import com.udacity.nkonda.baketime.BaseView;
import com.udacity.nkonda.baketime.data.Recipe;

import java.util.List;

/**
 * Created by nkonda on 3/25/18.
 */

public interface RecipesContract {
    interface View extends BaseView {
        void showResults(List<Recipe> recipes);
    }

    interface Presenter extends BasePresenter {
        void load();
        void recipeSelected(Context context, int pos);
    }
}
