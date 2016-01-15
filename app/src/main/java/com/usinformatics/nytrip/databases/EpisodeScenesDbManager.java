package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.usinformatics.nytrip.databases.dao.EpisodeDao;
import com.usinformatics.nytrip.databases.model.EpisodeDBModel;
import com.usinformatics.nytrip.models.SceneModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 23.07.2015.
 */
class EpisodeScenesDbManager {

    private static final String TAG = EpisodeScenesDbManager.class.getSimpleName();
    private EpisodeDao mEpisodeDao;

    private Context mContext;
    private EduMaterialDbHelper mHelper;

    EpisodeScenesDbManager(Context context, EduMaterialDbHelper helper) {
        mContext = context;
        mHelper = helper;
    }

    public boolean saveEpisodes(List<EpisodeDBModel> episodes) {
        if (ListsUtils.isEmpty(episodes))
            return false;
        int size = episodes.size();
        try {
            mHelper.getWritableDatabase().beginTransaction();
            for (int i = 0; i < size; i++) {
                saveEpisode(episodes.get(i));
            }
            mHelper.getWritableDatabase().setTransactionSuccessful();
            return true;
        } catch (RuntimeException e) {
            Log.e(TAG, "saveTasks_; runtime error e = " + e.toString());
            return false;
        } finally {
            mHelper.getWritableDatabase().endTransaction();
        }
    }

