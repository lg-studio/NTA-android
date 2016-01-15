package com.usinformatics.nytrip.preferences;

import android.content.Context;

import com.securepreferences.SecurePreferences;
import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.storages.AppDataStorage;

/**
 * Created by D1m11n on 11.06.2015.
 */
public class PrefsApp implements AppDataStorage {


    private Context context;

    private static PrefsApp mInstance;


    private SecurePreferences mPrefs;

    private  SecurePreferences.Editor mEditor;

    private void initPrefsIfNeeded(Context context){
        if (mPrefs!=null&&mEditor!=null)
            return;
        mPrefs = new SecurePreferences(context);
        mEditor=mPrefs.edit();
    }

    private PrefsApp(Context context){
        this.context=context.getApplicationContext();
        initPrefsIfNeeded(this.context);

    }

    public static PrefsApp getInstance(Context context){
        if (mInstance==null){
            synchronized (PrefsApp.class){
                if (mInstance==null)
                    mInstance= new PrefsApp(context);
            }
        }
        return mInstance;
    }

    @Override
    public boolean isFirstRun() {
        return mPrefs.getBoolean("first_run", true);
    }

    @Override
    public void setWasFirstRun() {
        mEditor.putBoolean("first_run", true).commit();

    }

    @Override
    public void setDisplayIntro(boolean value) {
        mEditor.putBoolean("display_intro", value).commit();
    }

    @Override
    public boolean canDisplayIntro() {
        return mPrefs.getBoolean("display_intro", true);
    }

    @Override
    public void saveProjectId(String projectId) {
        mEditor.putString(AppConsts.PROJECT_ID, projectId).commit();
    }

    @Override
    public void saveInstanceId(String instanceId) {
        mEditor.putString(AppConsts.GCM_INSTANCE_ID, instanceId);
    }
}
