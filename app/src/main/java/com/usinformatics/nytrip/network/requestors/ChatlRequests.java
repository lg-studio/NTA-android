package com.usinformatics.nytrip.network.requestors;

import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.models.NetAudioFileIdModel;
import com.usinformatics.nytrip.network.models.NetExecutedChatModel;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by D1m11n on 24.06.2015.
 */
public interface ChatlRequests {

    @POST(Api.URI_SEND_CHAT_RESULT)
    public void sendChatResult(@Body NetExecutedChatModel executedChat, OnServerResponseCallback<NetAudioFileIdModel> callback);
}
