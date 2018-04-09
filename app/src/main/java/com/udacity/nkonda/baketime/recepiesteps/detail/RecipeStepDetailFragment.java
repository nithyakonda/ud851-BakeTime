package com.udacity.nkonda.baketime.recepiesteps.detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.udacity.nkonda.baketime.R;
import com.udacity.nkonda.baketime.data.source.RecipesRepository;
import com.udacity.nkonda.baketime.recepiesteps.list.RecipeStepListActivity;

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
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private FloatingActionButton mNextStep;
    private FloatingActionButton mPrevStep;

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
        mPresenter = new RecipeStepDetailPresenter(this, new RecipesRepository(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);
        mSimpleExoPlayerView = rootView.findViewById(R.id.exo_media);
        mSimpleExoPlayerView.requestFocus();

        mDescView = rootView.findViewById(R.id.tv_desc);
        mNextStep = rootView.findViewById(R.id.btn_next);
        mPrevStep = rootView.findViewById(R.id.btn_prev);
        if (mDescView != null) {
            mDescView.setMovementMethod(new ScrollingMovementMethod());
        }
        if (mNextStep != null &&  mPrevStep != null) {
            mNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onNextButtonClicked();
                }
            });
            mPrevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onPrevButtonClicked();
                }
            });
        }
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
    public void showMedia(String url) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        mSimpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(true);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

//        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
//                mediaDataSourceFactory, extractorsFactory, null, null);
//        player.prepare(mediaSource);
    }

    @Override
    public void showDesc(String desc) {
        if (mDescView != null) {
            mDescView.setText(desc);
        }
    }

    @Override
    public void setTitle(String shortDesc) {
        AppCompatActivity activity = (AppCompatActivity) this.getActivity();
        activity.getSupportActionBar().setTitle(shortDesc);
    }

    @Override
    public void enableNext(boolean enable) {
        int color = R.color.colorAccent;
        if (mNextStep != null) {
            mNextStep.setEnabled(enable);
            setButtonColor(mNextStep, enable);
        }
    }

    @Override
    public void enablePrev(boolean enable) {
        if (mPrevStep != null) {
            mPrevStep.setEnabled(enable);
            setButtonColor(mPrevStep, enable);
        }
    }

    public int getRecipeId() {
        return mState.getLastRecipeId();
    }

    private void setButtonColor(FloatingActionButton btn, boolean enabled) {
        int color = R.color.colorAccent;
        if (!enabled) {
            color = R.color.colorDisabled;
        }
        btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
    }
}
