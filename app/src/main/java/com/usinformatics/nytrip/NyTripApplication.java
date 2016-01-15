package com.usinformatics.nytrip;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.usinformatics.nytrip.helpers.FileHelper;
import com.usinformatics.nytrip.storages.StorageFactory;

import java.util.Calendar;
import java.util.TimeZone;

import io.fabric.sdk.android.Fabric;

/**
 * Created by D1m11n on 17.07.2015.
 */
public class NyTripApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("APP", "=====MULTIDEX====");
        Fabric.with(this, new Crashlytics());
        if(StorageFactory.getAppStorage(this).isFirstRun())
            prepareOnFirstStart();
 //       HashUtils.generateHash(this);
    }

    private void prepareOnFirstStart(){
        FileHelper.createDir(AppConsts.APP_DIRECTORY_PATH);
    }

    public String getAppVersion(){
        return String.valueOf(BuildConfig.VERSION_NAME);
    }

    public long getUtcTimeNowSec(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return cal.getTimeInMillis()/1000L;

    }



}
