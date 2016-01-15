package com.usinformatics.nytrip.audio.speech;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.TextUtils;
import android.util.Log;

import com.usinformatics.nytrip.helpers.VolumeHelper;

import java.util.Locale;

/**
 * Created by D1m11n on 17.06.2015.
 */

//http://www.androidhive.info/2012/01/android-text-to-speech-tutorial/
public class TextToSpeechEngine implements TextToSpeech.OnInitListener {

    private static final String ITTERANCE_ID = "itt_nytrip";
    private static final String TAG = "TEXT_SPEECH_ENGINE";

    public interface OnTextToSpeechCallback{

        public void onTTSError(String message);


        /**
         * Not called, when you set non empty message in <i>startTextSpeecher </>
         *
         * @param textSpeech
         */
        public void onReadyToSpeech(TextToSpeech textSpeech);

        public void onDone();

    }

    private TextToSpeech mTextSpeech;

    private Activity mActivity;

    private OnTextToSpeechCallback mCallback;

    private Locale mLocale=Locale.ENGLISH;

    private String mMessage;


    public String getMessage(){
        return mMessage;
    }

    public TextToSpeechEngine(Activity activity){
        mActivity=activity;
    }

    public void startTextSpeecher(Locale locale, TextToSpeechEngine.OnTextToSpeechCallback callback){
        startTextSpeecher(locale,null, callback);
    }


    public void startTextSpeecher(Locale locale,String message,  TextToSpeechEngine.OnTextToSpeechCallback callback){
        mCallback=callback;
        if(locale!=null)
            mLocale=locale;
        mMessage=message;
        mTextSpeech= new TextToSpeech(mActivity, this);
        if(Build.VERSION.SDK_INT<18)
            mTextSpeech.setOnUtteranceCompletedListener(new ExtUttteranceCompletedListener());
        else
            mTextSpeech.setOnUtteranceProgressListener(new ExtUtteranceProgressListener());
    }

    public void startTextSpeecher(TextToSpeechEngine.OnTextToSpeechCallback callback){
        startTextSpeecher(Locale.ENGLISH,null, callback);
    }

    public void startTextSpeecher(String message,TextToSpeechEngine.OnTextToSpeechCallback callback){
        startTextSpeecher(null,message, callback);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextSpeech.setLanguage(mLocale);
            if (result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
                mCallback.onTTSError("TTS Error: " + "This Language is not supported");
                return;
            }
            if(!VolumeHelper.isMusicLevelEnough(mActivity,VolumeHelper.getMax(mActivity)/3)){
                mCallback.onTTSError("Volume level is not enoguh for playing");
                return;
            }
            if(TextUtils.isEmpty(mMessage))
              mCallback.onReadyToSpeech(mTextSpeech);
            else
                startSpeechMessage(mMessage);

        } else {
            mCallback.onTTSError("TTS Error: " + "Initilization Failed!");
        }
    }


    public void startSpeechMessage(String mMessage) {
        if (Build.VERSION.SDK_INT >= 21)
            mTextSpeech.speak(mMessage, TextToSpeech.QUEUE_FLUSH, null, ITTERANCE_ID);
        else
            mTextSpeech.speak(mMessage, TextToSpeech.QUEUE_FLUSH, null);
    }




    private class ExtUttteranceCompletedListener implements TextToSpeech.OnUtteranceCompletedListener{
        @Override
        public void onUtteranceCompleted(String utteranceId) {
            Log.e(TAG, "on DONE");
            if(mCallback!=null)
                mCallback.onDone();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private class ExtUtteranceProgressListener extends UtteranceProgressListener{

        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onDone(String utteranceId) {
            Log.e(TAG, "on class DONE");
            if(mCallback!=null)
                mCallback.onDone();
        }

        @Override
        public void onError(String utteranceId) {
            Log.e(TAG, "on class ERROR");
            if(mCallback!=null)
                mCallback.onTTSError("ErrorCode with utterance = " + utteranceId);
        }

        public void onError(String utteranceId, int errorCode){
            Log.e(TAG, "on class ERROR");
            if(mCallback!=null)
              mCallback.onTTSError("ErrorCode = " + errorCode);
        }
    }


}
