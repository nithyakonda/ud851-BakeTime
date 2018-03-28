package com.udacity.nkonda.baketime.recipes;

import com.udacity.nkonda.baketime.BaseState;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;

/**
 * Created by nkonda on 3/25/18.
 */

public class RecipesPresenter implements RecipesContract.Presenter{
    private RecipesContract.View mView;
    private RecipesRepository mRepository;

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
        mView.showResults(mRepository.getRecipes());
    }
}
