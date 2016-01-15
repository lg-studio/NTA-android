package com.usinformatics.nytrip.ui.user_activity;

import android.os.Bundle;
import android.widget.ListView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.BaseActivity;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarActions;
import com.usinformatics.nytrip.ui.additional.toolbar.ToolbarEngine;

import java.util.ArrayList;

/**
 * Created by admin on 7/15/15.
 */
public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);
        initToolbarEngine(true);
        mToolbarEngine.setToolbarTitle("User activity");
        initUserActivityList();

    }

    private void initUserActivityList() {
        ListView userList = (ListView) findViewById(R.id.user_activity_list);
        ArrayList<UserActivityModel> userActivityList = new ArrayList<>();


        UserActivityModel fakeData1 = new UserActivityModel();
        fakeData1.setTaskDate("friday");
        fakeData1.setTaskName("Arrive to NY");
        fakeData1.setTaskPath("semester 1 , scene 1 , task 1");
        fakeData1.setTaskRating("29");
        fakeData1.setTaskTime("33");
        userActivityList.add(fakeData1);
        userActivityList.add(fakeData1);
        userActivityList.add(fakeData1);

        UserActivityAdapter adapter = new UserActivityAdapter(this, userActivityList);
        userList.setAdapter(adapter);
    }

    @Override
    public String getTag() {
        return getString(R.string.user_activity);
    }

    @Override
    public ItemRawPopup getPopupItemOfCurrentActivity() {
        return ItemRawPopup.USER_ACTIVITY;
    }

    @Override
    protected ToolbarEngine getInstanceOfToolbar() {
        return null;
    }

    @Override
    protected int getIdResourcesOfToolbar() {
        return R.id.profile_toolbar;
    }

    @Override
    public void actionToolbarCallback(ToolbarActions currentItem) {

    }

    @Override
    public void onBackPressed() {
        if(dismissPopupIsNeeded())
            return;
        super.onBackPressed();
    }
}
