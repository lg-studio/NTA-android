package com.usinformatics.nytrip.audio.callbacks;

import java.io.IOException;

/**
 * Created by D1m11n on 07.07.2015.
 */
public interface VoiceTimerCallback {
    void wasStarted();

    void wasFinished();

    void onError(IOException e);
}
