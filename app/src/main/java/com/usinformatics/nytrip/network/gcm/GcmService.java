package com.usinformatics.nytrip.network.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.usinformatics.nytrip.network.gcm.models.ContentChangedModel;

/**
 * Created by admin on 7/8/15.
 */
public class GcmService extends GcmListenerService {

    private static final String TAG=GcmService.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String res=(data!=null?data.toString():"bundle is null");
        Log.e(TAG, "RES = " + res);
        if(res.contains("type"))
            onContentChanged(data);
    }

    private void onContentChanged(Bundle data){
        ContentChangedModel m= new ContentChangedModel();
        m.setType(data.getString("type"));
        m.courseId=data.getString("courseId");
        m.episodeId=data.getString("episodeId");
        m.sceneId=data.getString("sceneId");
        m.taskId=data.getString("taskId");
        Intent intent=new ContentChangedHandler(this.getApplicationContext(),m).startChange();
        trySendIntent(intent);
    }

    private void trySendIntent(Intent intent) {
        if(intent==null)
            return;
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


}
