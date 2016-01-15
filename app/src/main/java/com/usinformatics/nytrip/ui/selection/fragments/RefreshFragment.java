package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usinformatics.nytrip.R;

/**
 * Created by D1m11n on 29.07.2015.
 */
public class RefreshFragment extends Fragment implements IFragment{


    @Override
    public Type getFragmentType() {
        return Type.REFRESH;
    }

    @Override
    public void updateContent() {
    }

    @Override
    public Fragment getInstance() {
        return RefreshFragment.this;
    }

    public interface OnClickListener{

        public void onCLick(IFragment.Type whatRefreshType);

    }

    private OnClickListener mListener;

    private IFragment mTargetFragment;


    public static RefreshFragment newInstance(IFragment targetFragment, OnClickListener listener){
        RefreshFragment fr= new RefreshFragment();
        fr.mListener=listener;
        fr.mTargetFragment=targetFragment;
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frg_refresh, null, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                    mListener.onCLick(mTargetFragment.getFragmentType());
            }
        });
        return v;
    }
}
