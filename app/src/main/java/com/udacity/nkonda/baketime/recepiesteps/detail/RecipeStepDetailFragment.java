package com.udacity.nkonda.baketime.recepiesteps.detail;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListActivity;
import com.udacity.nkonda.baketime.recepiesteps.dummy.DummyContent;

import java.net.URL;

/**
 * A mFragment representing a single RecipeStep detail screen.
 * This mFragment is either contained in a {@link RecipeStepListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeStepDetailFragment extends Fragment implements RecipeStepDetailContract.View {

    public static final String ARG_RECIPE_STEP_ID = "ARG_RECIPE_STEP_ID";
    public static final String ARG_RECIPE_ID = "ARG_RECIPE_ID";

    private RecipeStepDetailState mState;
    private RecipeStepDetailPresenter mPresenter;

    private TextView mDescView;

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mState = new RecipeStepDetailState(
                    getArguments().getInt(ARG_RECIPE_ID),
                    getArguments().getInt(ARG_RECIPE_STEP_ID)
            );
        } else {
            mState = new RecipeStepDetailState(
                    savedInstanceState.getInt(ARG_RECIPE_ID),
                    savedInstanceState.getInt(ARG_RECIPE_STEP_ID)
            );
        }

        if (getArguments().containsKey(ARG_RECIPE_STEP_ID)) {
            // Load the dummy content specified by the mFragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                int stepNo = mState.getLastSelectedStepId()+1;
                appBarLayout.setTitle("Step " + stepNo);
            }
        }
        mPresenter = new RecipeStepDetailPresenter(this, new RecipesRepository(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);
        mDescView = rootView.findViewById(R.id.tv_desc);

        ImageView nextStep = rootView.findViewById(R.id.btn_next);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onNextButtonClicked();
            }
        });
        ImageView prevStep = rootView.findViewById(R.id.btn_prev);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onPrevButtonClicked();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start(mState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_RECIPE_ID, mState.getLastRecipeId());
        outState.putInt(ARG_RECIPE_STEP_ID, mState.getLastSelectedStepId());
    }

    @Override
    public boolean isOnline() {
        // TODO: 4/7/18 use this
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void showMedia(URL url) {

    }

    @Override
    public void showDesc(String desc) {
        mDescView.setText(desc);
    }

    public int getRecipeId() {
        return mState.getLastRecipeId();
    }
}
