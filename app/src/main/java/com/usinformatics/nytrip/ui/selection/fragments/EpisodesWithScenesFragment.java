package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.managers.EduMaterialRepository;
import com.usinformatics.nytrip.managers.RepositoryCallback;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.network.models.NetGroupModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.additional.toolbar.ExtToolbarEngine;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarMode;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.adapters.ScenesExpandListAdapter;
import com.usinformatics.nytrip.ui.selection.adapters.TaskListPagerAdapter;
import com.usinformatics.nytrip.ui.selection.models.EpisodesScenesListModel;

import java.util.HashMap;
import java.util.List;

import common.utis.ListsUtils;

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

    private ProgressDialog mProgressDialog;


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
        mProgressDialog = DialogFactory.newProgressDialog(activity);
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
        return mRootView;
    }

    private void findViews() {
        mExpandableListView = (ExpandableListView) mRootView.findViewById(R.id.lv_act_scenes);
    }

    private void getClasses(final Activity activity){
        EduMaterialRepository.newInstance(activity).getClasses(new RepositoryCallback<NetGroupModel[]>() {
            @Override
            public void onSuccess(NetGroupModel[] objects) {
                if(ListsUtils.isEmpty(objects)){
                    mProgressDialog.dismiss();
                    DialogFactory.showSimpleOneButtonDialog(mActivity, "Empty", "Classes are empty");
                    mActivity.displayRefreshFragment(EpisodesWithScenesFragment.this);
                    mToolbar.setIsEnabled(true);
                    return;
                }
                if(!TextUtils.isEmpty(objects[0].currentCourse))
                   StorageFactory.getUserStorage(activity).setCurrentCourseId(objects[0].currentCourse);
                else
                    StorageFactory.getUserStorage(activity).setCurrentCourseId(objects[0].coursesIDs[0]);
                tryGetCourses(activity);
            }

            @Override
            public void onError(String error) {
                mProgressDialog.dismiss();
                mToolbar.setIsEnabled(true);
                DialogFactory.showSimpleOneButtonDialog(activity,"Error", error);
                mActivity.displayRefreshFragment(EpisodesWithScenesFragment.this);
            }
        });
    }

    private void tryGetCourses(final Activity activity) {
        Log.e(TAG,"===courses");
        if(mToolbar!=null)
           mToolbar.setIsEnabled(false);
        EduMaterialRepository.newInstance(activity).getCourses(StorageFactory.getUserStorage(activity).getUser().getClasses(), new RepositoryCallback<CourseModel[]>() {
            @Override
            public void onSuccess(CourseModel[] objects) {
                if(ListsUtils.isEmpty(objects)){
                    mProgressDialog.dismiss();
                    mActivity.displayRefreshFragment(EpisodesWithScenesFragment.this);
                    DialogFactory.showSimpleOneButtonDialog(mActivity, "Empty", "Courses are empty");
                    mToolbar.setIsEnabled(true);
                    return;
                }
                StorageFactory.getEduStorage(activity).saveCourses(objects);
                tryGetEpisodesAndScenes(activity);
            }

            @Override
            public void onError(String error) {
                mProgressDialog.dismiss();
                mActivity.displayRefreshFragment(EpisodesWithScenesFragment.this);
                mToolbar.setIsEnabled(true);
                DialogFactory.showSimpleOneButtonDialog(mActivity, "Error", error);
            }
        });
    }

    private void tryGetEpisodesAndScenes(final Activity activity){
        if(mToolbar!=null)
            mToolbar.setIsEnabled(false);
        EduMaterialRepository.newInstance(getActivity()).getEpisodes(StorageFactory.getUserStorage(activity).getCurrentCourseId(), new RepositoryCallback<List<EpisodeModel>>() {
            @Override
            public void onSuccess(List<EpisodeModel> objects) {
                mToolbar.setIsEnabled(true);
                mProgressDialog.dismiss();
                if(ListsUtils.isEmpty(objects)){
                    mActivity.displayRefreshFragment(EpisodesWithScenesFragment.this);
                    DialogFactory.showSimpleOneButtonDialog(mActivity, "Empty", "Episodes/scenes are empty");
                    return;
                }
                displayEpisodeScenesScene(activity, objects);
            }

            @Override
            public void onError(String error) {
                mProgressDialog.dismiss();
                mActivity.displayRefreshFragment(EpisodesWithScenesFragment.this);
                mToolbar.setIsEnabled(true);
                DialogFactory.showSimpleOneButtonDialog(mActivity, "Error", error);
            }
        });
    }

    private void displayEpisodeScenesScene(final Activity activity, final List<EpisodeModel> episodes){
        HashMap<EpisodeModel, SceneModel[]> map = new HashMap<EpisodeModel, SceneModel[]>();
        for (EpisodeModel ep:episodes)
            map.put(ep, ep.scenes);
        updateExpandableList(episodes, map);
        mToolbar.setIsEnabled(true); //TODO MOVE CONTROL OF TOOLBAR TO ACTIVITY HERE IS NULL
    }

    private void updateExpandableList(List<EpisodeModel> episodes,  HashMap<EpisodeModel,SceneModel[]> scenes) {
        ScenesExpandListAdapter adapter = new ScenesExpandListAdapter(mActivity,new EpisodesScenesListModel(episodes, scenes));
        mExpandableListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateContent();
        if(mToolbar!=null)
           mToolbar.setToolbarTitle("Scene selection",ToolbarMode.SIMPLE);
    }

    private void startDataAggregators() {
        String courseId=StorageFactory.getUserStorage(getActivity()).getCurrentCourseId();
        mProgressDialog.show();
        if(TextUtils.isEmpty(courseId))
            getClasses(getActivity());
        else
            tryGetEpisodesAndScenes(getActivity());
    }

//    //TODO REINSTANCE TOOLBAR IF  NEEDED
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.e(TAG, "FRAGMENT_CREATE_OPTIONS_MENU");
//        if (mToolbar!=null)
//            mToolbar.updateToolbarView("Scene selection",ToolbarMode.SIMPLE);
//        super.onCreateOptionsMenu(menu,inflater);
//    }


//
//    private boolean isError(Activity activity, RetrofitError error) {
//        if(!NetworkErrorHelper.showNetworkErrorDialogIfNeeded(activity, error))
//            return false;
//        mToolbar.setIsEnabled(true);
//        return true;
//    }



    @Override
    public void updateContent(){
         //tryGetCourses(mActivity);
        startDataAggregators();
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
