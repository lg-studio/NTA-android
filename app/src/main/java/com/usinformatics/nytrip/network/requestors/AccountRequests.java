package com.usinformatics.nytrip.network.requestors;

import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.models.CourseListModel;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by D1m11n on 19.06.2015.
 */
public interface AccountRequests {

    @POST(Api.URI_REGISTER)
    public void getAccessTokenThroughRegister(@Body UserModel user, OnServerResponseCallback<UserModel> callback);

    @POST(Api.URI_LOGIN)
    public void getAccessTokenThroughLogin(@Body UserModel user, OnServerResponseCallback<UserModel> callback);

    @GET(Api.URI_CLASS)
    public void getGroupsInfo(OnServerResponseCallback<CourseListModel[]> callback);





}
