package com.usinformatics.nytrip.ui.profile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usinformatics.nytrip.R;

/**
 * Created by admin on 7/3/15.
 */
public class ProfileStatisticFragment extends Fragment implements IFragment {


    private String title;

    public static ProfileStatisticFragment newInstance(Activity activity) {
        ProfileStatisticFragment f=new ProfileStatisticFragment();
        f.title=activity.getString(R.string.statistics);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_profile_statistic, container, false);
        return view;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Fragment getInstance() {
        return ProfileStatisticFragment.this;
    }

    @Override
    public void update() {

    }
}
