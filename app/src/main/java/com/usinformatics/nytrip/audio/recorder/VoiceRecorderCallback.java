package com.usinformatics.nytrip.audio.recorder;

/**
 * Created by D1m11n on 20.07.2015.
 */
public interface VoiceRecorderCallback {


    public static enum RecordError{
        STATE, PROCESS, FILE
    }
    public void onStart();

    public void onEnd(String fileName);


    /**
     *
     * @param error
     * @param message can be null
     */
    public void onError(RecordError error, String message);

}
