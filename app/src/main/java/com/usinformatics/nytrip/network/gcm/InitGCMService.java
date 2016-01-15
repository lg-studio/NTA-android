package com.usinformatics.nytrip.network.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.securepreferences.SecurePreferences;
import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.storages.StorageFactory;

import java.io.IOException;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by admin on 7/13/15.
 */
public class InitGCMService extends IntentService {


    public InitGCMService() {
        super("InitGCMService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getGCMRegistrationId(intent.getStringExtra(getString(R.string.user_token)));
    }

    private void getGCMRegistrationId(String userToken) {
        RequestExecutor.getInstance(this).gcmRegistrationId(userToken, new OnServerResponseCallback<Object>() {
            @Override
            public void onResponse(Object programId, Response responseBody, RetrofitError error) {
                if (programId != null) {
                    initGCM(programId);
                }
            }
        });
    }

    private void initGCM(Object programId) {
        String projectId = ((Map<String, String>) programId).get(AppConsts.PROJECT_ID);
        if (!isGCMInstanceIdExist()) {
            saveProjectId(projectId);

                SendInstanceId sendInstanceId = new SendInstanceId(this);
                sendInstanceId.start();
        }
    }

    private boolean isGCMInstanceIdExist() {
        SecurePreferences preferences = new SecurePreferences(this);
        if (preferences.getString(AppConsts.GCM_INSTANCE_ID, "").isEmpty()) {
            return false;
        }
        return true;
    }

    private void saveProjectId(String projectId) {
        StorageFactory.getAppStorage(this).saveProjectId(projectId);
    }

    public String getAuthorizedId() {
        SecurePreferences preferences = new SecurePreferences(this);
        return preferences.getString(AppConsts.PROJECT_ID, "");
    }

    private class SendInstanceId extends Thread{

        private Context context;

        public SendInstanceId(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                generateAndSendInstanceId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void generateAndSendInstanceId() throws IOException {
            InstanceID instanceID = InstanceID.getInstance(context);
            instanceID.deleteInstanceID();
            String token = instanceID.getToken(getAuthorizedId(),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            StorageFactory.getAppStorage(context).saveInstanceId(token);

            sendInstanceId(token);
        }

        private void sendInstanceId(String token) {
            RequestExecutor.getInstance(context).sendGCMToken(token, new OnServerResponseCallback<Object>() {
                @Override
                public void onResponse(Object programId, Response responseBody, RetrofitError error) {
                    if (programId != null) {
                    }
                }
            });
        }
    }
}
