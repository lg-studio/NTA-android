package com.usinformatics.nytrip.ui.excercises;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.FakeData;
import com.usinformatics.nytrip.IntentConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.audio.SpeechRecognizerEngine;
import com.usinformatics.nytrip.audio.callbacks.OnGetTextCallback;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorder;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorderCallback;
import com.usinformatics.nytrip.audio.speech.TextToSpeechEngine;
import com.usinformatics.nytrip.models.AnswerModel;
import com.usinformatics.nytrip.models.ChatModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.services.media.CustomMediaPlayer;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;
import com.usinformatics.nytrip.ui.excercises.fragments.CharacterFragment;
import com.usinformatics.nytrip.ui.excercises.fragments.ChatFragment;
import com.usinformatics.nytrip.ui.excercises.fragments.ChatInfoFragment;
import com.usinformatics.nytrip.ui.excercises.fragments.ChatPlaceFragment;
import com.usinformatics.nytrip.ui.excercises.fragments.ChatResultFragment;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by D1m11n on 06.07.2015.
 */
public class ExcerciseActivity  extends BaseActivity  {


    private SpeechRecognizerEngine mSpeechEngine;
    private VoiceRecorder mRecorder;
    private TextToSpeechEngine mTTSEngine;
    private TaskModel mTask;
    private ChatModel mChat= FakeData.getChat();

    private ChatInfoFragment infoFragment;
    private ChatFragment mChatFragment;

    @Override
    public String getTag() {
        return "EXERCISE ACTIVITY";
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return null;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return R.id.tt_info;
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {
        if(currentItem==ToolbarActions.BACK)
            onBackPressed();
    }

//    private BroadcastReceiver mExcersizeReceiver= new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(intent==null||intent.getAction()==null) return;
//            if(intent.getAction()==IntentConsts.TTS){
//                initTTSEngineIfNeeded();
//                startTTS(intent.getStringExtra(IntentConsts.Extra.MESSAGE));
//            }
//        }
//    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_task_info);
        initToolbarEngine(false);
        mTask=getIntent().getParcelableExtra(IntentConsts.Extra.TASK);

    }

    public void displayEcerciseinfo(){
        if(infoFragment==null)
           infoFragment = ChatInfoFragment.newInstance(mTask);
        mToolbarEngine.setVisibilityMenu(true);
        getFragmentManager().beginTransaction().replace(R.id.exercise_fragment,infoFragment).commit();
    }

    public void displayChat() {
        if(mChatFragment ==null)
              mChatFragment = ChatFragment.newInstance(mChat);
        mToolbarEngine.setVisibilityMenu(false);
        getFragmentManager().beginTransaction().replace(R.id.exercise_fragment, mChatFragment).commit();
    }

    private void finishChat() {
        if(mChatFragment ==null)
            return;
        mToolbarEngine.setVisibilityMenu(false);
        mChatFragment.clearData();
        getFragmentManager().beginTransaction().remove(mChatFragment).commit();
        mChatFragment=null;
    }

    public void displayResultChat(float mark){
        finishChat();
        getFragmentManager().beginTransaction().replace(R.id.exercise_fragment, ChatResultFragment.newInstance(mChat.chatType, mark)).commit();
    }

    public void displayCharacter() {
        mToolbarEngine.setVisibilityMenu(false);
        getFragmentManager().beginTransaction().replace(R.id.info_fragment,CharacterFragment.newInstance(mTask.character)).commit();
    }

    public boolean hideCharacter() {
        Fragment frg=getFragmentManager().findFragmentById(R.id.info_fragment);
        if(frg==null)
            return false;
        if(!(frg instanceof CharacterFragment))
            return false;
        getFragmentManager().beginTransaction().remove(frg).commitAllowingStateLoss();
        mToolbarEngine.setVisibilityMenu(true);
        return true;
    }

    public void displayPlace() {
        mToolbarEngine.setVisibilityMenu(false);
        mToolbarEngine.setNavigationButton(true);
        getFragmentManager().beginTransaction().replace(R.id.info_fragment, ChatPlaceFragment.newInstance(mTask)).commit();
    }

    public boolean hidePlace() {
        Fragment frg=getFragmentManager().findFragmentById(R.id.info_fragment);
        if(frg==null)
            return false;
        if(!(frg instanceof ChatPlaceFragment))
            return false;
        mToolbarEngine.setNavigationButton(false);
        mToolbarEngine.setVisibilityMenu(true);
        getFragmentManager().beginTransaction().remove(frg).commit();
        return true;
    }

    private boolean isResultChatVisible() {
        Fragment frg=getFragmentManager().findFragmentById(R.id.info_fragment);
        if(frg==null)
            return false;
        if(!(frg instanceof ChatResultFragment))
            return false;
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mToolbarEngine==null){
            initToolbarEngine(false);
        }
        mToolbarEngine.setToolbarTitle(mTask.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!mSpeechEngine.onActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result= super.onCreateOptionsMenu(menu);
        displayEcerciseinfo();
        return result;
    }

    @Override
    public void onBackPressed() {
        if(dismissPopupIsNeeded())
            return;
        if(hideCharacter()||hidePlace())
            return;
        if(mChatFragment!=null){
            askEndChat();
            return;
        }
        if(isResultChatVisible()){
            askResultActionOrEnd();
            return;
        }
        super.onBackPressed();
    }

    private void askResultActionOrEnd() {
        if(mChat.chatType == ChatModel.ChatType.RECOGNITION){
            ExcerciseActivity.this.finish();
            return;
        }
        ExcerciseActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogFactory.showSimpleOneButtonDialog(ExcerciseActivity.this, "Chat", "Please, select what to do with results");
            }
        });
    }

    private void askEndChat() {
        DialogFactory.showSimpleTwoButtonsDialog(ExcerciseActivity.this, "Chat", "Do you want to stop chat", new DialogFactory.OnOkClickListener() {
            @Override
            public void wasOkClicked(DialogInterface dialog, boolean isOk) {
                if(isOk){
                    ExcerciseActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishChat();
                            displayEcerciseinfo();
                        }
                    });
                }
            }
        });
    }


    public void startSpeech(String message,TextToSpeechEngine.OnTextToSpeechCallback callback){
        initTTSEngineIfNeeded();
        mTTSEngine.startTextSpeecher(Locale.ENGLISH,message, callback);
    }

    private void initTTSEngineIfNeeded() {
        if(mTTSEngine==null)
            mTTSEngine= new TextToSpeechEngine(ExcerciseActivity.this);
    }

    private void initSpeechRecognitionIfneeded(){
        if(mSpeechEngine ==null)
            mSpeechEngine = new SpeechRecognizerEngine(ExcerciseActivity.this);
    }

    public void startSpeechRecognize(OnGetTextCallback callback) {
        initSpeechRecognitionIfneeded();
        mSpeechEngine.startTextRecognizer(callback);
    }

    public void showVariants(AnswerModel answer){
        mChatFragment.showVariants(answer);
    }

    public void startPlayAudio(String urlAudio) {
        new CustomMediaPlayer().playUrl(urlAudio);
    }

    public void startRecordVoice(VoiceRecorderCallback callback) {
        if(mRecorder==null)
            mRecorder= new VoiceRecorder(ExcerciseActivity.this);
        try {
            mRecorder.start(new File(AppConsts.APP_DIRECTORY_PATH + "/RESULT_80001.ogg"),callback);
            //TODO REMOVE EXCEPTION AND USE ERROR CALLBACK
        } catch (IOException e) {
            log("====RECORD ERROR====" + e.toString());
        }
    }
}
