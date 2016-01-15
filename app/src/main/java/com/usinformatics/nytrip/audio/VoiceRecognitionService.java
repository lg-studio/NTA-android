package com.usinformatics.nytrip.audio;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.usinformatics.nytrip.AppConsts;

import java.util.ArrayList;

/**
 * Created by D1m11n on 06.07.2015.
 * http://stackoverflow.com/questions/9997720/how-to-register-a-custom-speech-recognition-service/11633783#11633783
 */
public class VoiceRecognitionService extends RecognitionService {


    public static String EXTRA_CHECK="check";

    public static String INTENT_RECOGNITION="recognition";

    private SpeechRecognizer m_EngineSR;

    private static final String TAG="VOICE_RECOGNITION";

    private static VoiceRecognitionService mInstance;

    private String mLang;

    private boolean mStop=false;


    //private VoiceRecorder mRecorder;



    public static  VoiceRecognitionService getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        //mRecorder = new VoiceRecorder(this);
        m_EngineSR=SpeechRecognizer.createSpeechRecognizer(this);
        Log.e("SimpleVoiceService", "Service started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SimpleVoiceService", "Service stopped");
        mInstance=null;
        mStop=true;
        if(m_EngineSR!=null)
            m_EngineSR.cancel();
        m_EngineSR=null;
    }

    @Override
    protected void onCancel(Callback listener) {
        m_EngineSR.cancel();
        Log.e(TAG, "onCancel_");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "intent = " + intent);
        if (intent!=null&&intent.hasExtra(EXTRA_CHECK)) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(INTENT_RECOGNITION));
            onStartListening(null,null);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void start(String lang){
        mLang=lang;

    }

    public void stop(){
        onDestroy();
    }

    @Override
    public void onStartListening(Intent i, Callback listener) {
        Log.e("SimpleVoiceService", "onStart listening");
//        Intent intent= new Intent();
////        if (!TextUtils.isEmpty(lang))
////            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
//        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
//        intent.putExtra("android.speech.extra.GET_AUDIO", true);


        m_EngineSR.setRecognitionListener(new VoiceResultsListener());
        m_EngineSR.startListening(generateIntent());
    }


    private Intent generateIntent(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
        intent.putExtra("android.speech.extra.GET_AUDIO", true);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, AppConsts.SPEECH_DURATIONms);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, AppConsts.SPEECH_SILENTms);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, AppConsts.SPEECH_SILENTms);
        if (!TextUtils.isEmpty(mLang))
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, mLang);
        return intent;
    }

    @Override
    protected void onStopListening(Callback listener) {
        m_EngineSR.stopListening();
    }
    /**
     *
     */
    private class VoiceResultsListener implements RecognitionListener {

        public VoiceResultsListener() {
        }

        @Override
        public void onBeginningOfSpeech() {
//            try {
//                try {
//                    //mRecorder.start(new File(Environment.getExternalStorageDirectory().toString() + "/OUTPUT.OGG"), null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.e(TAG, "beginning");
//            } catch (RuntimeException e) {
//                Log.e(TAG, "error = " + e.toString());
//            }
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            try {
                Log.e(TAG, "===RECEIVED " + buffer.length);
            } catch (RuntimeException e) {
                Log.e(TAG, "error = " + e.toString());
            }
        }

        @Override
        public void onEndOfSpeech() {
            try {
                Log.e(TAG, "end speech");
                if(!mStop)
                    start(mLang);
                //TODO CHECK RESTART LISTENING

            } catch (RuntimeException e) {
                Log.e(TAG, "error = " + e.toString());
            }
        }

        @Override
        public void onError(int error) {
            try {
                //error No match
                // error INT =  7
                Log.e(TAG, "error " +getErrorText(error));
                Log.e(TAG, "error INT =  " +error);
                if(error==SpeechRecognizer.ERROR_NETWORK){
//                    m_EngineSR.setRecognitionListener(new VoiceResultsListener());
//                    m_EngineSR.startListening(generateIntent());
                }
                //error No speech input
                // error INT =  6
            } catch (RuntimeException e) {
                Log.e(TAG, "error = " + e.toString());
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.e(TAG, "eventType" + params);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            try {
                Log.e(TAG, "partial" + partialResults);
            } catch (RuntimeException e) {
                Log.e(TAG, "error = " + e.toString());
            }
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            try {
                //m_UserSpecifiedListener.readyForSpeech(params);
                Log.e(TAG, "ready");
            } catch (RuntimeException e) {
                Log.e(TAG, "error = " + e.toString());
            }
        }

        @Override
        public void onResults(Bundle results) {
            try {
                Log.e(TAG, "results" + results.getBundle(SERVICE_META_DATA));
                ArrayList<String> list= results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.e(TAG, "results" + list);

                //TODO ADD HERE WORK WITH RESTART
//                for (String key:list){
//                    Log.e(TAG, key +  "  ");
//                }
                //m_UserSpecifiedListener.results(results);
            } catch (RuntimeException e) {
                Log.e(TAG, "error runtime = " + e.toString());
            }
        }

        @Override
        public void onRmsChanged(float rmsdB) {
                //Log.e(TAG, "rms changed");
        }
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}
