package com.usinformatics.nytrip.ui.excercises.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.ToastHelper;
import com.usinformatics.nytrip.models.CharacterModel;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;

import common.picasso.transformations.CircleTransform;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class CharacterFragment extends Fragment {

    private CharacterModel mCharacter;
    private ExcerciseActivity mActivity;

    private ImageView mivCharacterPhoto;
    private TextView mtvCharacterName, mtvCharacterPost, mtvCharacterInfo, mtvCharacterLocation;




    public static CharacterFragment newInstance(CharacterModel character){
        CharacterFragment fr= new CharacterFragment();
        fr.mCharacter=character;
        return fr;
    }

    private View mRootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=(ExcerciseActivity)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.frg_excersize_character, null, false);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()!=R.id.character_photo)
                  mActivity.hideCharacter();
            }
        });
        findViews();
        initFoundedViews();
        return mRootView;
    }

    private void findViews() {
        mivCharacterPhoto=(ImageView)mRootView.findViewById(R.id.character_photo);
        mtvCharacterInfo=(TextView)mRootView.findViewById(R.id.character_info);
        mtvCharacterLocation=(TextView)mRootView.findViewById(R.id.character_location);
        mtvCharacterName=(TextView)mRootView.findViewById(R.id.character_name);
        mtvCharacterPost=(TextView)mRootView.findViewById(R.id.character_post);
    }

    private void initFoundedViews() {
        if(TextUtils.isEmpty(mCharacter.getImageUrl()))
        Picasso.with(mActivity).load(R.mipmap.ic_character).transform(new CircleTransform()).into(mivCharacterPhoto);
        else
            Picasso.with(mActivity).load(mCharacter.getImageUrl()).error(R.mipmap.ic_character).transform(new CircleTransform()).into(mivCharacterPhoto);
        mivCharacterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showSimple(getActivity(),"HELLO");
            }
        });
        if(TextUtils.isEmpty(mCharacter.name))
            mtvCharacterName.setText("---");
        else
            mtvCharacterName.setText(mCharacter.name);
        mtvCharacterPost.setText("---");
        mtvCharacterLocation.setText("----");
        mtvCharacterInfo.setText(TextUtils.isEmpty(mCharacter.desc)?"---":mCharacter.desc);
    }


}
