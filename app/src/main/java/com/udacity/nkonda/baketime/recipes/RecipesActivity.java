package com.udacity.nkonda.baketime.recipes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.nkonda.baketime.BaseActivity;
import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.adapters.RecipesAdapter;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends BaseActivity implements RecipesContract.View{

    @BindView(R.id.rv_recipes) RecyclerView mRecipesView;

    RecipesAdapter mAdapter;
    RecipesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepies);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = null;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 1);
        }
        mAdapter = new RecipesAdapter();
        mAdapter.setOnItemClickedListener(new RecipesAdapter.OnItemClickedListener() {
            @Override
            public void onClick(View v) {
                mPresenter.recipeSelected(RecipesActivity.this, (Integer) v.getTag());
            }
        });
        mRecipesView.setLayoutManager(layoutManager);
        mRecipesView.setAdapter(mAdapter);

        mPresenter = new RecipesPresenter(this,
                new RecipesRepository(this));
        mPresenter.load();
    }

    @Override
    public void showResults(List<Recipe> recipes) {
        mAdapter.setRecipes(recipes);
        mAdapter.notifyDataSetChanged();
    }
}
