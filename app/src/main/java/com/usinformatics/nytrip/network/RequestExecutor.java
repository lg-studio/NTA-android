package com.usinformatics.nytrip.network;

import android.content.Context;

import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.network.models.NetAudioFileIdModel;
import com.usinformatics.nytrip.network.models.NetCourseModel;
import com.usinformatics.nytrip.network.models.NetEpisodeModel;
import com.usinformatics.nytrip.network.models.NetExecutedChatModel;
import com.usinformatics.nytrip.network.models.NetGCMToken;
import com.usinformatics.nytrip.network.models.NetGroupModel;
import com.usinformatics.nytrip.network.models.NetSceneModel;
import com.usinformatics.nytrip.network.models.NetTaskModel;
import com.usinformatics.nytrip.network.models.NetUserModel;
import com.usinformatics.nytrip.network.models.NetSendFeedbackModel;
import com.usinformatics.nytrip.network.requestors.AccountRequests;
import com.usinformatics.nytrip.network.requestors.ChatlRequests;
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

    public void login(final NetUserModel user, OnServerResponseCallback<NetUserModel> callback) {
        mAccountRequest.getAccessTokenThroughLogin(user, callback);
    }

    public void gcmRegistrationId(String authorize, OnServerResponseCallback<Object> callback) {
        ServiceGenerator.createService(GCMRequest.class, StorageFactory.getUserStorage(mContext).getUser().token)
                .getGMCRegistrationId(authorize, callback);
    }

    public void sendGCMToken(String token, OnServerResponseCallback<Object> callback){
        ServiceGenerator.createService(GCMRequest.class, StorageFactory.getUserStorage(mContext).getUser().token)
                .sendGCMToken(new NetGCMToken(token), callback);
    }

    public void coursesList(OnServerResponseCallback<NetGroupModel[]> callback) {
        ServiceGenerator.createService(AccountRequests.class, StorageFactory.getUserStorage(mContext).getUser().token)
                .getGroupsInfo(callback);
    }

    public void courses(String classId, OnServerResponseCallback<NetCourseModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getCourses(classId, callback);
    }

    public void episodesWithScenes(String semesterId, OnServerResponseCallback<EpisodeModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getEpisodes(semesterId, callback);
    }

    public void scenes(String episodeId, OnServerResponseCallback<SceneModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getScenes(episodeId, callback);
    }

    public void scene(String sceneId, OnServerResponseCallback<NetSceneModel> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getScene(sceneId, callback);
    }

    public void episode(String episodeId, OnServerResponseCallback<NetEpisodeModel> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getEpisode(episodeId, callback);
    }

    public void tasks(String sceneId, OnServerResponseCallback<NetTaskModel[]> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getTasks(sceneId, callback);
    }

    public void task(String sceneId,String taskId, OnServerResponseCallback<NetTaskModel> callback) {
        initEduMaterialExecutorIfNeeded();
        mEduMaterialRequest.getTask(sceneId,taskId, callback);
    }

    public void register(final NetUserModel user, OnServerResponseCallback<NetUserModel> callback) {
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

    public void sendChatResult(NetExecutedChatModel chat, final OnServerResponseCallback<NetAudioFileIdModel> callback){
        ServiceGenerator.createService(ChatlRequests.class, StorageFactory.getUserStorage(mContext).getUser().token).sendChatResult(chat,callback);
    }

    public void sendFeedback(NetSendFeedbackModel model, final OnServerResponseCallback<Object> callback){
        mAccountRequest.sendFeedback(model,callback);
    }

    public void reset() {
        mAccountRequest = null;
        mEduMaterialRequest = null;
        mContext = null;
        mInstance = null;
    }



}
