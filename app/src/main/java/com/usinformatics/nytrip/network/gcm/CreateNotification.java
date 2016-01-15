package com.usinformatics.nytrip.network.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.selection.TasksSelectionActivity;

/**
 * Created by admin on 7/14/15.
 */
public class CreateNotification {

    private Context mContext;

    public CreateNotification (Context context){
        mContext = context;
    }


    public void buildNotification(){
        Intent notificationIntent = new Intent(mContext, TasksSelectionActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                1, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = mContext.getResources();

        Notification notification = new Notification.Builder(mContext)
                .setContentIntent(contentIntent)
                .setContentTitle("Title ")
                .setContentText("content")
                .setSmallIcon(R.mipmap.ic_launcher).getNotification();

    }
}
