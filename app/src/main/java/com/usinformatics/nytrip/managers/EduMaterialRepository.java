package com.usinformatics.nytrip.managers;

import android.content.Context;

import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.databases.EduMaterialDbManager;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.network.NetworkErrorHelper;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.network.models.NetCourseModel;
import com.usinformatics.nytrip.network.models.NetEpisodeModel;
import com.usinformatics.nytrip.network.models.NetGroupModel;
import com.usinformatics.nytrip.network.models.NetSceneModel;
import com.usinformatics.nytrip.network.models.NetTaskModel;
import com.usinformatics.nytrip.storages.StorageFactory;

import java.util.Arrays;
import java.util.List;

import common.utis.ListsUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 22.07.2015.
 */
public class EduMaterialRepository extends AbstractRepository {

    private final Context context;

    private EduMaterialRepository(Context context){
        this.context=context;
    }

    public static EduMaterialRepository newInstance(Context context){
        return new EduMaterialRepository(context);
    }

    //TODO CHANGE NETMODEL TO MODEl
    public void getClasses(final RepositoryCallback<NetGroupModel[]> callback){
        RequestExecutor.getInstance(context).coursesList(new OnServerResponseCallback<NetGroupModel[]>() {
            @Override
            public void onResponse(NetGroupModel[] objects, Response responseBody, RetrofitError error) {
                String err=NetworkErrorHelper.getErrorString(context, error);
                if (err!=null) {
                    notifyError(callback, err);
                    return;
                }
                if (ListsUtils.isEmpty(objects)) {
                    //DialogFactory.showSimpleOneButtonDialog(context, "ERROR GROUP", );
                    notifyError(callback,"List of groups is empty");
                    return;
                }

                //TODO UPDATE
                notifySuccess(callback, objects);
                //Log.e(TAG, "group " + NetGroupModel.toString(objects));
                //Log.e(TAG, "current semester " + StorageFactory.getUserStorage(activity).getCurrentCourseId());
                //tryGetCourses(mActivity);
            }
        });
    };



    public void getTasks(final String sceneID, final RepositoryCallback<List<TaskModel>> callback){
        if(callback==null) return;
        List<TaskModel> list= EduMaterialDbManager.getInstance(context).getTasksBy(sceneID);
        if(!ListsUtils.isEmpty(list)) {
            notifySuccess(callback,list);
            return;
        }
        RequestExecutor.getInstance(context).tasks(sceneID, new OnServerResponseCallback<NetTaskModel[]>() {


            @Override
            public void onResponse(NetTaskModel[] objects, Response responseBody, RetrofitError error) {
                String err=NetworkErrorHelper.getErrorString(context,error);
                if(err!=null){
                    callback.onError(err);
                    return;
                }
                List<TaskModel> list= Arrays.asList(ModelBridge.getTasksFrom(objects));
                for(TaskModel t:list){
                    t.sceneId=sceneID;
                }
                notifySuccess(callback,list);
                EduMaterialDbManager.getInstance(context).saveTasks(list);
            }
        });

    }

    public void getTask(final String sceneID, final String taskId, final RepositoryCallback<TaskModel> callback){
        if(callback==null) return;
        TaskModel task= EduMaterialDbManager.getInstance(context).getTask(taskId);
        if(task!=null) {
            notifySuccess(callback,task);
            return;
        }
        RequestExecutor.getInstance(context).task(sceneID,taskId, new OnServerResponseCallback<NetTaskModel>() {


            @Override
            public void onResponse(NetTaskModel objects, Response responseBody, RetrofitError error) {
                String err=NetworkErrorHelper.getErrorString(context,error);
                if(err!=null){
                    notifyError(callback, err);
                    return;
                }
                TaskModel task= ModelBridge.getTaskFrom(objects);
                task.sceneId=sceneID;
                EduMaterialDbManager.getInstance(context).saveTask(task);
                notifySuccess(callback,task);
                //callback.onSuccess(list);
            }
        });

    }

