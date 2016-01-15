package com.usinformatics.nytrip.storages;

import android.content.Context;

import com.usinformatics.nytrip.databases.EduMaterialDbManager;
import com.usinformatics.nytrip.preferences.PrefsApp;
import com.usinformatics.nytrip.preferences.PrefsUser;

/**
 * Created by D1m11n on 11.06.2015.
 */
public class StorageFactory {

    public static UserDataStorage getUserStorage(Context context){
        return PrefsUser.getInstance(context);
    }

    public static AppDataStorage getAppStorage(Context context){
        return PrefsApp.getInstance(context);
    }

    public static EduDataStorage getEduStorage(Context context){
        return EduMaterialDbManager.getInstance(context);
    }

    public static FilesStorage getFileStorage(Context context){
        return null;
    }

}
