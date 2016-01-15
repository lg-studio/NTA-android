package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.databases.model.CourseDBModel;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.storages.EduDataStorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 22.06.2015.
 */
public class EduMaterialDbManager implements EduDataStorage {


    private static final String TAG = "EDU_MATERIAL_DB_MANAGER";
    private Context mContext;

    private static EduMaterialDbManager mInstance;
    private static EduMaterialDbHelper mDatabaseHelper;
    private TaskDbManager mTaskManager;
    private EpisodeScenesDbManager mEpScDbManager;

    private EduMaterialDbManager(Context context){
        mContext=context.getApplicationContext();
        if(mDatabaseHelper ==null)
            mDatabaseHelper = OpenHelperManager.getHelper(mContext,EduMaterialDbHelper.class);
        if(mTaskManager==null){
            mTaskManager= new TaskDbManager(mContext,mDatabaseHelper);
        }
        if(mEpScDbManager==null)
            mEpScDbManager= new EpisodeScenesDbManager(mContext,mDatabaseHelper);
    }

    public static synchronized EduMaterialDbManager
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
            mDatabaseHelper.getCourseDao().createOrUpdate(ModelBridge.getCoursesDbModelFrom(model));
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "saveCourse_; sql error e = " + e.toString());
            return false;
        }
    }

    @Override
    public boolean saveCourses(CourseModel[] models) {
        if(ListsUtils.isEmpty(models)) return false;
        try {
            mDatabaseHelper.getWritableDatabase().beginTransaction();
            for (int i=0; i<models.length; i++){
                saveCourse(models[i]);
            }
            mDatabaseHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "saveCourses_; runtime error e = " + e.toString());
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
            return ModelBridge.getSemestersFrom (mDatabaseHelper.getCourseDao().queryForAll());
        } catch (SQLException e) {
            Log.e(TAG,"saveEpisode_ sql exception" + e.toString());
            return null;
        }
    }

    public boolean removeEpisodeIdFromCourse(String episodeID){
        if(TextUtils.isEmpty(episodeID))
            return false;
        try {
            List<CourseDBModel> courses=mDatabaseHelper.getCourseDao().queryForAll();
            CourseDBModel target=null;
            for(int i=0;i<courses.size(); i++){
                if(courses.get(i).getEpisodesArrayAsString().contains(episodeID)) {
                    target=courses.get(i);
                    break;
                }
            }
            if(target==null)
                return false;
            String [] ids = target.getEpisodesArray();
            ArrayList<String> list=new ArrayList<>(Arrays.asList(ids));
            list.remove(episodeID);
            target.setEpisodesArray(list.toArray(new String[list.size()]));
            mDatabaseHelper.getCourseDao().update(target);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void clearAllCourses() {
       if(!mDatabaseHelper.clearCourseTable()){
           try {
               mDatabaseHelper.getCourseDao().delete(mDatabaseHelper.getCourseDao().queryForAll());
           } catch (SQLException e) {
               Log.e(TAG, "clearSemesters_ sql exception" + e.toString());
           }
       }
    }

    @Override
    public void saveTasks(List<TaskModel> tasks) {
        mTaskManager.saveTasks(ModelBridge.getTasksDbFrom(tasks));
    }

    @Override
    public List<TaskModel> getTasksBy(String sceneId) {
        return ModelBridge.getTasksFrom(mTaskManager.getTasksBy(sceneId));
    }

    @Override
    public TaskModel getTask(String taskId) {
        return ModelBridge.getTaskFrom(mTaskManager.getTask(taskId));
    }

    @Override
    public void saveEpisodes(List<EpisodeModel> episodes) {
        mEpScDbManager.saveEpisodes(ModelBridge.getEpisodesDBFrom(episodes));
    }

    @Override
    public List<EpisodeModel> getEpisodesBy(String courseId) {
        return ModelBridge.getEpisodesFrom(mEpScDbManager.getEpisodesBy(courseId));
    }

    @Override
    public EpisodeModel getEpisode(String episodeId) {
        return ModelBridge.getEpisodeFrom(mEpScDbManager.getEpisode(episodeId));
    }

    @Override
    public void saveTask(TaskModel task) {
        mTaskManager.saveTask(ModelBridge.getTaskDbFrom(task));
    }

    @Override
    public boolean removeTasks(String[] taskIDs) {
        try {
            mDatabaseHelper.getWritableDatabase().beginTransaction();
            for (int i=0; i<taskIDs.length; i++){
                removeTask(taskIDs[i]);
            }
            mDatabaseHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "removeTasks_; runtime error e = " + e.toString());
            return false;
        }finally {
            mDatabaseHelper.getWritableDatabase().endTransaction();
        }
    }

    @Override
    public boolean removeTask(String taskId) {
        return mTaskManager.removeTask(taskId);
    }

    /**
     *
     * @param episodeId
     * @return arrayf of deleted scenes
     */
    @Override
    public List<String> removeEpisode(String episodeId) {
         return  mEpScDbManager.removeEpisode(episodeId);
    }

    public EpisodeModel getEpisodeOf(String sceneId){
        return ModelBridge.getEpisodeFrom(mEpScDbManager.getEpisodeOfSceneId(sceneId));
    }

    @Override
    public boolean removeScene(String sceneID) {
        return mEpScDbManager.removeScene(sceneID);
    }

    @Override
    public boolean removeTaskBy(String sceneId) {
        return mTaskManager.removeTaskByScene(sceneId);
    }

    @Override
    public boolean removeTasksBy(String[] scenesId) {
        if(ListsUtils.isEmpty(scenesId)) return false;
        try {
            mDatabaseHelper.getWritableDatabase().beginTransaction();
            for (int i=0; i<scenesId.length; i++){
                removeTask(scenesId[i]);
            }
            mDatabaseHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "removeTasks_; runtime error e = " + e.toString());
            return false;
        }finally {
            mDatabaseHelper.getWritableDatabase().endTransaction();
        }
    }

    @Override
    public boolean removeTasksBy(List<String> scenesId) {
        if(ListsUtils.isEmpty(scenesId)) return false;
        try {
            mDatabaseHelper.getWritableDatabase().beginTransaction();
            for (int i=0; i<scenesId.size(); i++){
                removeTask(scenesId.get(i));
            }
            mDatabaseHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "removeTasks_; runtime error e = " + e.toString());
            return false;
        }finally {
            mDatabaseHelper.getWritableDatabase().endTransaction();
        }
    }

    @Override
    public boolean removeTaskFromScene(String sceneId, String taskId){
        return mEpScDbManager.removeTaskIdfromScene(sceneId, taskId);
    }

    @Override
    public boolean addTaskIdToScene(String taskId, String sceneId){
        return mEpScDbManager.addTaskIdToScene(sceneId,taskId);
    }

    @Override
    public boolean removeEpisodesOf(String courseId){
        return mEpScDbManager.removeEpisodesByCourse(courseId);
    }

}
