package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.managers.EduMaterialRepository;
import com.usinformatics.nytrip.managers.RepositoryCallback;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.additional.toolbar.ExtToolbarEngine;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;
import com.usinformatics.nytrip.ui.selection.adapters.TaskListPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import common.utis.ListsUtils;

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
    private ProgressDialog mProgressDialog;


    public static SceneWithTaskListFragment newInstance(/*EpisodeModel episode, SceneModel scene*/) {
        SceneWithTaskListFragment frg = new SceneWithTaskListFragment();
        return frg;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(TAG,"onatach");
        mActivity= (TasksSelectionActivity) activity;
        mToolbar=(ExtToolbarEngine)mActivity.getToolbar();
        mProgressDialog=DialogFactory.newProgressDialog(mActivity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_scenes_with_tasks, container, false);
        Log.e(TAG,"onCreateView");
        findViews();
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
    }

    private void tryGetTasks() {
        mProgressDialog.show();
        EduMaterialRepository.newInstance(mActivity).getTasks(mScene.sceneID, new RepositoryCallback<List<TaskModel>>() {
            @Override
            public void onSuccess(List<TaskModel> objects) {
                mProgressDialog.dismiss();
                displayTasks(objects);
            }

            @Override
            public void onError(String error) {
                mProgressDialog.dismiss();
                DialogFactory.showSimpleOneButtonDialog(getActivity(), "ERROR", error); //TODO UPDATE MESSAGE GETTER
            }
        });
    }

    private void displayTasks(List<TaskModel> objects) {
        if(ListsUtils.isEmpty(objects)){
            DialogFactory.showSimpleOneButtonDialog(mActivity, "Empty", "There are not tasks in this scene", new DialogFactory.OnOkClickListener() {
                @Override
                public void wasOkClicked(DialogInterface dialog, boolean isOk) {
                    mActivity.displayEpisodesScenes();
                }
            });
            mActivity.displayRefreshFragment(SceneWithTaskListFragment.this);
            return;
        }
        for(TaskModel m:objects){
            Log.e(TAG, "CHAT  = " + m.getChat());
        }
        mTasksAdapter=new TaskListPagerAdapter(mActivity, new ArrayList<TaskModel>(objects), getTaskPath());
        mViewPager.setAdapter(mTasksAdapter);
    }

    public void setPositionBy(String taskId){
        Log.e(TAG,"position = " + taskId + ", " + mTasksAdapter + ", " + ListsUtils.isEmpty(mTasksAdapter.getAllItems()));
        if(mTasksAdapter==null||ListsUtils.isEmpty(mTasksAdapter.getAllItems()))
            return;
        int size=mTasksAdapter.getAllItems().size();
        for(int i=0; i<size; i++){
            if(mTasksAdapter.getAllItems().get(i).id.equals(taskId)) {
                setViewPagerPosition(i);
                return;
            }
        }
        setViewPagerPosition(0);
    }

    private void setViewPagerPosition(final int pos){
        mViewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                mViewPager.setCurrentItem(pos);
            }
        }, 150);
    }

    @Deprecated
    public List<TaskModel> getTasks(){
        if(mTasksAdapter!=null)
        return mTasksAdapter.getAllItems();
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
        updateContent();
//        initViews();
//        tryGetTasks();
    }

    @Override
    public Type getFragmentType() {
        return Type.TASKS;
    }


    public void updateContent(){
        try {
            String newEpisodeId = StorageFactory.getUserStorage(mActivity).getCurrentEpisode().episodeID;
            String newSceneId = StorageFactory.getUserStorage(mActivity).getCurrentScene().sceneID;
//        if(mEpisode==null||mScene==null){
//            updateFragmentContent(newEpisodeId, newSceneId);
//            return;
//        }
//        if(newEpisodeId.equals(mEpisode.episodeID)&&newSceneId.equals(mScene.sceneID))
//            return;
        if(TextUtils.isEmpty(newEpisodeId)||TextUtils.isEmpty(newSceneId))
            mActivity.displayEpisodesScenes();
        else
          updateFragmentContent(newEpisodeId, newSceneId);
            //IF FOR EXAMPLE AFTER DELETE CURRENT SCENE ID OR EPISODE ID ARE NULL
        }catch (RuntimeException e){
            Log.e(TAG, "updateContent_ " +  e.toString());
            mActivity.displayEpisodesScenes();
        }
    }

    private void updateFragmentContent(String episodeId, String sceneId){
        mEpisode=StorageFactory.getEduStorage(mActivity).getEpisode(episodeId);
        for(int i=0; i<mEpisode.scenes.length; i++){
            if(mEpisode.scenes[i].sceneID.equals(sceneId)){
                mScene=mEpisode.scenes[i];
                break;
            }
        }
        initViews();
        tryGetTasks();
    }

    @Override
    public Fragment getInstance() {
        return SceneWithTaskListFragment.this;
    }

    public String getTaskPath() {
        return mtvEpisodeName.getText()+"/"+mtvSceneName.getText();
    }
}
