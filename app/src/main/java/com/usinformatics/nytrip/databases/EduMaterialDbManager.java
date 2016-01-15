package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.storages.EduDataStorage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by D1m11n on 22.06.2015.
 */
public class EduMaterialDbManager implements EduDataStorage {


    private static final String TAG = "EDU_MATERIAL_DB_MANAGER";
    private Context mContext;

    private static EduMaterialDbManager mInstance;
    private static EduMaterialDbHelper mDatabaseHelper;
//    EpisodeDao mEpisodeDao;
//    SceneDao mSceneDao;
//    SemesterDao mSemesterDao;
//    TaskDao mTaskDao;

    private EduMaterialDbManager(Context context){
        mContext=context.getApplicationContext();
        if(mDatabaseHelper ==null)
            mDatabaseHelper = OpenHelperManager.getHelper(mContext,EduMaterialDbHelper.class);
//        try {
//            mEpisodeDao = new EpisodeDao(mDatabaseHelper.getConnectionSource());
//            mTaskDao = new TaskDao(mDatabaseHelper.getConnectionSource());
//            mSceneDao = new SceneDao(mDatabaseHelper.getConnectionSource());
//            mSemesterDao = new SemesterDao(mDatabaseHelper.getConnectionSource());
//        }catch (SQLException e){
//            Log.e(TAG, "init error e = " + e.toString());
//        }
    }



    public static EduMaterialDbManager
    getInstance(Context context){
        if (mInstance==null){
            synchronized (EduMaterialDbManager.class){
                if (mInstance==null)
                    mInstance= new EduMaterialDbManager(context);
            }
        }
        return mInstance;
    }

    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        mDatabaseHelper = null;
    }

    @Override
    public boolean saveCourse(CourseModel model) {
        try {
            mDatabaseHelper.getSemesterDao().createOrUpdate(ModelBridge.getCoursesDbModelFrom(model));
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "saveSemester_; sql error e = " + e.toString());
            return false;
        }
    }

    @Override
    public boolean saveCourses(CourseModel[] models) {
        try {
            mDatabaseHelper.getWritableDatabase().beginTransaction();
            for (int i=0; i<models.length; i++){
                saveCourse(models[i]);
            }
            mDatabaseHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "saveSemesters_; runtime error e = " + e.toString());
            return false;
        }finally {
            mDatabaseHelper.getWritableDatabase().endTransaction();
        }
    }

    //TODO ADD BODY
    @Override
    public boolean saveEpisodes(EpisodeModel[] models) {
        return false;
    }

    //TODO ADD BODY
    @Override
    public boolean saveEpisode(EpisodeModel model) {
        return false;
    }

    @Override
    public ArrayList<CourseModel> getCourses() {
        try {
            return ModelBridge.getSemestersFrom (mDatabaseHelper.getSemesterDao().queryForAll());
        } catch (SQLException e) {
            Log.e(TAG,"getSemesters_ sql exception" + e.toString());
            return null;
        }
    }

    @Override
    public void clearAllCourses() {
       if(!mDatabaseHelper.clearSemesterTable()){
           try {
               mDatabaseHelper.getSemesterDao().delete(mDatabaseHelper.getSemesterDao().queryForAll());
           } catch (SQLException e) {
               Log.e(TAG, "clearSemesters_ sql exception" + e.toString());
           }
       }
    }
}
