package com.example.schmidegv.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schmidegv.bakingapp.R;
import com.example.schmidegv.bakingapp.model.Recipe;
import com.example.schmidegv.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public class SingleStepFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = SingleStepFragment.class.getSimpleName();
    public static final String PLAYER_POSITION = "player_position";
    public static final String PLAY_WHEN_READY = "play_when_ready";

    private String description;
    private String recipeThumbnailUrl;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private String recipeUrl;
    private Step step;
    private ImageView mImage;
    private static Bundle mBundle;
    private View rootView;

    private boolean isPlayWhenReady;
    private long playerPosition;

    public SingleStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.single_step_fragment, container, false);

        mPlayer = rootView.findViewById(R.id.playerView);
        mImage = rootView.findViewById(R.id.iv_step_image);


        if (savedInstanceState != null) {
            Log.e(TAG, "savedInstance ");

            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            isPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);

            step = savedInstanceState.getParcelable("step");
            if (step != null)
                setPlayerOrImg(step);

        } else {
            Log.e(TAG, "No saved instance ");

            step = getActivity().getIntent().getParcelableExtra("stepObject");
            if (step != null)
                setPlayerOrImg(step);

            //for tablet
            if (mBundle != null) {
                step = mBundle.getParcelable("bundleStep");
                Log.e(TAG, "bundle step " + step);
                if (step != null)
                    setPlayerOrImg(step);
            }
        }

        TextView mName = rootView.findViewById(R.id.tv_step_descr);
        mName.setText(description);

        Recipe recipe = getActivity().getIntent().getParcelableExtra("recipeObject");
        String title = recipe.getName();
        getActivity().setTitle(title);

        return rootView;
    }

    public void setPlayerOrImg(Step step) {
        description = step.getDescription();
        recipeThumbnailUrl = step.getThumbnailURL();
        recipeUrl = step.getVideoURL();

        if (!step.getVideoURL().equals("")) {
            mImage.setVisibility(View.GONE);
            mPlayer.setVisibility(View.VISIBLE);
            initializeMediaSession();
            initializePlayer(Uri.parse(recipeUrl));

        } else if (!step.getThumbnailURL().equals("")) {
            mPlayer.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);

            Picasso.Builder builder = new Picasso.Builder(getContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                    Log.e(TAG, "image error ");
                    mImage.setVisibility(View.GONE);
                }
            });
            builder.build().load(recipeThumbnailUrl).into(mImage);
        } else {
            mPlayer.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", step);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(PLAY_WHEN_READY, isPlayWhenReady);
    }

    /**
     * Initializes the media session
     */
    private void initializeMediaSession() {

        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCallback());

        mMediaSession.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayer.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);
            mExoPlayer.seekTo(playerPosition);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            Log.e(TAG, "PLAYING");
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            Log.e(TAG, "PAUSED");
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            isPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!step.getVideoURL().equals("")) {
            releasePlayer();
            mMediaSession.setActive(false);
        }
    }

    public static void putItemT(Bundle bundle) {
        mBundle = bundle;
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer(Uri.parse(recipeUrl));
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(recipeUrl));
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }


}

