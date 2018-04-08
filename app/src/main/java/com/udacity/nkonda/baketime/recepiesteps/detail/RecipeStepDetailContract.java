package com.udacity.nkonda.baketime.recepiesteps.detail;

import com.udacity.nkonda.baketime.BasePresenter;
import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.BaseView;

import java.net.URL;

/**
 * Created by nkonda on 3/25/18.
 */

public interface RecipeStepDetailContract {

    interface View extends BaseView {
        void showMedia(URL url);
        void showDesc(String desc);
        void setTitle(String shortDesc);
    }

    interface Presenter extends BasePresenter<RecipeStepDetailState> {
        void load();
        void onNextButtonClicked();
        void onPrevButtonClicked();
    }

    interface State extends BaseState {
        int getLastRecipeId();
        int getLastSelectedStepId();
    }
}
