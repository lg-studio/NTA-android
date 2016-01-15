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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.network.NetworkErrorHelper;
import com.usinformatics.nytrip.network.NetworkUtils;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.network.models.CourseListModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.additional.toolbar.ExtToolbarEngine;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarMode;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.adapters.ScenesExpandListAdapter;
import com.usinformatics.nytrip.ui.selection.adapters.TaskListPagerAdapter;
import com.usinformatics.nytrip.ui.selection.models.EpisodesScenesListModel;

import java.util.HashMap;

import common.utis.ListsUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class EpisodesWithScenesFragment extends Fragment implements IFragment {


    private static final String TAG ="EPISODES_SCENES" ;
    private View mRootView;
    private ViewPager mViewPager;
    private TextView mtvTitle;
    private TaskListPagerAdapter mTasksAdapter;
    private TasksSelectionActivity mActivity;

    private ExpandableListView mExpandableListView;
    private ExtToolbarEngine mToolbar;


    public static EpisodesWithScenesFragment newInstance() {
        EpisodesWithScenesFragment frg = new EpisodesWithScenesFragment();
        return frg;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(TAG,"ON ATTACH");
        mActivity= (TasksSelectionActivity) activity;
        mToolbar=(ExtToolbarEngine)mActivity.getToolbar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_episodes_with_scenes, container, false);
        findViews();
         //TODO UPDATE ACTIVITY
        getClasses(getActivity());
        return mRootView;
    }

    private void findViews() {
        mExpandableListView = (ExpandableListView) mRootView.findViewById(R.id.lv_act_scenes);
    }

    private void getClasses(final Activity activity){
        RequestExecutor.getInstance(activity).coursesList(new OnServerResponseCallback<CourseListModel[]>() {
            @Override
            public void onResponse(CourseListModel[] objects, Response responseBody, RetrofitError error) {
                if (NetworkErrorHelper.showNetworkErrorDialogIfNeeded(mActivity, error)) {
                    return;
                }
                if (ListsUtils.isEmpty(objects)) {
                    DialogFactory.showSimpleOneButtonDialog(mActivity, "ERROR GROUP", "List of groups is empty");
                    return;
                }
                StorageFactory.getUserStorage(activity).setCurrentCourseId(objects[0].currentCourse);
                Log.e(TAG, "group " + CourseListModel.toString(objects));
                Log.e(TAG, "current semester " + StorageFactory.getUserStorage(activity).getCurrentCourseId());
                tryGetCourses(mActivity);
            }
        });
    }

    private void tryGetCourses(final Activity activity) {
        Log.e(TAG,"===courses");
        if(mToolbar!=null)
           mToolbar.setIsEnabled(false);
        RequestExecutor.getInstance(activity).courses(StorageFactory.getUserStorage(activity).getUser().getClasses()[0], new OnServerResponseCallback<CourseModel[]>() {
            @Override
            public void onResponse(CourseModel[] objects, Response responseBody, RetrofitError error) {
                if (isError(activity, error))
                    return;
                Log.e(TAG,"response = " + NetworkUtils.getResponseBody(responseBody));
                if(ListsUtils.isEmpty(objects)){
                    DialogFactory.showSimpleOneButtonDialog(mActivity, "Empty", "Semesters are empty");
                    mToolbar.setIsEnabled(true);
                    return;
                }
                StorageFactory.getEduStorage(activity).saveCourses(objects);
                tryGetEpisodesAndScenes(activity, objects);
            }
        });
    }

    private void tryGetEpisodesAndScenes(final Activity activity, final CourseModel[] semesters){
        RequestExecutor.getInstance(activity).episodesWithScenes(StorageFactory.getUserStorage(activity).getCurrentCourseId(), new OnServerResponseCallback<EpisodeModel[]>() {
            @Override
            public void onResponse(EpisodeModel[] objects, Response responseBody, RetrofitError error) {
                Log.e(TAG,"====episodes");
                if (isError(activity, error))
                    return;
                Log.e(TAG,"response episdes= " + EpisodeModel.toString(objects));
                Log.e(TAG,"response  array = " + NetworkUtils.getResponseBody(responseBody));
                if(ListsUtils.isEmpty(objects)){
                    DialogFactory.showSimpleOneButtonDialog(mActivity, "Empty", "Episodes/scenes are empty");
                    mToolbar.setIsEnabled(true);
                    return;
                }
                tryGetScene(activity, objects);
            }
        });
    }

    private void tryGetScene(final Activity activity, final EpisodeModel[] episodes){
        if (ListsUtils.isEmpty(episodes)) return;
        HashMap<EpisodeModel, SceneModel[]> map = new HashMap<EpisodeModel, SceneModel[]>();
        for (int i = 0; i < episodes.length; i++)
            map.put(episodes[i], episodes[i].scenes);
        updateExpandableList(episodes, map);
        mToolbar.setIsEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mToolbar!=null)
           mToolbar.setToolbarTitle("Scene selection",ToolbarMode.SIMPLE);
    }

//    //TODO REINSTANCE TOOLBAR IF  NEEDED
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.e(TAG, "FRAGMENT_CREATE_OPTIONS_MENU");
//        if (mToolbar!=null)
//            mToolbar.updateToolbarView("Scene selection",ToolbarMode.SIMPLE);
//        super.onCreateOptionsMenu(menu,inflater);
//    }



    private boolean isError(Activity activity, RetrofitError error) {
        if(!NetworkErrorHelper.showNetworkErrorDialogIfNeeded(activity, error))
            return false;
        mToolbar.setIsEnabled(true);
        return true;
    }

    private void updateExpandableList( EpisodeModel [] episodes,  HashMap<EpisodeModel,SceneModel[]> scenes) {
        ScenesExpandListAdapter adapter = new ScenesExpandListAdapter(mActivity,new EpisodesScenesListModel(episodes, scenes));
        mExpandableListView.setAdapter(adapter);
    }

    @Override
    public void updateContent(){
         tryGetCourses(mActivity);
    }

    @Override
    public Fragment getInstance() {
        return EpisodesWithScenesFragment.this;
    }



    @Override
    public Type getFragmentType() {
        return Type.EPISODES;
    }
}
