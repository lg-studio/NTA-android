package com.usinformatics.nytrip.ui.excercises.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.PicassoHelper;
import com.usinformatics.nytrip.helpers.ToastHelper;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class ChatPlaceFragment extends Fragment {

    private static final String TAG = ChatPlaceFragment.class.getSimpleName();
    private TaskModel mPlace;
    private ExcerciseActivity mActivity;

    private ImageView mivPlacePhoto, mivLink;
    private TextView  mtvPlaceInfo, mtvPlaceLocation;
    private View mMap;


    public static ChatPlaceFragment newInstance(TaskModel place){
        ChatPlaceFragment fr= new ChatPlaceFragment();
        fr.mPlace=place;
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
        mRootView=inflater.inflate(R.layout.frg_place, null, false);
        findViews();
        initFoundedViews();
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.hidePlace();
            }
        });
        return mRootView;
    }

    private void findViews() {
        mivPlacePhoto=(ImageView)mRootView.findViewById(R.id.place_photo);
        mtvPlaceInfo=(TextView)mRootView.findViewById(R.id.place_info);
        mtvPlaceLocation=(TextView)mRootView.findViewById(R.id.place_location);
        mivLink=(ImageView)mRootView.findViewById(R.id.place_link);
        mMap=mRootView.findViewById(R.id.map_view);
    }

    private void initFoundedViews() {
//        if(TextUtils.isEmpty(mPlace.imageUrl))
//            Picasso.with(mActivity).load(R.mipmap.intro_image_1).into(mivPlacePhoto);
//        else
//            Picasso.with(mActivity).load(mPlace.getImageUrl()).into(mivPlacePhoto);
        Log.e(TAG,"IMAGE URL = " + mPlace.getImageUrl());
        PicassoHelper.showSimpleImage(mActivity, mPlace.getImageUrl(), R.mipmap.intro_image_1, mivPlacePhoto);
        mivPlacePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showSimple(getActivity(),"HELLO");
            }
        });
        mtvPlaceLocation.setText(TextUtils.isEmpty(mPlace.desc)?"----":mPlace.desc);
        mtvPlaceInfo.setText(TextUtils.isEmpty(mPlace.desc)?"---":mPlace.desc);
        mivLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showSimple(mActivity, "LINK IS HERE");
            }
        });
        initMap();
    }

    private void initMap() {
        if(mPlace.location==null){
            mMap.setVisibility(View.GONE);
            return;
        }
        mMap.setVisibility(View.VISIBLE);
        getActivity().getFragmentManager().beginTransaction().replace(mMap.getId(), ChatPlaceMapFragment.newInstance(mPlace.location.latLng,mPlace.getName())).commitAllowingStateLoss();
    }


}
