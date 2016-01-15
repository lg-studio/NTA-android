package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.usinformatics.nytrip.databases.dao.TaskDao;
import com.usinformatics.nytrip.databases.model.TaskDbModel;

import java.sql.SQLException;
import java.util.List;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 23.07.2015.
 */
class TaskDbManager {

    private static final String TAG = TaskDbManager.class.getSimpleName();
    private TaskDao mTaskDao;

    private Context mContext;
    private EduMaterialDbHelper mHelper;

    TaskDbManager(Context context, EduMaterialDbHelper helper){
        mContext=context;
        mHelper=helper;
    }

    public boolean saveTasks(List<TaskDbModel> tasks){
        if(ListsUtils.isEmpty(tasks))
            return false;
        int size=tasks.size();
        try {
            mHelper.getWritableDatabase().beginTransaction();
            for (int i=0; i<size; i++){
                saveTask(tasks.get(i));
            }
            mHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "saveTasks_; runtime error e = " + e.toString());
            return false;
        }finally {
            mHelper.getWritableDatabase().endTransaction();
        }
    }

    public boolean saveTask(TaskDbModel taskDbModel) {
        if(taskDbModel==null) return false;
           initTaskDaoIfNeeded();
        try {
            mTaskDao.createOrUpdate(taskDbModel);
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "saveTask_; sql error e = " + e.toString());
            return false;
        }
    }

    private void initTaskDaoIfNeeded(){
        if(mTaskDao==null)
            mTaskDao=mHelper.getTaskDao();
    }

    public List<TaskDbModel> getTasksBy(String sceneId) {
        initTaskDaoIfNeeded();
        try {
            return mTaskDao.queryForEq(TaskDbModel.Columns.SCENE_ID, sceneId);
        } catch (SQLException e) {
            Log.e(TAG, "getTasks_; sql error e = " + e.toString());
            return null;
        }
    }

    public TaskDbModel getTask(String taskId) {
        initTaskDaoIfNeeded();
        try {
            List<TaskDbModel> list= mTaskDao.queryForEq(TaskDbModel.Columns.TASK_ID, taskId);
            Log.e(TAG, "getTask_; list " + list);
            return ListsUtils.isEmpty(list)?null:list.get(0);
        } catch (SQLException e) {
            Log.e(TAG, "getTask_; sql error e = " + e.toString());
            return null;
        }
    }

    public boolean removeTask(String taskId) {
        try {
            mTaskDao.deleteById(taskId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeTaskByScene(String sceneId) {
        try {
            DeleteBuilder deleteDb=mTaskDao.deleteBuilder();
            deleteDb.where().eq(TaskDbModel.Columns.SCENE_ID, sceneId);
            deleteDb.delete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
