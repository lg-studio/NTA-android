package com.usinformatics.nytrip.audio;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import com.usinformatics.nytrip.audio.callbacks.LanguageDetailsCallback;

import java.util.List;

/**
 * Created by D1m11n on 07.07.2015.
 */
public class RecognizeLanguageReceiver {

    public void getLangInfoOnDevice(Activity activity, final LanguageDetailsCallback callback){
        Intent detailsIntent =  new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
        activity.sendOrderedBroadcast(
                detailsIntent, null, new LanguageDetailsChecker(callback), null, Activity.RESULT_OK, null, null);
    }

    private class LanguageDetailsChecker extends BroadcastReceiver {

        private LanguageDetailsCallback mCallback;

        private List<String> supportedLanguages;

        private String languagePreference;

        private LanguageDetailsChecker(LanguageDetailsCallback callback){
            mCallback=callback;
        }


        @Override
        public void onReceive(Context context, Intent intent){
            Bundle results = getResultExtras(true);
            if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)){
                languagePreference =
                        results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
            }
            if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)){
                supportedLanguages =
                        results.getStringArrayList(
                                RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
            }
            if (mCallback!=null)
                mCallback.onGetLanguageDetails(languagePreference, supportedLanguages);
        }
    }



}
