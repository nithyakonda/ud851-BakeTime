package com.udacity.nkonda.baketime.recepiesteps.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.udacity.nkonda.baketime.BaseActivity;
import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single RecipeStep detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeStepListActivity}.
 */
public class RecipeStepDetailActivity extends BaseActivity {
    private static final String FRAGMENT_TAG = "RECIPE_STEP_DETAIL_FRAGMENT";
    RecipeStepDetailFragment mFragment;

    @BindView(R.id.detail_toolbar)
    @Nullable
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_detail);
        ButterKnife.bind(this);

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail mFragment and add it to the activity
            // using a mFragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID,
                    getIntent().getIntExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, -1));
            arguments.putInt(RecipeStepDetailFragment.ARG_RECIPE_ID,
                    getIntent().getIntExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, -1));
            mFragment = new RecipeStepDetailFragment();
            mFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipestep_detail_container, mFragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, RecipeStepListActivity.class);
            if (mFragment == null) {
                mFragment = (RecipeStepDetailFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            }
            intent.putExtra(RecipeStepListActivity.ARGKEY_RECIPE_ID, mFragment.getRecipeId());
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
