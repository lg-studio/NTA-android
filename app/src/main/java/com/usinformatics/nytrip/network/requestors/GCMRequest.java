package com.usinformatics.nytrip.network.requestors;

import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.models.GCMToken;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by admin on 7/14/15.
 */
public interface GCMRequest {

    @POST(Api.URI_PUSH_NOTIFICATIONS)
    public void sendGCMToken(@Body GCMToken token, OnServerResponseCallback<Object> callback);

    @GET(Api.URI_PUSH_NOTIFICATIONS)
    public void getGMCRegistrationId(@Header("Authorization") String authorization, OnServerResponseCallback<Object> callback);
}
