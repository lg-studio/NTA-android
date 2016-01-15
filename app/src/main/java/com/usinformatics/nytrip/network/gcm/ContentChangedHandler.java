package com.usinformatics.nytrip.network.gcm;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.usinformatics.nytrip.IntentConsts;
import com.usinformatics.nytrip.databases.EduMaterialDbManager;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.network.gcm.models.ContentChangedModel;
import com.usinformatics.nytrip.storages.StorageFactory;

import java.util.List;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 31.07.2015.
 */
public class ContentChangedHandler {

    private static final String TAG = ContentChangedHandler.class.getSimpleName();

    private final ContentChangedModel mContentChangedModel;
    private final EduMaterialDbManager mDbManager;
    private Context context;

    public ContentChangedHandler(Context context,ContentChangedModel instance){
       this.mContentChangedModel=instance;
        mDbManager= EduMaterialDbManager.getInstance(context);
        this.context=context;
    }

    public Intent startChange(){
        if(mContentChangedModel.getType()==null){
            Log.e(TAG, "type is null");
            return null;
        }
        try {

            String extraUpdate=null;
            switch (mContentChangedModel.getType()) {
                case REMOVED:
                    extraUpdate=removeContent();
                    break;
                case UPDATED:
                    extraUpdate=updateContent();
                    break;
                case CREATED:
                    extraUpdate=createNewContent();
                    break;
            }
            if(extraUpdate!=null){
                Intent intent= new Intent();
                intent.setAction(IntentConsts.UPDATE_CONTENT);
                intent.putExtra(IntentConsts.Extra.CONTENT_TYPE,extraUpdate);
            }
            return null;
        }catch (RuntimeException e){
            Log.e(TAG,"E= " + e.toString());
            return null;
        }
    }

    private String removeContent() {
        Log.e(TAG, "remove = " + mContentChangedModel.toString());
        if(!TextUtils.isEmpty(mContentChangedModel.episodeId)){
            return removeEpisode(mContentChangedModel.episodeId);
        }
        if(!TextUtils.isEmpty(mContentChangedModel.sceneId)){
            return removeScene(mContentChangedModel.sceneId);
        }
        if(!TextUtils.isEmpty(mContentChangedModel.taskId)){
            return removeTask(mContentChangedModel.taskId);
        }
        return null;
    }

    private String updateContent() {
        Log.e(TAG, "update = " + mContentChangedModel.toString());
        if(!TextUtils.isEmpty(mContentChangedModel.episodeId)){
            return updateEpisode(mContentChangedModel.episodeId);
        }
        if(!TextUtils.isEmpty(mContentChangedModel.sceneId)){
            return updateScene(mContentChangedModel.sceneId);
        }
        if(!TextUtils.isEmpty(mContentChangedModel.taskId)){
            return updateTask(mContentChangedModel.taskId);
        }
        return null;
    }

    private String updateScene(String sceneId) {
        EpisodeModel db=mDbManager.getEpisodeOf(sceneId);
        if(db==null)
            return null;
       return removeEpisode(db.episodeID);
    }

    private String updateTask(String taskId) {
        TaskModel task=mDbManager.getTask(taskId);
        if(task==null)
            return null;
        if(mDbManager.removeTask(taskId))
            return IntentConsts.Extra.SCENES;
        else return null;
    }


    private String removeTask(String taskId) {
        TaskModel task=mDbManager.getTask(taskId);
        if(task==null)
            return null;
        mDbManager.removeTask(taskId);
        mDbManager.removeTaskFromScene(task.sceneId, taskId);
        task=null;
        return IntentConsts.Extra.SCENES;
    }

    private String removeScene(String sceneId) {
        mDbManager.removeScene(sceneId);
        mDbManager.removeTaskBy(sceneId);
        if(StorageFactory.getUserStorage(context).getCurrentScene().sceneID.equals(sceneId)){
            StorageFactory.getUserStorage(context).setCurrentScene(null);
        }
        return IntentConsts.Extra.EPISODES;
    }

    private String removeEpisode(String episodeId) {
        List<String> scenes=mDbManager.removeEpisode(episodeId);
        if(StorageFactory.getUserStorage(context).getCurrentEpisode().episodeID.equals(episodeId)){
            StorageFactory.getUserStorage(context).setCurrentEpisode(null);
        }
        if(ListsUtils.isEmpty(scenes))
            return null;
        mDbManager.removeTasksBy(scenes);
        mDbManager.removeEpisodeIdFromCourse(episodeId);
        return IntentConsts.Extra.EPISODES;
        //ADD MANAGER
    }

    private String updateEpisode(String episodeId) {
        removeEpisode(episodeId);
        return IntentConsts.Extra.EPISODES;
    }

    private String createNewContent() {
        Log.e(TAG, "create new = " + mContentChangedModel.toString());
        if(!TextUtils.isEmpty(mContentChangedModel.taskId)){
           return createNewTask(mContentChangedModel.taskId, mContentChangedModel.sceneId);
        }
        if(!TextUtils.isEmpty(mContentChangedModel.sceneId)){
            return createNewScene(mContentChangedModel.sceneId, mContentChangedModel.episodeId);
        }
        if(!TextUtils.isEmpty(mContentChangedModel.episodeId)){
            return createNewEpisode(mContentChangedModel.episodeId,mContentChangedModel.courseId);
        }
        return null;
    }

    private String createNewEpisode(String episodeId, String courseId) {
        if(TextUtils.isEmpty(episodeId)||TextUtils.isEmpty(courseId))
            return null;
        if(mDbManager.removeEpisodesOf(courseId)){
            return IntentConsts.Extra.EPISODES;
        }else
            return null;
    }

    private String createNewScene(String sceneId, String episodeId) {
        if(TextUtils.isEmpty(sceneId)||TextUtils.isEmpty(episodeId))
            return null;
        return removeEpisode(episodeId);
    }


    private String createNewTask(String taskId, String sceneId) {
        if(TextUtils.isEmpty(sceneId)||TextUtils.isEmpty(sceneId))
            return null;
        mDbManager.addTaskIdToScene(taskId,sceneId);
        mDbManager.removeTaskBy(sceneId);
        return IntentConsts.Extra.SCENES;
    }

}
