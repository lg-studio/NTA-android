package com.usinformatics.nytrip;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.zl.android.ui.scopes.AppScope;
import com.zl.android.ui.scopes.AppScopeModule;
import com.zl.android.ui.scopes.DaggerAppScope;
import com.usinformatics.nytrip.helpers.FileHelper;
import com.usinformatics.nytrip.storages.StorageFactory;

import io.fabric.sdk.android.Fabric;

/**
 * Created by D1m11n on 17.07.2015.
 */
public class NyTripApplication extends Application {

    private AppScope mAppScope;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        if (StorageFactory.getAppStorage(this).isFirstRun())
            prepareOnFirstStart();
        //       HashUtils.generateHash(this);

        mAppScope = DaggerAppScope.builder()
                .appScopeModule(new AppScopeModule(this))
                .build();
    }

    public static AppScope getScope(Application app) {
        return ((NyTripApplication) app).mAppScope;
    }

    private void prepareOnFirstStart() {
        FileHelper.createDir(AppConsts.APP_DIRECTORY_PATH);
    }

}