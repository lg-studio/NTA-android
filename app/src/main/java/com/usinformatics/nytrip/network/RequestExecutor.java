package com.usinformatics.nytrip.network;

import android.content.Context;
import android.util.Log;

import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.network.models.CourseListModel;
import com.usinformatics.nytrip.network.models.GCMToken;
import com.usinformatics.nytrip.network.requestors.AccountRequests;
import com.usinformatics.nytrip.network.requestors.EduMaterialRequests;
import com.usinformatics.nytrip.network.requestors.GCMRequest;
import com.usinformatics.nytrip.storages.StorageFactory;

import retrofit.mime.TypedFile;

/**
 * Created by D1m11n on 19.06.2015.
 */
public class RequestExecutor {

    private static final String TAG = "REQUEST_EXECUTOR";

    private static Context mContext;

    private static RequestExecutor mInstance = null;
    private static EduMaterialRequests mEduMaterialRequest ;
    private static AccountRequests mAccountRequest ;

    private RequestExecutor(Context context) {
        mContext = context.getApplicationContext();
    }

    public static RequestExecutor getInstance(Context context) {

        if (mInstance == null) {
            synchronized (RequestExecutor.class) {
                if (mInstance == null) {
                    mInstance = new RequestExecutor(context);
                }
                mAccountRequest = ServiceGenerator.createService(AccountRequests.class);
            }
        }
        return mInstance;
    }

    public void account(final UserModel user, OnServerResponseCallback<UserModel> callback) {
        mAccountRequest.getAccessTokenThroughLogin(user, callback);
    }

    public void gcmRegistrationId(String authorize, OnServerResponseCallback<Object> callback) {
        ServiceGenerator.createService(GCMRequest.class, StorageFactory.getUserStorage(mContext).getUser().token)
                .getGMCRegistrationId(authorize, callback);
    }

    public void sendGCMToken(String token, OnServerResponseCallback<Object> callback){
        ServiceGenerator.createService(GCMRequest.class, StorageFactory.getUserStorage(mContext).getUser().token)
                .sendGCMToken(new GCMToken(token), callback);
    }

    public void coursesList(OnServerResponseCallback<CourseListModel[]> callback) {
        ServiceGenerator.createService(AccountRequests.class, StorageFactory.getUserStorage(mContext).getUser().token)
                .getGroupsInfo(callback);
    }

    public void courses(String groupId, OnServerResponseCallback<CourseModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getCourses(groupId, callback);
    }

    public void episodesWithScenes(String semesterId, OnServerResponseCallback<EpisodeModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getEpisodes(semesterId, callback);
    }

    public void scenes(String episodeId, OnServerResponseCallback<SceneModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getScenes(episodeId, callback);
    }

    public void tasks(String sceneId, OnServerResponseCallback<TaskModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getTasks(sceneId, callback);
    }

    public void register(final UserModel user, OnServerResponseCallback<UserModel> callback) {
        mAccountRequest.getAccessTokenThroughRegister(user, callback);
    }

    private void initEduMaterialExecutorIfNeeded(){
        if(mEduMaterialRequest==null)
          mEduMaterialRequest = ServiceGenerator.createService(EduMaterialRequests.class, StorageFactory.getUserStorage(mContext).getUser().token);
    }


    public void sendAudio(String audioId, TypedFile filePath, OnServerResponseCallback<Object> callback){
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.sendAudio(audioId, filePath, callback);
    }

    public void reset() {
        mAccountRequest = null;
        mEduMaterialRequest = null;
        mContext = null;
        mInstance = null;
    }
}
