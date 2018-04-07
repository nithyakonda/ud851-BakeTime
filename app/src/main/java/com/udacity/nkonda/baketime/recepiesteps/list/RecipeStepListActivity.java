package com.udacity.nkonda.baketime.recepiesteps.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.nkonda.baketime.BaseActivity;
import com.udacity.nkonda.baketime.R;

import com.udacity.nkonda.baketime.data.Ingredient;
import com.udacity.nkonda.baketime.data.Recipe;
import com.udacity.nkonda.baketime.data.RecipeStep;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailActivity;
import com.udacity.nkonda.baketime.recepiesteps.detail.RecipeStepDetailFragment;

import java.util.List;

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

    private RecyclerView mRecyclerView;

    private boolean mTwoPane;
    private RecipeStepListPresenter mPresenter;
    private RecipeStepListState mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.recipestep_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.recipestep_list);
        assert mRecyclerView != null;
        mPresenter = new RecipeStepListPresenter(this, new RecipesRepository(this));
        if (savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(ARGKEY_RECIPE_ID, -1);

            mState = new RecipeStepListState(0,
                    recipeId);
        } else {
            mState = new RecipeStepListState(savedInstanceState.getInt(SAVEKEY_STEP_ID),
                    savedInstanceState.getInt(SAVEKEY_RECIPE_ID));
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
        mRecyclerView.setAdapter(new RecipeStepsAdapter(this, recipe, mTwoPane));
    }

    public static class RecipeStepsAdapter
            extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {
        private static final int VIEW_TYPE_INGREDIENTS = 0;
        private static final int VIEW_TYPE_RECIPE_STEP = 1;

        private final RecipeStepListActivity mParentActivity;
        private final Recipe mRecipe;
        private final boolean mTwoPane;
        private Context mContext;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int recipeStepId = (Integer) view.getTag();
                if (recipeStepId == -1) return;
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, recipeStepId);
                    arguments.putInt(RecipeStepDetailFragment.ARG_RECIPE_ID, mRecipe.getId());
                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipestep_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                    intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_STEP_ID, recipeStepId);
                    intent.putExtra(RecipeStepDetailFragment.ARG_RECIPE_ID, mRecipe.getId());

                    context.startActivity(intent);
                }
            }
        };

        RecipeStepsAdapter(RecipeStepListActivity parent,
                Recipe recipe,
                boolean twoPane) {
            mRecipe = recipe;
            mParentActivity = parent;
            mTwoPane = twoPane;
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
                    holder.bindIngredientsView(mContext, mRecipe.getIngredients());
                    break;
                case VIEW_TYPE_RECIPE_STEP:
                    holder.bindRecipeStep(position - 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid view type, value of " + viewType);
            }
            holder.itemView.setOnClickListener(mOnClickListener);
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

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mStepDescView;
            final LinearLayout mIngredientsLayout;

            ViewHolder(View view) {
                super(view);
                mStepDescView = (TextView) view.findViewById(R.id.tv_short_desc);
                mIngredientsLayout = view.findViewById(R.id.ll_ingredients);
            }

            void bindIngredientsView(Context context, List<Ingredient> ingredients) {
                LayoutInflater inflater = LayoutInflater.from(context);
                itemView.setTag(-1);
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
                itemView.setTag(step.getId());
            }
        }
    }
}
