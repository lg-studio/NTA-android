package com.usinformatics.nytrip.audio;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.helpers.FileHelper;
import com.usinformatics.nytrip.audio.callbacks.OnGetTextCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by D1m11n on 15.06.2015.
 */
public  class SpeechRecognizerEngine {

    private static final String TAG="RECOGNITION_ACTIVITY";

    private static final int SPEECH_REQUEST_CODE = 0;

    private Activity mActivity;

    private OnGetTextCallback mCallback;

    /**
     *
     * @param activity
     *
     * <b>Need invoke onActivityResult to the appropriate meothod of activity<b/>
     */
    public SpeechRecognizerEngine(Activity activity){
       mActivity=activity;
    }


    public final void startTextRecognizer(OnGetTextCallback callback) {
        startTextRecognizer(null, callback);
    }

    //http://developer.android.com/reference/android/speech/RecognizerIntent.html#EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS
    public final void startTextRecognizer(String language, OnGetTextCallback callback) {
        mCallback = callback;
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
            intent.putExtra("android.speech.extra.GET_AUDIO", true);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, AppConsts.SPEECH_DURATIONms);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, AppConsts.SPEECH_SILENTms);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, AppConsts.SPEECH_SILENTms);
            if (!TextUtils.isEmpty(language))
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
            mActivity.startActivityForResult(intent, SPEECH_REQUEST_CODE);
            //TODO ADD HERE FLAG WHEN OPENED
        }catch (ActivityNotFoundException e){
            if(mCallback!=null)
                mCallback.onError("Not found Activity to execute this");
        }
    }


    //http://stackoverflow.com/questions/10538791/how-to-set-the-language-in-speech-recognition-on-android

    /**
     *
     * @return true if it was handled by this method
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult; requestCode = " + requestCode + "; responseCode = " + resultCode);
        if (requestCode != SPEECH_REQUEST_CODE)
              return false;
        if(resultCode == mActivity.RESULT_OK) {
           onResultOk(data);
        }
        else{
            onResultError(resultCode);
        }
        return true;
    }

    private void onResultError(int resultCode){
        if(mCallback==null) return;
        switch(resultCode){
            case RecognizerIntent.RESULT_AUDIO_ERROR:
                mCallback.onError("Audio Error"); return;
            case RecognizerIntent.RESULT_CLIENT_ERROR:
                mCallback.onError("Client Error"); return;
            case RecognizerIntent.RESULT_NETWORK_ERROR:
                mCallback.onError("Network Error or Microphone is busy"); return;
            case RecognizerIntent.RESULT_NO_MATCH:
                mCallback.onError("No Match"); return;
            case RecognizerIntent.RESULT_SERVER_ERROR:
                mCallback.onError("Server Error"); return;
        }
    }

    private void onResultOk(Intent data){
        List<String> texts = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        float [] marks= data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);
        if (mCallback!=null)
            mCallback.onGetSpokenText(texts,marks);
        if(data!=null) {
            Uri audioUri = data.getData();
           // ToastHelper.showSimple(mActivity, "URI = " + audioUri);
            getAudioFile(audioUri);

        }else{
            //ToastHelper.showSimple(mActivity, "URI = " + " is NULL");
        }
        Log.e("TEXT_RESULT", "spoken text is  = " + texts.get(0));
    }

    private void getAudioFile(Uri audioUri) {
        if(audioUri==null)
            return;
        ContentResolver contentResolver = mActivity.getContentResolver();
        try {
           FileHelper.saveFromContentResolver(mActivity, audioUri);
        } catch (IOException e) {
            Log.e(TAG, "getAudioFile error = " +e.toString());
        }
    }

}
