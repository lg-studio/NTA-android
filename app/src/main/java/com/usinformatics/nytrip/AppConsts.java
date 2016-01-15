package com.usinformatics.nytrip;

import android.os.Environment;

/**
 * Created by D1m11n on 11.06.2015.
 */
public interface AppConsts {

    int SPLASH_DELAY = 250;

    int DELAY_INTRO_TRANSITION = 2500;

    int MIN_PASS_LENGTH = 5;

    int SPEECH_DURATIONms = 30000;

    int SPEECH_SILENTms = 30000;

    long SPEECH_MAX_TIMEms = 5000L;

    int MAX_RECORD_DURATIONs=15;

    int MIN_MARK_SHOW_DIALOG=50;


    String BROADCAST_NOTIFICATION = "intent_notification";

    String EXTRA_NOTIFICATION = "extra_notification";

    String KEY_EXTRA_SCENE_ID = "scene_id";

    String GCM_INSTANCE_ID = "GCM instance id";

    String PROJECT_ID = "projectNumber";

    String APP_DIRECTORY_PATH= Environment.getExternalStorageDirectory().getPath()+ "/NYtrip";

    String GOODBYE = "Goodbye";
}
