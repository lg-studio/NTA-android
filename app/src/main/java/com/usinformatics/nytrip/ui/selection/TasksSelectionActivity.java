package com.usinformatics.nytrip.ui.selection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.usinformatics.nytrip.IntentConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.helpers.UserActionsHelper;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.storages.UserDataStorage;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ExtToolbarEngine;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarMode;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;
import com.usinformatics.nytrip.ui.selection.fragments.EpisodesWithScenesFragment;
import com.usinformatics.nytrip.ui.selection.fragments.IFragment;
import com.usinformatics.nytrip.ui.selection.fragments.RefreshFragment;
import com.usinformatics.nytrip.ui.selection.fragments.SceneWithTaskListFragment;
import com.usinformatics.nytrip.ui.selection.fragments.TaskMapFragment;

/**
 * Created by D1m11n on 17.06.2015.
 */
public class TasksSelectionActivity extends BaseActivity {


    private static final String TAG = "TASK_SELECTION_ACT";

    @Override
    public String getTag() {
        return "TASK_SELECTION_ACTIVITY";
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return ItemRawPopup.MAIN;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return new ExtToolbarEngine(TasksSelectionActivity.this, R.id.tt);
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return 0;
    }


    private static final int ID_CONTENT = R.id.content;

    private SceneWithTaskListFragment mScenesWithTaskListFragment;

    private EpisodesWithScenesFragment mEpisodesFragment;

    private TaskMapFragment mMapFragment;

    private UserDataStorage mUserData;

    private IFragment mCurrentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scenes_tasks);
        initToolbarEngine(true);
        mUserData = StorageFactory.getUserStorage(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ((ExtToolbarEngine) mToolbarEngine).onToolbarBackClick(item) ? true : super.onOptionsItemSelected(item);
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {
        switch (currentItem) {
            case BACK:
                onBackPressed();
                return;
            case MAP:
                displayMap();
                break;
            case MENU:
                break;
            case SCENES_WTH_TASKS:
                displayScenesTasks();
                break;
            case INFO:
                displayInfoDialog();
                break;
            default:
                break;
        }
    }

    private void displayInfoDialog() {
        DialogFactory.showSimpleOneButtonDialog(TasksSelectionActivity.this, "Empty", "No actions implemented");
    }

    private void displayMap() {
        ((ExtToolbarEngine) mToolbarEngine).setToolbarTitle("Map", ToolbarMode.MAP);
        //if (mMapFragment == null)
            mMapFragment = TaskMapFragment.newInstance(ModelBridge.getMarkersFrom(mScenesWithTaskListFragment.getTasks()));
//        else
//            mMapFragment.
        replaceFragment(mMapFragment);
    }


    public void displayEpisodesScenes() {
        if (mEpisodesFragment == null)
            mEpisodesFragment = EpisodesWithScenesFragment.newInstance();
        ((ExtToolbarEngine) mToolbarEngine).setToolbarTitle("Scene selection", ToolbarMode.SIMPLE);
        replaceFragment(mEpisodesFragment);
    }

    public void displayScenesTasks() {
        if (mScenesWithTaskListFragment == null)
            mScenesWithTaskListFragment = SceneWithTaskListFragment.newInstance(/*mUserData.getCurrentEpisode(), mUserData.getCurrentScene()*/);
       // mScenesWithTaskListFragment.updateContent();
        ((ExtToolbarEngine) mToolbarEngine).setToolbarTitle("Task selection", ToolbarMode.TASKS);
        replaceFragment(mScenesWithTaskListFragment);
    }

    public void displayScenesTasks(String taskId) {
        Log.e(TAG, "TASK ID = " +taskId + ", " + mScenesWithTaskListFragment);
        if(mScenesWithTaskListFragment==null|| TextUtils.isEmpty(taskId))
            return;
        displayScenesTasks();
        mScenesWithTaskListFragment.setPositionBy(taskId);
    }

    private void replaceFragment(IFragment fragment) {
        if (fragment == null) return;
        mCurrentFragment = fragment;
        getFragmentManager().beginTransaction().replace(ID_CONTENT, fragment.getInstance()).commit(); //TODO CHECK ON NPE
    }


    @Override
    public void onBackPressed() {
        if (dismissPopupIsNeeded())
            return;
        Log.e(TAG, "FRAGMENT NOW IS = " + mCurrentFragment.getFragmentType());
        if (mCurrentFragment == null || mCurrentFragment.getFragmentType() == IFragment.Type.EPISODES) {
            UserActionsHelper.logout(TasksSelectionActivity.this);
            return;
        }
        if(mCurrentFragment==null || mCurrentFragment.getFragmentType()== IFragment.Type.REFRESH){
            UserActionsHelper.logout(TasksSelectionActivity.this);
            return;
        }
        displayEpisodesScenes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayMainWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void displayMainWindow() {
        if(getIntent()!=null&&getIntent().hasExtra(IntentConsts.Extra.SCENES)){
            displayScenesTasks();
        }else
            displayEpisodesScenes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ExtToolbarEngine) mToolbarEngine).release();
    }

    public void openTaskWindow(TaskModel item) {
        Intent intent= new Intent(TasksSelectionActivity.this, ExcerciseActivity.class);
        intent.putExtra(IntentConsts.Extra.TASK, item.id);
        TasksSelectionActivity.this.startActivity(intent);
    }

    public void displayRefreshFragment(IFragment invokedFragment){
        replaceFragment(RefreshFragment.newInstance(invokedFragment, new RefreshFragment.OnClickListener() {
            @Override
            public void onCLick(IFragment.Type type) {
                if(type== IFragment.Type.EPISODES)
                  displayEpisodesScenes();
                if(type== IFragment.Type.TASKS)
                    displayScenesTasks();
            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mUpdateReceiver, new IntentFilter(IntentConsts.UPDATE_CONTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mUpdateReceiver);
    }

    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type=intent.getStringExtra(IntentConsts.Extra.CONTENT_TYPE);
            updateContentOnIntentReceivedBy(type);
        }
    };

    private void updateContentOnIntentReceivedBy(String type) {
      if(type==null) return;
      if(mCurrentFragment==null)
          return;
      if(mCurrentFragment.getFragmentType()== IFragment.Type.REFRESH||mCurrentFragment.getFragmentType()== IFragment.Type.MAP)
          return;
        mCurrentFragment.updateContent();
    }
}
