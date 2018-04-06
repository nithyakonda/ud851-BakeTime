package com.udacity.nkonda.baketime.recepiesteps.list;

import com.udacity.nkonda.baketime.BasePresenter;
import com.udacity.nkonda.baketime.BaseView;
import com.udacity.nkonda.baketime.data.Recipe;

/**
 * Created by nkonda on 3/25/18.
 */

public interface RecipeStepListContract {
    interface View extends BaseView {
        void showRecipeDetails(Recipe recipe);
    }

    interface Presenter extends BasePresenter {
        void loadRecipeDetails(Recipe recipe);
        void onStepSelected(int recipeStepId);
    }
}
