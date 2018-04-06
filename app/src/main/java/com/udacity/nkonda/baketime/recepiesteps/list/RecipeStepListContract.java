package com.udacity.nkonda.baketime.recepiesteps.list;

import com.udacity.nkonda.baketime.BasePresenter;
import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.BaseView;
import com.udacity.nkonda.baketime.data.Recipe;

/**
 * Created by nkonda on 3/25/18.
 */

public interface RecipeStepListContract {
    interface View extends BaseView {
        void showRecipeDetails(Recipe recipe);
    }

    interface Presenter extends BasePresenter<State> {
        void loadRecipeDetails();
        void onStepSelected(int recipeStepId);
    }

    interface State extends BaseState {
        int getLastSelectedStepId();
        Recipe getLastRecipe();
    }
}
