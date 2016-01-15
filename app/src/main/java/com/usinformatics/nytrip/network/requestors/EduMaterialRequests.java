package com.usinformatics.nytrip.network.requestors;

import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.TaskModel;
import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.network.OnServerResponseCallback;

import retrofit.http.FormUrlEncoded;
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
    public void getCourses(@Path("classid") String id, OnServerResponseCallback<CourseModel[]> callback);

    @GET(Api.URI_EPISODES_ALL)
    public void getEpisodes(@Path("courseid") String id, OnServerResponseCallback<EpisodeModel[]> callback);

    @GET(Api.URI_SCENES)
    public void getScenes(@Path("episodeid") String id, OnServerResponseCallback<SceneModel[]> callback);

    @GET(Api.URI_TASKS)
    public void getTasks(@Path("sceneid") String id, OnServerResponseCallback<TaskModel[]> callback);

    @Multipart
    @POST(Api.URI_AUDIO)
    public void sendAudio(@Path("audioId") String id, @Part("file") TypedFile filePath, OnServerResponseCallback<Object> callback);

}
