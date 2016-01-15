package com.usinformatics.nytrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.NyTripApplication;
import com.usinformatics.nytrip.databases.EduMaterialDbManager;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.account.activities.AccountActivity;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;


/**
 * Created by D1m11n on 07.05.2015.
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EduMaterialDbManager.getInstance(this);
        String token = StorageFactory.getUserStorage(this).getUser().token;
        if (!TextUtils.isEmpty(token)) {

            // set token to the singleton web service
            NyTripApplication.getScope(getApplication())
                    .webServiceLocator()
                    .setAccessToken(token);

            moveToOtherActivity(TasksSelectionActivity.class, AppConsts.SPLASH_DELAY);
        } else {
            moveToOtherActivity(AccountActivity.class, AppConsts.SPLASH_DELAY);
        }
    }

    private void moveToOtherActivity(final Class activity, long delay){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashActivity.this, activity);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, delay);
    }

}
