package com.usinformatics.nytrip.network.requestors;

import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.models.NetCourseModel;
import com.usinformatics.nytrip.network.models.NetEpisodeModel;
import com.usinformatics.nytrip.network.models.NetSceneModel;
import com.usinformatics.nytrip.network.models.NetTaskModel;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by D1m11n on 24.06.2015.
 */
public interface EduMaterialRequests {


    @GET(Api.URI_COURSES)
    public void getCourses(@Path("classid") String id, OnServerResponseCallback<NetCourseModel[]> callback);

    @GET(Api.URI_EPISODES_ALL)
    public void getEpisodes(@Path("courseid") String id, OnServerResponseCallback<EpisodeModel[]> callback);

    @GET(Api.URI_SCENES)
    public void getScenes(@Path("episodeid") String id, OnServerResponseCallback<SceneModel[]> callback);

    @GET(Api.URI_TASKS)
    public void getTasks(@Path("sceneid") String id, OnServerResponseCallback<NetTaskModel[]> callback);


    @GET(Api.URI_TASK)
    public void getTask(@Path("sceneid") String sceneId, @Path("taskid") String taskId, OnServerResponseCallback<NetTaskModel> callback);

    @Multipart
    @POST(Api.URI_AUDIO)
    public void sendAudio(@Path("audioId") String id, @Part("file") TypedFile filePath, OnServerResponseCallback<Object> callback);

    @GET(Api.URI_SCENE)
    public void getScene(@Path("sceneid") String sceneId, OnServerResponseCallback<NetSceneModel> callback);

    @GET(Api.URI_EPISODE)
    public void getEpisode(@Path("episodeid") String episodeId, OnServerResponseCallback<NetEpisodeModel> callback);


}