    public void getEpisodes(final String courseID, final RepositoryCallback<List<EpisodeModel>> callback){
        if(callback==null)  return;
        List<EpisodeModel> list=EduMaterialDbManager.getInstance(context).getEpisodesBy(courseID);
        if(!ListsUtils.isEmpty(list)){
            callback.onSuccess(list);
            return;
        }
        RequestExecutor.getInstance(context).episodesWithScenes(StorageFactory.getUserStorage(context).getCurrentCourseId(), new OnServerResponseCallback<EpisodeModel[]>() {
            @Override
            public void onResponse(EpisodeModel[] objects, Response responseBody, RetrofitError error) {
                String err=NetworkErrorHelper.getErrorString(context,error);
                if(err!=null){
                    notifyError(callback,err);
                    return;
                }
                for(EpisodeModel e:objects){
                    e.courseID=courseID;
                }
                List<EpisodeModel> list= Arrays.asList(objects);
                EduMaterialDbManager.getInstance(context).saveEpisodes(list);
                notifySuccess(callback, list);
            }
        });

    }

    public void getCourses(String [] classes,final RepositoryCallback<CourseModel[]> callback){
        RequestExecutor.getInstance(context).courses(classes[0], new OnServerResponseCallback<NetCourseModel[]>() {
            @Override
            public void onResponse(NetCourseModel[] objects, Response responseBody, RetrofitError error) {
                String err=NetworkErrorHelper.getErrorString(context,error);
                if(err!=null){
                    callback.onError(err);
                    return;
                }
              notifySuccess(callback,ModelBridge.getCoursesFrom(objects));
            }
        });
    }

    public void getScene(String sceneId,final RepositoryCallback<SceneModel> callback){
        RequestExecutor.getInstance(context).scene(sceneId, new OnServerResponseCallback<NetSceneModel>() {
            @Override
            public void onResponse(NetSceneModel objects, Response responseBody, RetrofitError error) {
                String err = NetworkErrorHelper.getErrorString(context, error);
                if (err != null) {
                    notifyError(callback,err);
                    return;
                }
                notifySuccess(callback, ModelBridge.getSceneFrom(objects));
            }
        });
    }

    //TODO UPDATE MODEL FOR IDS
    public void getEpisode(String episodeId,final RepositoryCallback<EpisodeModel> callback){
        RequestExecutor.getInstance(context).episode(episodeId, new OnServerResponseCallback<NetEpisodeModel>() {
            @Override
            public void onResponse(NetEpisodeModel objects, Response responseBody, RetrofitError error) {
                String err = NetworkErrorHelper.getErrorString(context, error);
                if (err != null) {
                    notifyError(callback,err);
                    return;
                }
                notifySuccess(callback, ModelBridge.getEpisodeFrom(objects));
            }
        });
    }

//    /**
//     *
//     * @param episodeId
//     * @return true if something was removed false elsewhere
//     * @throws RepositoryContentException when episodeId is null
//     */
//    public boolean removeEpisode(String episodeId) throws RepositoryContentException{
//        if(TextUtils.isEmpty(episodeId))
//            throw new RepositoryContentException("EpisodeId is empty");
//        EpisodeModel m=EduMaterialDbManager.getInstance(context).getEpisode(episodeId);
//        if(m==null) return false;
//        for(int i=0; i<m.scenes.length; i++){
//            EduMaterialDbManager.getInstance(context).removeTaskBy(m.scenes[i].sceneID);
//        }
//        EduMaterialDbManager.getInstance(context).removeEpisode(m.episodeID);
//        return true;
//    }

//    public boolean removeScene(String sceneId) throws RepositoryContentException{
//        if(TextUtils.isEmpty(sceneId))
//            throw new RepositoryContentException("Scene Id is empty");
//        String courseId=StorageFactory.getUserStorage(context).getCurrentCourseId();
//        if(TextUtils.isEmpty(courseId))
//            throw new RepositoryContentException("Course Id is empty");
//        List<EpisodeModel> list=StorageFactory.getEduStorage(context).getEpisodesBy(courseId);
//        if(ListsUtils.isEmpty(list))
//            return false;
//        int size= list.size();
//        for(int i=0; i<size;i++){
//            if(tryRemoveScene(list.get(i), sceneId))
//                return true;
//        }
//        return false;
//    }
//
//    private boolean tryRemoveScene(EpisodeModel episodeModel, String sceneId) {
//        for(int i=0; i<episodeModel.scenes.length; i++){
//            if(episodeModel.scenes[i].sceneID.equals(sceneId)){
//                StorageFactory.getEduStorage(context).removeScene(sceneId);
//                EduMaterialDbManager.getInstance(context).removeTaskBy(sceneId);
//                return true;
//            }
//        }
//        return false;
//    }


}
