package com.udacity.nkonda.baketime.recepiesteps.detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
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
    private static final String ARG_PLAYBACK_POSITION = "ARG_PLAYBACK_POSITION";

    private RecipeStepDetailState mState;
    private RecipeStepDetailPresenter mPresenter;
    private MediaPlayerStateListener mMediaPlayerStateListener;

    private TextView mDescView;
    private View mDefaultVideoView;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private FloatingActionButton mNextStep;
    private FloatingActionButton mPrevStep;

    private SimpleExoPlayer mPlayer;
    private int mCurrentWindow = 0;
    private long mPlaybackPosition = 0;
    private boolean mPlayWhenReady = true;

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RecipeStepDetailPresenter(this, new RecipesRepository(getActivity()));
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
            mPlaybackPosition = savedInstanceState.getLong(ARG_PLAYBACK_POSITION);
        }
        mMediaPlayerStateListener = new MediaPlayerStateListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);
        mDefaultVideoView = rootView.findViewById(R.id.no_video_background);
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
        initializePlayer();
        mPresenter.start(mState);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RecipeStepDetailState state = mPresenter.getState();
        outState.putInt(ARG_RECIPE_ID, state.getLastRecipeId());
        outState.putInt(ARG_RECIPE_STEP_ID, state.getLastSelectedStepId());
        if (mPlayer != null) {
            outState.putLong(ARG_PLAYBACK_POSITION, mPlayer.getCurrentPosition());
        } else {
            outState.putLong(ARG_PLAYBACK_POSITION, mPlaybackPosition);
        }
    }

    @Override
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void showMedia(String url) {
        mPlayer.stop();
        if (mSimpleExoPlayerView.getVisibility() == View.GONE) {
            mDefaultVideoView.setVisibility(View.GONE);
            mSimpleExoPlayerView.setVisibility(View.VISIBLE);
        }
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-baketime")).
                createMediaSource(uri);
        mPlayer.prepare(mediaSource, true, false);
    }

    @Override
    public void showNoMedia() {
        mPlayer.stop();
        mSimpleExoPlayerView.setVisibility(View.GONE);
        mDefaultVideoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDesc(String desc) {
        if (mDescView != null) {
            mDescView.setText(desc);
        }
    }

    @Override
    public void setTitle(String recipeName) {
        AppCompatActivity activity = (AppCompatActivity) this.getActivity();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(recipeName);
        }
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

    private void initializePlayer() {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mSimpleExoPlayerView.setPlayer(mPlayer);
            mPlayer.addListener(mMediaPlayerStateListener);
            mPlayer.seekTo(mPlaybackPosition);
            mPlayer.setPlayWhenReady(mPlayWhenReady);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-baketime"))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private class MediaPlayerStateListener extends Player.DefaultEventListener  {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE:
                    break;
                case Player.STATE_BUFFERING:
                    break;
                case Player.STATE_READY:
                    break;
                case Player.STATE_ENDED:
                    mPlayWhenReady = false;
                    mPlayer.seekTo(C.TIME_UNSET);
                    mPlayer.setPlayWhenReady(mPlayWhenReady);
                    break;
                default:
                    break;
            }
        }
    }
}
