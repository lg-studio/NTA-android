package com.usinformatics.nytrip.managers;

import android.content.Context;

import com.usinformatics.nytrip.converters.ModelBridge;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.network.NetworkErrorHelper;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.network.models.NetUserModel;
import com.usinformatics.nytrip.network.models.NetSendFeedbackModel;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 28.07.2015.
 */
public class AccountRepository extends AbstractRepository {

    private final Context context;

    private AccountRepository(Context context){
        this.context=context;
    }

    public static AccountRepository newInstance(Context context){
        return new AccountRepository(context);
    }

    public void login(final UserModel user, final RepositoryCallback<UserModel> callback){
        RequestExecutor.getInstance(context).login(ModelBridge.getNetUserFrom(user), new OnServerResponseCallback<NetUserModel>() {
            @Override
            public void onResponse(NetUserModel objects, Response responseBody, RetrofitError error) {
                String err = NetworkErrorHelper.getErrorString(context,error);
                if (err != null) {
                    notifyError(callback, err);
                    return;
                }
                objects.setPassword(user.getPassword());
                notifySuccess(callback, ModelBridge.getUserFrom(objects));
            }
        });
    }

    public void register(final UserModel user, final RepositoryCallback<UserModel> callback){
        RequestExecutor.getInstance(context).register(ModelBridge.getNetUserFrom(user), new OnServerResponseCallback<NetUserModel>() {
            @Override
            public void onResponse(NetUserModel objects, Response responseBody, RetrofitError error) {
                String err = NetworkErrorHelper.getErrorString(context,error);
                if (err != null) {
                    notifyError(callback, err);
                    return;
                }
                objects.setPassword(user.getPassword());
                notifySuccess(callback, ModelBridge.getUserFrom(objects));
            }
        });
    }

    public void sendFeedback(final NetSendFeedbackModel send, final RepositoryCallback<NetSendFeedbackModel> callback){
        RequestExecutor.getInstance(context).sendFeedback(send, new OnServerResponseCallback<Object>() {
            @Override
            public void onResponse(Object objects, Response responseBody, RetrofitError error) {
                String err=NetworkErrorHelper.getErrorString(context,error);
                if(err!=null)
                    notifyError(callback,err);
                else
                    notifySuccess(callback, null);
            }
        });
    }


}