    public boolean saveEpisode(EpisodeDBModel episode) {
        if (episode == null) return false;
        initEpisodeDaoIfNeeded();
        try {
            mEpisodeDao.createOrUpdate(episode);
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "saveTask_; sql error e = " + e.toString());
            return false;
        }
    }

    private void initEpisodeDaoIfNeeded() {
        if (mEpisodeDao == null)
            mEpisodeDao = mHelper.getEpisodeDao();
    }

    public List<EpisodeDBModel> getEpisodesBy(String courseId) {
        initEpisodeDaoIfNeeded();
        try {
            return mEpisodeDao.queryForEq(EpisodeDBModel.Columns.COURSE_ID, courseId);
        } catch (SQLException e) {
            Log.e(TAG, "getEpisodes_; sql error e = " + e.toString());
            return null;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "getEpisodes_; illegal  error e = " + e.toString());
            return null;
        }
    }

    public EpisodeDBModel getEpisode(String episodeId) {
        initEpisodeDaoIfNeeded();
        try {
            return mEpisodeDao.queryForId(episodeId);
        } catch (SQLException e) {
            Log.e(TAG, "getTask_; sql error e = " + e.toString());
            return null;
        }
    }

    /**
     * @param episodeId
     * @return arrayf of deleted scenes
     */

    //TODO REFACTOR
    public List<String> removeEpisode(String episodeId) {
        initEpisodeDaoIfNeeded();
        EpisodeDBModel ep = getEpisode(episodeId);
        if (ep == null)
            return null;
        List<String> list = getScenesIdsOf(ep);
        try {
            mEpisodeDao.delete(ep);
        } catch (SQLException e) {
            Log.e(TAG, "removeEpisode_; sql error e = " + e.toString());
        }
        return list;
    }

    public EpisodeDBModel getEpisodeOfSceneId(String sceneId) {
        if (sceneId == null) return null;
        try {
            List<EpisodeDBModel> eps = mEpisodeDao.queryForAll();
            for (EpisodeDBModel ep : eps) {
                List<String> scenes = getScenesIdsOf(ep);
                if (scenes.contains(sceneId))
                    return ep;
            }
        } catch (SQLException e) {
            Log.e(TAG, "getEpisodeOfSceneId_; sql error e = " + e.toString());
        }
        return null;
    }

    public boolean addSceneTo(EpisodeDBModel m, SceneModel scene) {
        if (m == null || scene == null)
            return false;
        List<SceneModel> list = getScenesOf(m);
        list.add(scene);
        SceneModel[] scenes = list.toArray(new SceneModel[list.size()]);
        m.scenesJsonArr = new Gson().toJson(scenes, SceneModel[].class);
        try {
            mEpisodeDao.update(m);
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "removeScene_; sql error e = " + e.toString());
            return false;
        }
    }

    public boolean addSceneTo(String episodeId, SceneModel scene) {
        if (TextUtils.isEmpty(episodeId) || scene == null)
            return false;
        EpisodeDBModel m = getEpisode(episodeId);
        if (getScenesIdsOf(m).contains(scene.sceneID))
            return false;
        List<SceneModel> list = getScenesOf(m);
        list.add(scene);
        return updateScenesInEpisode(m, list);
    }

    public boolean updateScenesInEpisode(EpisodeDBModel m, List<SceneModel> list) {
        SceneModel[] scenes = list.toArray(new SceneModel[list.size()]);
        m.scenesJsonArr = new Gson().toJson(scenes, SceneModel[].class);
        try {
            mEpisodeDao.update(m);
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "removeScene_; sql error e = " + e.toString());
            return false;
        }
    }


    private List<String> getScenesIdsOf(EpisodeDBModel ep) {
        List<String> list = new ArrayList<>();
        SceneModel[] scenes = new Gson().fromJson(ep.scenesJsonArr, SceneModel[].class);
        for (int i = 0; i < scenes.length; i++)
            list.add(scenes[i].sceneID);
        return list;
    }

    private List<SceneModel> getScenesOf(EpisodeDBModel ep) {
        List<SceneModel> list = new ArrayList<>();
        SceneModel[] scenes = new Gson().fromJson(ep.scenesJsonArr, SceneModel[].class);
        for (int i = 0; i < scenes.length; i++)
            list.add(scenes[i]);
        return list;
    }

    private SceneModel getSceneOf(EpisodeDBModel ep, String sceneId) {
        List<SceneModel> list = new ArrayList<>();
        SceneModel[] scenes = new Gson().fromJson(ep.scenesJsonArr, SceneModel[].class);
        for (int i = 0; i < scenes.length; i++)
            if(scenes[i].sceneID.equals(sceneId))
                return scenes[i];
        return null;
    }


    public boolean removeScene(String sceneID) {
        EpisodeDBModel m = getEpisodeOfSceneId(sceneID);
        if (m == null)
            return false;
        List<SceneModel> list = getScenesOf(m);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).sceneID.equals(sceneID)) {
                list.remove(list.get(i));
                break;
            }
        }
       return updateScenesInEpisode(m, list);
    }

    public boolean removeTaskIdfromScene(String sceneId,String taskId){
        if(TextUtils.isEmpty(sceneId)||TextUtils.isEmpty(taskId))
            return false;
        EpisodeDBModel m=getEpisodeOfSceneId(sceneId);
        List<SceneModel> list=getScenesOf(m);
        SceneModel currentSc=null;
        for (int i=0; i<list.size(); i++){
            if(list.get(i).sceneID.equals(sceneId)){
                currentSc=list.get(i);
                break;
            }
        }
        if(currentSc==null) return false;
        ArrayList<String> tasks=new ArrayList(Arrays.asList(currentSc.taskIDs));
        if(!tasks.contains(taskId))
            return false;
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).contains(taskId)){
                tasks.remove(tasks.get(i));
                break;
            }
        }
        currentSc.taskIDs=tasks.toArray(new String[tasks.size()]);
        return updateScenesInEpisode(m,list);
    }

    public boolean addTaskIdToScene(String sceneId,String taskId){
        if(TextUtils.isEmpty(sceneId)||TextUtils.isEmpty(taskId))
            return false;
        EpisodeDBModel m=getEpisodeOfSceneId(sceneId);
        List<SceneModel> list=getScenesOf(m);
        SceneModel currentSc=null;
        for (int i=0; i<list.size(); i++){
            if(list.get(i).sceneID.equals(sceneId)){
                currentSc=list.get(i);
                break;
            }
        }
        if(currentSc==null) return false;
        ArrayList<String> tasks=new ArrayList(Arrays.asList(currentSc.taskIDs));
        if(tasks.contains(taskId))
            return false;
        tasks.add(taskId);
        currentSc.taskIDs=tasks.toArray(new String[tasks.size()]);
        return updateScenesInEpisode(m,list);
    }

    public boolean removeEpisodesByCourse(String courseId) {
        try {
            DeleteBuilder deleteDb=mEpisodeDao.deleteBuilder();
            deleteDb.where().eq(EpisodeDBModel.Columns.COURSE_ID, courseId);
            deleteDb.delete();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
