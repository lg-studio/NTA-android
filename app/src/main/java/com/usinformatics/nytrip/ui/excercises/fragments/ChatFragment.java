package com.usinformatics.nytrip.ui.excercises.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.audio.RecognitionEstimator;
import com.usinformatics.nytrip.audio.callbacks.OnGetTextCallback;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorderCallback;
import com.usinformatics.nytrip.databases.model.AudioModel;
import com.usinformatics.nytrip.models.ExecutedAnswerModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.models.types.ChatType;
import com.usinformatics.nytrip.preferences.PrefsUser;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.excercises.ChatAdapter;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemList;
import com.usinformatics.nytrip.ui.excercises.items.NpcQuestionItemList;
import com.usinformatics.nytrip.ui.excercises.items.StudentAnswerItemList;

import java.util.ArrayList;
import java.util.List;

import common.utis.ListsUtils;

/**
 * Created by admin on 7/7/15.
 */
public class ChatFragment extends Fragment {


    private static final int MAX_VARIANTS=4;
    private static final String TAG = "CHAT_FRAGMENT" ;

    private View mRootView;
    private View mAnswersLayout;
    private View mMicLayout;
    private TextView [] mVariants;
    private TextView mSelectedVariant;
    private ImageView mMic;
    private ListView mExerciseChatList;

    private ChatAdapter mChatAdapter;
    private StudentAnswerItemList mStudentAnswerNotifier;
    private NpcQuestionItemList mQuestionNotifier;
    private int mItemChatIndex =0;
    private TaskModel mTask;
    private float mSummary;




    public static ChatFragment newInstance(TaskModel task){
        ChatFragment fr= new ChatFragment();
        fr.mTask=task;
        return fr;
    }

