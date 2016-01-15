package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.network.NetworkErrorHelper;
import com.usinformatics.nytrip.network.NetworkUtils;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.ui.additional.toolbar.ExtToolbarEngine;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.adapters.TaskListPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class SceneWithTaskListFragment extends Fragment implements IFragment {


    private static final String TAG ="SCENE_TASKS_Fragment" ;
    private View mRootView;
    private ViewPager mViewPager;
    private TextView mtvEpisodeName;
    private TextView mtvSceneName;
    private TaskListPagerAdapter mTasksAdapter;

    private SceneModel mScene;
    private EpisodeModel mEpisode;
    private ExtToolbarEngine mToolbar;
    private TasksSelectionActivity mActivity;


    public static SceneWithTaskListFragment newInstance(EpisodeModel episode, SceneModel scene) {
        SceneWithTaskListFragment frg = new SceneWithTaskListFragment();
        frg.mEpisode=episode;
        frg.mScene = scene;
        return frg;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity= (TasksSelectionActivity) activity;
        mToolbar=(ExtToolbarEngine)mActivity.getToolbar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_scenes_with_tasks, container, false);
        findViews();
        initViews();
        return mRootView;
    }

    private void findViews() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_frg_tasks);
        mtvEpisodeName = (TextView) mRootView.findViewById(R.id.tv_episode_name);
        mtvSceneName=(TextView)mRootView.findViewById(R.id.tv_scene_name);

    }

    private void initViews() {
        mtvEpisodeName.setText(String.valueOf(mEpisode.name));
        mtvSceneName.setText(String.valueOf("Scene : " + mScene.name));
        tryGetTasks();
    }

    private void tryGetTasks() {
        RequestExecutor.getInstance(mActivity).tasks(mScene.sceneID /*FakeData.SCENE_ID*/, new OnServerResponseCallback<TaskModel[]>() {


            @Override
            public void onResponse(TaskModel[] objects, Response responseBody, RetrofitError error) {
                if (NetworkErrorHelper.showNetworkErrorDialogIfNeeded(mActivity, error))
                    return;
                displayTasks(objects);
                Log.e(TAG,"response = " + NetworkUtils.getResponseBody(responseBody));
                Log.e(TAG,"tasks = " + TaskModel.toString(objects));
            }
        });
    }

    private void displayTasks(TaskModel[] objects) {
        mViewPager.setAdapter(new TaskListPagerAdapter(mActivity, new ArrayList<TaskModel>(Arrays.asList(objects)), getTaskPath()));
    }


    @Override
    public Type getFragmentType() {
        return Type.TASKS;
    }


    public void updateContent(){
        //http://stackoverflow.com/questions/11326155/fragment-onresume-onpause-is-not-called-on-backstack
    //TODO ADD HERE UPDATING DATA/VIEW/ if needed, for ex. when instead of creating always new instance, you use one object with replace/or stack of fragment
    }

    @Override
    public Fragment getInstance() {
        return SceneWithTaskListFragment.this;
    }

    public String getTaskPath() {
        return mtvEpisodeName.getText()+"/"+mtvSceneName.getText();
    }
}
