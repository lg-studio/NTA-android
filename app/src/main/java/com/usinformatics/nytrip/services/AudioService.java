package com.usinformatics.nytrip.services;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.usinformatics.nytrip.IntentConsts;
import com.usinformatics.nytrip.services.media.CustomMediaPlayer;

/**
 * Created by D1m11n on 16.07.2015.
 */
public class AudioService extends IntentService{


    private static final String TAG="AUDIO_SERVICE";
    private final CustomMediaPlayer mMediaPlayer;

    public AudioService() {
        super(TAG);
        mMediaPlayer = new CustomMediaPlayer();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) return;
        if (intent.getAction().equals(IntentConsts.AUDIO)&&intent.hasExtra(IntentConsts.Extra.AUDIO_URL)) {
            Log.e(TAG, "AUDIO");
            startPlay(intent.getStringExtra(IntentConsts.Extra.AUDIO_URL));
        }
    }

    private void startPlay(String url) {
        Log.e(TAG, "PLAY IS  = " + url);
        if (TextUtils.isEmpty(url)) return;
        mMediaPlayer.playUrl(url);
    }
}
