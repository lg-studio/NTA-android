package com.usinformatics.nytrip.ui.excercises.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usinformatics.nytrip.IntentConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.models.types.ChatType;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;


/**
 * Created by D1m11n on 17.07.2015.
 */
public class ChatResultFragment extends Fragment implements View.OnClickListener {

    private TaskModel mTask;
    private float mMark;

    private View mRootView;
    private TextView mTextMark, mPercentMark, mSendToTeacher;



    public static ChatResultFragment newInstance(TaskModel task, float mark){
        ChatResultFragment fr= new ChatResultFragment();
        fr.mTask =task;
        fr.mMark =mark;
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.frg_excercise_result, null, false);
        findViews();
        initViews();
        return mRootView;
    }

    private void findViews() {
        mTextMark = (TextView) mRootView.findViewById(R.id.text_mark);
        mPercentMark= (TextView) mRootView.findViewById(R.id.percents_mark);
        mSendToTeacher=(TextView)mRootView.findViewById(R.id.send_to_teacher);
    }

    private void initViews() {
        setMark();
        mRootView.findViewById(R.id.try_again).setOnClickListener(this);
        mRootView.findViewById(R.id.back_to_scene).setOnClickListener(this);
        mRootView.findViewById(R.id.next_task).setOnClickListener(this);
    }

    private void setMark() {
        mTextMark.setText(getTextMark((int)(mMark*100)));
        if(mTask.chatType== ChatType.RECOGNITION){
            mSendToTeacher.setVisibility(View.GONE);
            mPercentMark.setVisibility(View.VISIBLE);
            mPercentMark.setText(calculateResultInPercents());
        }else{
            mSendToTeacher.setVisibility(View.VISIBLE);
            mPercentMark.setVisibility(View.GONE);
            mSendToTeacher.setOnClickListener(this);
        }
    }

    private String calculateResultInPercents() {
        return (int)(mMark*100) + "%";
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.try_again:
                tryTaskAgain(); return;
            case R.id.back_to_scene:
                backToScene(); return;
            case R.id.next_task:
                nextTask(); return;
            case R.id.send_to_teacher:
                sendToTeacher(); return;
        }

    }

    private void tryTaskAgain() {
        ((ExcerciseActivity)getActivity()).displayChatInfo();
    }

    private void backToScene() {
        Intent intent= new Intent(getActivity(), TasksSelectionActivity.class);
        intent.putExtra(IntentConsts.Extra.SCENES,"");
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    private void nextTask() {
        Intent intent= new Intent(getActivity(), TasksSelectionActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    private void sendToTeacher() {

    }

    private String getTextMark(int markInPercent){
        if(markInPercent<20)
            return getActivity().getString(R.string.Insufficient);
        if(markInPercent<60)
            return getActivity().getString(R.string.Low);
        if(markInPercent<70)
            return getActivity().getString(R.string.Okay);
        if(markInPercent<80)
            return getActivity().getString(R.string.Fairly_good);
        if(markInPercent<90)
            return getActivity().getString(R.string.Good);
        return getActivity().getString(R.string.Excellent);
    }
}
