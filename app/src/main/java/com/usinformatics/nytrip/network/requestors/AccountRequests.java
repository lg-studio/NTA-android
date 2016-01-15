package com.usinformatics.nytrip.network.requestors;

import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.models.NetGroupModel;
import com.usinformatics.nytrip.network.models.NetUserModel;
import com.usinformatics.nytrip.network.models.NetSendFeedbackModel;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by D1m11n on 19.06.2015.
 */
public interface AccountRequests {

    @POST(Api.URI_REGISTER)
    public void getAccessTokenThroughRegister(@Body NetUserModel user, OnServerResponseCallback<NetUserModel> callback);

    @POST(Api.URI_LOGIN)
    public void getAccessTokenThroughLogin(@Body NetUserModel user, OnServerResponseCallback<NetUserModel> callback);

    @GET(Api.URI_CLASS)
    public void getGroupsInfo(OnServerResponseCallback<NetGroupModel[]> callback);

    @POST(Api.FEEDBACK)
    public void sendFeedback(@Body NetSendFeedbackModel feedback, OnServerResponseCallback<Object> callback);





}
