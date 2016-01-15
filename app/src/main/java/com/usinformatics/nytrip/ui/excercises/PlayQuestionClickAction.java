package com.usinformatics.nytrip.ui.excercises;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.ViewHelper;
import com.usinformatics.nytrip.models.QuestionModel;
import com.usinformatics.nytrip.models.types.QuestionType;
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
            stopPlaying(true);
        else
            startPlaying();
    }

    public void updateHolderViewsState(NpcQuestionItemList.QuestionViewHolder holder){
        holder.massage.setText(mQuestion.getText());
        holder.playStopBtn.setTag(holder.progressWheel);
        if(mIsPlaying)
            ViewHelper.setBackground(mActivity, holder.playStopBtn, R.mipmap.pause_button);
        else
            ViewHelper.setBackground(mActivity, holder.playStopBtn, R.mipmap.play_button);
        mPlayButton=holder.playStopBtn;
        //holder.playStopBtn.setTag(holder);
        mPlayButton.setOnClickListener(this);
        return;
    }

    private void stopPlaying(boolean nativeStop) {
        mIsPlaying=false;
        ViewHelper.setBackground(mActivity,mPlayButton, R.mipmap.play_button);
        if(mQuestion.type== QuestionType.TEXT&&nativeStop) {
            mActivity.stopSpeech();
            return;
        }
        if(mQuestion.type== QuestionType.AUDIO&&nativeStop){
            mActivity.stopPlayAudio();
        }

    }

    private void startPlaying() {
        mIsPlaying=true;
        ViewHelper.setBackground(mActivity,mPlayButton, R.mipmap.pause_button);
        if(mQuestion.type== QuestionType.TEXT){
            mActivity.startSpeech(mQuestion.getText(), this);
            return;
        }
        if(mQuestion.type== QuestionType.AUDIO){
            mActivity.startPlayAudio(mQuestion.urlAudioFromTeacher);
        }
    }

    @Override
    public void onTTSError(final String message) {
        Log.e(TAG,"on TTSERROR");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DialogFactory.showSimpleOneButtonDialog(mActivity,"TTS", message);
            }
        });
        stopPlaying(false);
    }

    @Override
    public void onReadyToSpeech(TextToSpeech textSpeech) {

    }

    @Override
    public void onDone() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                stopPlaying(false);
            }
        });
    }
}
