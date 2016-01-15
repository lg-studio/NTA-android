package com.usinformatics.nytrip.ui.excercises.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;

import common.picasso.transformations.CircleTransform;
import common.utis.ScreenUtils;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class ChatInfoFragment extends Fragment{

    private TaskModel mTask;
    private ExcerciseActivity mExcerciseActivity;
    private View mRootView;

    private static final int BACKGROUND_HEIGHTdp=168;

    private View mBackgroundView;

    private ImageView mivTaskPhoto, mivCharacterPhoto, mivCompass;

    private TextView mtvCharacterName, mtvCharacterPost,  mtvTaskDescription, mtvTaskName, mtvRating;

    private RatingBar mRatingbar;

    private Button mbtnGetStarted;


    public static ChatInfoFragment newInstance(TaskModel task){
        ChatInfoFragment fr= new ChatInfoFragment();
        fr.mTask=task;
        return fr;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mExcerciseActivity=(ExcerciseActivity)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.frg_excercise_info, null, false);
        findViews();
        initFoundedViews();
        return mRootView;
    }

    private void findViews(){
        mBackgroundView=mRootView.findViewById(R.id.iv_top_background);
        mtvCharacterName=(TextView)mRootView.findViewById(R.id.tv_character_name);
        mtvCharacterPost=(TextView)mRootView.findViewById(R.id.tv_character_post);
        mtvRating=(TextView)mRootView.findViewById(R.id.tv_rating);
        mivCharacterPhoto=(ImageView)mRootView.findViewById(R.id.iv_character_photo);
        mRatingbar=(RatingBar)mRootView.findViewById(R.id.rating);
        mtvTaskDescription=(TextView)mRootView.findViewById(R.id.tv_task_descrpition);
        mtvTaskName=(TextView)mRootView.findViewById(R.id.tv_task_title);
        mivTaskPhoto=(ImageView)mRootView.findViewById(R.id.iv_task_image);
        mbtnGetStarted=(Button)mRootView.findViewById(R.id.btn_task_start);
        mivCompass=(ImageView)mRootView.findViewById(R.id.iv_compass);
    }

    private void initFoundedViews() {
        updateHeightOfBackground();
        mRootView.findViewById(R.id.lt_character).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExcerciseActivity.displayCharacter();
            }
        });
        mRootView.findViewById(R.id.lt_place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExcerciseActivity.displayPlace();
            }
        });
        mtvCharacterName.setText(String.valueOf(mTask.character.name));
        mtvCharacterPost.setText(String.valueOf(mTask.character.desc));
        if(mTask.character.getImageUrl()==null)
            Picasso.with(mExcerciseActivity).load(R.mipmap.ic_character).transform(new CircleTransform()).into(mivCharacterPhoto);
        else
            Picasso.with(mExcerciseActivity).load(mTask.character.getImageUrl()).error(R.mipmap.ic_character).transform(new CircleTransform()).into(mivCharacterPhoto);
        mtvTaskDescription.setText(mTask.getDesc());
        mtvTaskName.setText(mTask.getName());
        mRatingbar.setRating(mTask.rating);
        mtvRating.setText(String.valueOf(mTask.rating));
        mbtnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExcercise();
            }
        });
        mivCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExcerciseActivity.displayPlace();
            }
        });
    }

    private void startExcercise() {
        mExcerciseActivity.displayChat();
    }

    private void updateHeightOfBackground() {
        int px= (int) ScreenUtils.dpToPixels(mExcerciseActivity,BACKGROUND_HEIGHTdp);
        TypedValue tv = new TypedValue();
        int actionBarHeight=0;
        if (mExcerciseActivity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
           actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        mBackgroundView.setLayoutParams(new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, px-actionBarHeight));
    }

}