    private void printChatElements() {
        for(int i=0; i<mTask.getChat().questions.length; i++)
            Log.e(TAG,mTask.getChat().questions[i].toString());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_exercise_chat, container, false);
        findViews();
        initAdapter();
        initViews();
        printChatElements();
        return mRootView;
    }

    private void findViews() {
        mAnswersLayout=mRootView.findViewById(R.id.lt_variants);
        mMicLayout=mRootView.findViewById(R.id.lt_selected_answer);
        mVariants= new TextView[MAX_VARIANTS];
        for(int i=0; i<MAX_VARIANTS; i++) {
            mVariants[i] = (TextView) mRootView.findViewWithTag("variant" + i);//android:tag="variant0"
            mVariants[i].setOnClickListener(mVariantClickListener);
        }
        mSelectedVariant=(TextView)mRootView.findViewById(R.id.selected_variant);
        mMic=(ImageView)mRootView.findViewById(R.id.mic);
    }

    private void initViews() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mItemChatIndex ==0&&mChatAdapter.getCount()==0) {
            mSummary =0.0F;
            displayNextQuestion();
        }
    }

    private void displayNextQuestion(){
        if (mItemChatIndex >= mTask.getChat().questions.length) {
            ((ExcerciseActivity)getActivity()).displayResultChat(getResult(mItemChatIndex));
            return;
        }
        mQuestionNotifier.notify(mTask.getChat().questions[mItemChatIndex], 0);
      //  if(mTask.chatType== ChatType.RECOGNITION) {
            showVariants(mTask.getChat().questions[mItemChatIndex].getVariants());
//        }else{
//            showVoiceMic();
//        }
        mChatAdapter.notifyDataSetChanged();
        scrollListViewToBottom();
    }

    private void showVoiceMic() {
    }


    private void initAdapter() {
        mExerciseChatList = (ListView)  mRootView.findViewById(R.id.exercise_chat_list);
        ArrayList<ChatItemList> differentView = new ArrayList<>();
        mStudentAnswerNotifier = new StudentAnswerItemList((ExcerciseActivity) getActivity());
        mQuestionNotifier = new NpcQuestionItemList((ExcerciseActivity) getActivity(), mTask.character);
        differentView.add(mQuestionNotifier);
        differentView.add(mStudentAnswerNotifier);
        mChatAdapter = new ChatAdapter(getActivity(),differentView);
        mExerciseChatList.setAdapter(mChatAdapter);
    }

    public void showVariants(String[] variants) {
        mAnswersLayout.setVisibility(View.VISIBLE);
        mMicLayout.setVisibility(View.GONE);
        for(int i=0; i<MAX_VARIANTS; i++){
            mVariants[i].setVisibility(View.GONE);
        }
        if(ListsUtils.isEmpty(variants)){
            mVariants[0].setVisibility(View.VISIBLE);
            mVariants[0].setText(AppConsts.GOODBYE);
            return;
        }
        for(int i=0; i<variants.length; i++){
            mVariants[i].setVisibility(View.VISIBLE);
            mVariants[i].setText(String.valueOf(variants[i]));
        }
    }

    private View.OnClickListener mVariantClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text=((TextView)v).getText().toString();
            if(text.equals(AppConsts.GOODBYE)){
                ((ExcerciseActivity)getActivity()).displayResultChat(getResult(mItemChatIndex-1));
                return;
            }
            mSelectedVariant.setText(text);
            showMic();
            startAnswering();
        }
    };

    private void showMic() {
        mAnswersLayout.setVisibility(View.GONE);
        mMicLayout.setVisibility(View.VISIBLE);
        mMic.setOnClickListener(mOnMicClickListener);
    }

    private View.OnClickListener mOnMicClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startAnswering();
        }
    };

    private void startAnswering(){
       if(mTask.chatType== ChatType.RECOGNITION){
           startRecognition();
       }else
           startRecording(saveAudioInBD());
    }

    private AudioModel saveAudioInBD() {
        AudioModel audioModel = new AudioModel();
        audioModel.setEpisode(PrefsUser.getInstance(getActivity()).getCurrentEpisode().name);
        audioModel.setScene(PrefsUser.getInstance(getActivity()).getCurrentScene().name);
        audioModel.setTask(PrefsUser.getInstance(getActivity()).getCurrentTaskPath());
        audioModel.setQuestion(mTask.getChat().questions[mItemChatIndex].getText());
        audioModel.setAudioId(mTask.getChat().questions[mItemChatIndex].audioId);
        return audioModel;
    }

    private void startRecognition() {
                ((ExcerciseActivity)getActivity()).startSpeechRecognize(new OnGetTextCallback() {
                    @Override
                    public void onGetSpokenText(List<String> texts, float[] marks) {
                        logResults(texts, marks);
                        handleListening(texts);
                    }

                    @Override
                    public void onError(String error) {
                        DialogFactory.showSimpleOneButtonDialog(getActivity(), "Recognition Error", error);
                    }
                });
    }

    private void startRecording(AudioModel audio) {
        ((ExcerciseActivity)getActivity()).startRecordVoice(audio, new VoiceRecorderCallback() {
            @Override
            public void onStart() {
                Log.e(TAG, "RECORD START");
            }

            @Override
            public void onEnd(String fileName) {
                Log.e(TAG, "RECORD END " + fileName);
                handleListening(null);
            }

            @Override
            public void onError(RecordError error, String message) {
                Log.e(TAG, "RECORD ERROR " + error.toString() + ", " + message);
            }
        });
    }

    private void handleListening(List<String> texts){
        ExecutedAnswerModel ex= new ExecutedAnswerModel();
        ex.selectedAnswer=mSelectedVariant.getText().toString();
        if(texts!=null) {
            ex.mark = RecognitionEstimator.getBestEstimateOf(ex.selectedAnswer, texts, RecognitionEstimator.EstimatorMode.LEVENSHTEIN);
        }
            mSummary +=ex.mark;
        mStudentAnswerNotifier.notify(ex,0);
        mChatAdapter.notifyDataSetChanged();
        scrollListViewToBottom();
        mItemChatIndex++;
        displayNextQuestion();

    }

    private void logResults(List<String> text, float[] marks){

        for(int i=0; i<text.size(); i++){
            Log.e(TAG,"RES " + i+ " = " + text.get(i));
            if(marks==null||marks.length==0)
                continue;
            Log.e(TAG,"RES " + i+ " = " + (i>marks.length?"-1":marks[i]));
        }
    }

    private void scrollListViewToBottom() {
        if(mExerciseChatList==null)
            return;
        mExerciseChatList.post(new Runnable() {
            @Override
            public void run() {
                mExerciseChatList.setSelection(mExerciseChatList.getAdapter().getCount() - 1);
            }
        });
    }

    public float getResult(int divider){
        int div=divider==0?1:divider;
        return mSummary / div;
    }

    public void clearData(){

    }
}
