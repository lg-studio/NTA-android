package com.usinformatics.nytrip.managers;

import android.content.Context;

import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.models.AudioFileIdModel;
import com.usinformatics.nytrip.models.ExecutedChatModel;
import com.usinformatics.nytrip.network.NetworkErrorHelper;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.network.models.NetAudioFileIdModel;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 30.07.2015.
 */
public class ChatRepository extends AbstractRepository {


    private final Context context;

    private ChatRepository(Context context){
        this.context=context;
    }

    public static ChatRepository newInstance(Context context){
        return new ChatRepository(context);
    }

    public void sendChatResult(ExecutedChatModel chat, final RepositoryCallback<AudioFileIdModel> callback){
        RequestExecutor.getInstance(context).sendChatResult(ModelBridge.getNetExecutedChatFrom(chat), new OnServerResponseCallback<NetAudioFileIdModel>() {
            @Override
            public void onResponse(NetAudioFileIdModel objects, Response responseBody, RetrofitError error) {
                String err= NetworkErrorHelper.getErrorString(context,error);
                if(err!=null)
                    notifyError(callback,err);
                else
                    notifySuccess(callback,ModelBridge.getAudioFileIdFrom(objects));
            }
        });
    }
}
