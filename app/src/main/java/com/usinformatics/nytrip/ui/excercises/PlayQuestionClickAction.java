package com.usinformatics.nytrip.ui.excercises;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.ViewHelper;
import com.usinformatics.nytrip.models.QuestionModel;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.excercises.items.NpcQuestionItemList;
import com.usinformatics.nytrip.audio.speech.TextToSpeechEngine;


/**
 * Created by D1m11n on 16.07.2015.
 * http://stackoverflow.com/questions/11123621/running-code-in-main-thread-from-another-thread
 */
public class PlayQuestionClickAction implements View.OnClickListener, TextToSpeechEngine.OnTextToSpeechCallback {

    private static final String TAG = "PLAY_QUESTION_CLICK";
    private QuestionModel mQuestion;

    private ExcerciseActivity mActivity;

    private View mPlayButton;

    private ProgressWheel mProgressWheel;

    private boolean mIsPlaying = false;


    private Handler mHandler;


    public PlayQuestionClickAction(ExcerciseActivity activity, QuestionModel question){
        mQuestion=question;
        mActivity=activity;
        mHandler= new Handler(activity.getMainLooper());
    }

    @Override
    public void onClick(View v) {
        if(mPlayButton ==null)
            mPlayButton=v;
        mProgressWheel=(ProgressWheel)mPlayButton.getTag();
        if(mIsPlaying)
            stopPlaying();
        else
            startPlaying();
    }

    public void updateHolderViewsState(NpcQuestionItemList.QuestionViewHolder holder){
        return;
    }

    private void stopPlaying() {
        mPlayButton.setEnabled(true);
        ViewHelper.setBackground(mActivity,mPlayButton, R.mipmap.play_button);
    }

    private void startPlaying() {
        if(mQuestion.getType()== QuestionModel.Type.TEXT){
            mActivity.startSpeech(mQuestion.text, this);
            ViewHelper.setBackground(mActivity,mPlayButton, R.mipmap.pause_button);
            mPlayButton.setEnabled(false);
            return;
        }
        if(mQuestion.getType()== QuestionModel.Type.AUDIO){
            mActivity.startPlayAudio(mQuestion.urlAudio);
        }
    }

    @Override
    public void onTTSError(String message) {
        Log.e(TAG,"on TTSERROR");
        DialogFactory.showSimpleOneButtonDialog(mActivity,"TTS", message);
        stopPlaying();
    }

    @Override
    public void onReadyToSpeech(TextToSpeech textSpeech) {

    }

    @Override
    public void onDone() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                stopPlaying();
            }
        });
    }
}
