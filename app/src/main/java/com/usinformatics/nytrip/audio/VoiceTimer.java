package com.usinformatics.nytrip.voice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.audio.VoiceRecognitionService;
import com.usinformatics.nytrip.audio.callbacks.VoiceTimerCallback;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorder;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorderCallback;

import java.io.IOException;

/**
 * Created by D1m11n on 07.07.2015.
 */
public class VoiceTimer extends CountDownTimer {

    private static final String TAG="VOICE_TIMER";
    private static final long INTERVAL=100L;
    private VoiceRecorder mRecorder;
    private boolean mIsStopped;
    private boolean mBound;
    private Activity mActivity;
    private BroadcastReceiver mServiceReceiver;
    private VoiceTimerCallback mCallback;

    public VoiceTimer(Activity activity,VoiceTimerCallback callback) {
        super(AppConsts.SPEECH_MAX_TIMEms, INTERVAL);
        mActivity=activity;
        mCallback=callback;
        initVoiceEntities();
    }

    private void initVoiceEntities() {
        mRecorder = new VoiceRecorder(mActivity);
        mServiceReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent!=null&&intent.getAction()== VoiceRecognitionService.INTENT_RECOGNITION)
                    try {
                        startRecognition();
                    } catch (IOException e) {
                        if(mCallback!=null)
                            mCallback.onError(e);
                    }
            }
        };
    }

    private void startRecognition() throws IOException {

        if(mCallback!=null)
            mCallback.wasStarted();
    }

    @Override
    public final void onTick(long millisUntilFinished) {
        if(mIsStopped) {
            cancel();
        }
    }

    public void begin(){
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(mServiceReceiver,new IntentFilter(VoiceRecognitionService.INTENT_RECOGNITION));
        Intent intent = new Intent(mActivity, VoiceRecognitionService.class);
        intent.putExtra(VoiceRecognitionService.EXTRA_CHECK,"");
        mActivity.startService(intent);
    }

    @Override
    public void onFinish() {
        Log.e(TAG,"ON FINISH");
        release();
    }

    private void release() {
        mRecorder.stopNotSave();
        mRecorder=null;
        LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(mServiceReceiver);
        VoiceRecognitionService.getInstance().onDestroy();
        if(mCallback!=null)
            mCallback.wasFinished();
    }


    public void stop(){
        mIsStopped =true;
    }
}
