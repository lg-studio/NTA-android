package com.usinformatics.nytrip;

/**
 * Created by D1m11n on 06.07.2015.
 */
public interface IntentConsts {

    public static interface Extra{
        /**
         * intent for recording to file; intent contains full path with filename like a /../../filename.xxx
         */
        public static String RECORD_TO_FILENAME="file";

        public static String RECORD_DURATION="duration";

        String TASK = "task";

        String AUDIO_URL ="audio_url";

        String MESSAGE="message";

        String SCENES="scenes";
    }

    public String TTS= "tts";

    public String AUDIO="audio";



}
