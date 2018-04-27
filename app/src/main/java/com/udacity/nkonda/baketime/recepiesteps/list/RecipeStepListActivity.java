package com.udacity.nkonda.baketime.recepiesteps.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.nkonda.baketime.BaseActivity;
import com.udacity.nkonda.baketime.R;

import com.udacity.nkonda.baketime.data.Ingredient;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * An activity representing a list of RecipeSteps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends BaseActivity implements RecipeStepListContract.View{
    public static final String ARGKEY_RECIPE_ID = "ARGKEY_RECIPE_ID";
    private static final String SAVEKEY_RECIPE_ID = "SAVEKEY_RECIPE_ID";
    private static final String SAVEKEY_STEP_ID = "SAVEKEY_STEP_ID";


    @BindView(R.id.toolbar) Toolbar mToolBar;
    @BindView(R.id.recipestep_list) RecyclerView mRecyclerView;
    private View mLastSelView;

    private boolean mTwoPane;
    private RecipeStepListPresenter mPresenter;
    private RecipeStepListState mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.recipestep_detail_container) != null) {
            mTwoPane = true;
        }

        assert mRecyclerView != null;
        mPresenter = new RecipeStepListPresenter(this, new RecipesRepository(this));
        if (savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(ARGKEY_RECIPE_ID, -1);

            mState = new RecipeStepListState(0,
                    recipeId);
            mPresenter.recreatedOnOrientationChange(false);
        } else {
            mState = new RecipeStepListState(savedInstanceState.getInt(SAVEKEY_STEP_ID),
                    savedInstanceState.getInt(SAVEKEY_RECIPE_ID));
            mPresenter.recreatedOnOrientationChange(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start(mState);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RecipeStepListState state = (RecipeStepListState) mPresenter.getState();
        outState.putInt(SAVEKEY_STEP_ID, state.getLastSelectedStepId());
        outState.putInt(SAVEKEY_RECIPE_ID, state.getLastRecipeId());
    }

    @Override
    public void showRecipeDetails(Recipe recipe) {
        getSupportActionBar().setTitle(recipe.getName());
        RecipeStepsAdapter adapter = new RecipeStepsAdapter(this, recipe, mState, mTwoPane);
        adapter.setOnItemSelectedListener(new RecipeStepsAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view) {
                int recipeStepId = (Integer) view.getTag();
                if (recipeStepId == -1) return;
                mPresenter.onStepSelected(RecipeStepListActivity.this, recipeStepId, mTwoPane);
                if (mTwoPane) {
                    if (mLastSelView != null) {
                        mLastSelView.setSelected(false);
                    }
                    mLastSelView = view;
                }
                view.setSelected(true);
            }
        });

        mRecyclerView.setAdapter(adapter);

        if (mTwoPane) {
            mPresenter.onStepSelected(this, mState.getLastSelectedStepId(), mTwoPane);
        }
    }

    public static class RecipeStepsAdapter
            extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {
        private static final int VIEW_TYPE_INGREDIENTS = 0;
        private static final int VIEW_TYPE_RECIPE_STEP = 1;

        private final RecipeStepListActivity mParentActivity;
        private final Recipe mRecipe;
        private final boolean mTwoPane;
        private final RecipeStepListContract.State mState;
        private Context mContext;
        private OnItemSelectedListener mOnClickListener;

        RecipeStepsAdapter(RecipeStepListActivity parent,
                Recipe recipe,
                RecipeStepListContract.State state,
                boolean twoPane) {
            mRecipe = recipe;
            mParentActivity = parent;
            mState = state;
            mTwoPane = twoPane;
        }

        public void setOnItemSelectedListener(OnItemSelectedListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            int layoutId;
            switch (viewType) {
                case VIEW_TYPE_INGREDIENTS:
                    layoutId = R.layout.ingredients_layout;
                    break;
                case VIEW_TYPE_RECIPE_STEP:
                    layoutId = R.layout.recipestep_list_item;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid view type, value of " + viewType);
            }
            View view = LayoutInflater.from(mContext)
                    .inflate(layoutId, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case VIEW_TYPE_INGREDIENTS:
                    holder.bindIngredientsView(mContext, mRecipe.getIngredients(), mRecipe.getServings());
                    break;
                case VIEW_TYPE_RECIPE_STEP:
                    holder.bindRecipeStep(position - 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid view type, value of " + viewType);
            }
        }

        @Override
        public int getItemCount() {
            return mRecipe.getRecipeSteps().size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return VIEW_TYPE_INGREDIENTS;
            } else {
                return VIEW_TYPE_RECIPE_STEP;
            }
        }

        public interface OnItemSelectedListener {
            public void onItemSelected(View view);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            @BindView(R.id.tv_short_desc)
            @Nullable
            TextView mStepDescView;

            @BindView(R.id.tv_servings)
            @Nullable
            TextView mServingsView;

            @BindView(R.id.ll_ingredients)
            @Nullable
            LinearLayout mIngredientsLayout;

            @BindView(R.id.iv_video_icon)
            @Nullable
            ImageView mHasVideoIcon;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            void bindIngredientsView(Context context, List<Ingredient> ingredients, int servings) {
                LayoutInflater inflater = LayoutInflater.from(context);
                itemView.setTag(-1);
                mServingsView.setText(String.valueOf(servings));
                for (Ingredient ingredient : ingredients) {
                    View ingredientListItemView = inflater.inflate(R.layout.ingredient_item_layout, null);
                    TextView tvName = ingredientListItemView.findViewById(R.id.tv_ingredient_name);
                    TextView tvQty = ingredientListItemView.findViewById(R.id.tv_ingredient_qty);
                    TextView tvMeasure = ingredientListItemView.findViewById(R.id.tv_ingredient_measure);
                    tvName.setText(ingredient.getName());
                    tvQty.setText(String.valueOf(ingredient.getQuantity()));
                    tvMeasure.setText(ingredient.getMeasureUnit());
                    mIngredientsLayout.addView(ingredientListItemView);
                }

            }

            void bindRecipeStep(int pos) {
                RecipeStep step = mRecipe.getRecipeSteps().get(pos);
                mStepDescView.setText(step.getShortDesc());
                if (step.getVideoUrl().isEmpty()) {
                    mHasVideoIcon.setVisibility(View.INVISIBLE);
                }
                itemView.setTag(step.getId());
                if (mTwoPane && step.getId() == mState.getLastSelectedStepId()) {
                    itemView.setSelected(true);
                    itemView.callOnClick();
                }
            }

            @Override
            public void onClick(View v) {
                mOnClickListener.onItemSelected(v);
            }
        }
    }
}
