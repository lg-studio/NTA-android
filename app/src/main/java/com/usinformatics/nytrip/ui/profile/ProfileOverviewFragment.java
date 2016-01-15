package com.usinformatics.nytrip.ui.profile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.usinformatics.nytrip.R;

/**
 * Created by admin on 7/3/15.
 */
public class ProfileOverviewFragment extends Fragment implements IFragment {



    private String title;

    public static ProfileOverviewFragment newInstance(Activity activity){
        ProfileOverviewFragment f= new ProfileOverviewFragment();
        f.title=activity.getString(R.string.overview);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_profile_overview, container, false);
        initTasksStatistics(view);
        initEpisodesList(view);
        return view;
    }

    private void initEpisodesList(View view) {
        ListView episodesList = (ListView) view.findViewById(R.id.profile_episodes_list);

    }

    private void initTasksStatistics(View view) {
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Fragment getInstance() {
        return ProfileOverviewFragment.this;
    }

    @Override
    public void update() {

    }
}
