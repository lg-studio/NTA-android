package com.usinformatics.nytrip.ui.excercises;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.IntentConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.audio.SpeechRecognizerEngine;
import com.usinformatics.nytrip.audio.callbacks.OnGetTextCallback;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorderCallback;
import com.usinformatics.nytrip.audio.speech.TextToSpeechEngine;
import com.usinformatics.nytrip.databases.model.AudioModel;
import com.usinformatics.nytrip.managers.EduMaterialRepository;
import com.usinformatics.nytrip.managers.RepositoryCallback;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.models.types.ChatType;
import com.usinformatics.nytrip.network.voice.SendVoice;
import com.usinformatics.nytrip.services.media.CustomMediaPlayer;
import com.usinformatics.nytrip.storages.StorageFactory;
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

import java.util.Locale;

/**
 * Created by D1m11n on 06.07.2015.
 */
public class ExcerciseActivity  extends BaseActivity  {


    private SpeechRecognizerEngine mSpeechEngine;
    //private VoiceRecorder mRecorder;
    private SendVoice mSendVoice;
    private TextToSpeechEngine mTTSEngine;
    private TaskModel mTask;

    private ChatInfoFragment infoFragment;
    private ChatFragment mChatFragment;

    private CustomMediaPlayer mMediaPlayer;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_task_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initToolbarEngine(false);
        getTask();
    }

    private void getTask() {
        Intent intent= getIntent();
        if(intent==null||!intent.hasExtra(IntentConsts.Extra.TASK)){
            DialogFactory.showSimpleOneButtonDialog(ExcerciseActivity.this,"ERROR", "Task id error");
            return;
        }
        String taskId=intent.getStringExtra(IntentConsts.Extra.TASK);
        EduMaterialRepository.newInstance(ExcerciseActivity.this).getTask(StorageFactory.getUserStorage(ExcerciseActivity.this).getCurrentScene().sceneID, taskId, new RepositoryCallback<TaskModel>() {
            @Override
            public void onSuccess(TaskModel objects) {
                mTask = objects;
                if (mToolbarEngine == null) {
                    initToolbarEngine(false);
                }
                mToolbarEngine.setToolbarTitle(mTask.getName());
                displayChatInfo();
            }

            @Override
            public void onError(String error) {
                DialogFactory.showSimpleOneButtonDialog(ExcerciseActivity.this, "ERROR", "Task ERROR " + error);
            }
        });
    }

    public void displayChatInfo(){
        if(infoFragment==null)
           infoFragment = ChatInfoFragment.newInstance(mTask);
        mToolbarEngine.setVisibilityMenu(true);
        getFragmentManager().beginTransaction().replace(R.id.exercise_fragment,infoFragment).commit();
    }

    public void displayChat() {
        if(mChatFragment ==null)
              mChatFragment = ChatFragment.newInstance(mTask);
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

    public void displayResultChat(final float mark){
        if(mTask.chatType!=ChatType.TEACHER&&mark<AppConsts.MIN_MARK_SHOW_DIALOG){
            DialogFactory.showLowMarkInfoSelection(ExcerciseActivity.this, mTask.name, Integer.toString((int) (mark*100)) , new DialogFactory.OnOkClickListener() {
                @Override
                public void wasOkClicked(DialogInterface dialog, boolean isOk) {
                    finishChat();
                    if(isOk)
                        displayChatInfo();
                    else
                        getFragmentManager().beginTransaction().replace(R.id.exercise_fragment, ChatResultFragment.newInstance(mTask, mark)).commit();
                }
            });
            return;
        }
        finishChat();
        getFragmentManager().beginTransaction().replace(R.id.exercise_fragment, ChatResultFragment.newInstance(mTask, mark)).commit();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!mSpeechEngine.onActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result= super.onCreateOptionsMenu(menu);
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
        if(mTask.chatType == ChatType.RECOGNITION){
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
                if (isOk) {
                    ExcerciseActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishChat();
                            displayChatInfo();
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

    private void initCustomMediaPLayerIfNeeded(){
        if(mMediaPlayer==null)
            mMediaPlayer=new CustomMediaPlayer();
    }

    private void initVoiceRecorderIfNeeded(){
        if(mSendVoice==null)
            mSendVoice= new SendVoice(ExcerciseActivity.this, mTask.name);
    }

    public void startSpeechRecognize(OnGetTextCallback callback) {
        initSpeechRecognitionIfneeded();
        if(mTTSEngine!=null)
            mTTSEngine.stopSpeech();
        mSpeechEngine.startTextRecognizer(callback);
    }

    public void startPlayAudio(String urlAudio) {
        initCustomMediaPLayerIfNeeded();
        mMediaPlayer.playUrl(urlAudio);
    }

    public void startRecordVoice(final AudioModel audio, final VoiceRecorderCallback callback) {
        DialogFactory.showRecordDialog(ExcerciseActivity.this, AppConsts.MAX_RECORD_DURATIONs, new DialogFactory.RecordProcessCallback() {
            @Override
            public void started() {
                initVoiceRecorderIfNeeded();
                mSendVoice.startRecording(audio);
                if(callback!=null)
                    callback.onStart();
            }

            @Override
            public void stopped() {
                if(callback!=null) {
                    callback.onEnd("");
                }
                mSendVoice.stopRecording();
            }

            @Override
            public void aborted() {
               mSendVoice.stopRecording();
            }
        });
    }

    public void stopSpeech() {
        if(mTTSEngine!=null/*&&mTTSEngine.isRunning()*/)
            mTTSEngine.stopSpeech();
    }

    public void stopPlayAudio() {
        if(mMediaPlayer!=null&&mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
    }
}
