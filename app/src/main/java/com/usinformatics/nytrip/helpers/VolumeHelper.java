package com.usinformatics.nytrip.helpers;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by D1m11n on 17.01.2015.
 */
public class VolumeHelper {

    private static final int MUSIC_STREAM = AudioManager.STREAM_MUSIC;
    private static int mLastVolume = -1;

    private static AtomicInteger mCount = new AtomicInteger(0);
    private static AudioManager mAmManager;

    private VolumeHelper() {
    }

    public static void setVolume(Context context,int level) {
        if (level < 0) return;
        mCount.incrementAndGet();
        initAudioManagerIfNeeded(context);
        if (mLastVolume == -1) {
            mLastVolume = mAmManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        mAmManager.setStreamVolume(MUSIC_STREAM, level > getMax(context) ? getMax(context) : level, 0);
    }

    public static void restoreVolume(Context context) {
        initAudioManagerIfNeeded(context);
        if (mLastVolume == -1) return;
        if (mCount.decrementAndGet() <= 0) {
            mAmManager.setStreamVolume(MUSIC_STREAM, mLastVolume, 0);
            mLastVolume = -1;
        }
    }

    public static int getCurrentLevel(Context context){
        initAudioManagerIfNeeded(context);
        return mAmManager.getStreamVolume(MUSIC_STREAM);
    }

    public static boolean isMusicLevelEnough(Context context, int targetlevel){
        return getCurrentLevel(context)>=targetlevel;
    }

    public static int getMax(Context context) {
        initAudioManagerIfNeeded(context);
        return mAmManager.getStreamMaxVolume(MUSIC_STREAM);
    }

    private static void initAudioManagerIfNeeded(Context context) {
        if (mAmManager == null)
            mAmManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
    }

}
