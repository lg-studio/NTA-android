package com.usinformatics.nytrip.network.gcm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.usinformatics.nytrip.R;

import java.io.IOException;

/**
 * Created by admin on 7/8/15.
 */
public class GcmService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
    }

}
