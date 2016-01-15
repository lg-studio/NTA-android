package com.usinformatics.nytrip.helpers;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by D1m11n on 16.07.2015.
 */
public class ViewHelper {

    public static  void setBackground(Activity activity,View view, int idDrawable){
//        if(activity==null||view==null) return;
        Drawable draw=ContextCompat.getDrawable(activity, idDrawable);
//        if(Build.VERSION.SDK_INT>=21)
//            draw=activity.getResources().getDrawable(idDrawable, activity.getTheme());
//        else
//            draw =activity.getResources().getDrawable(idDrawable);
        if(Build.VERSION.SDK_INT<16)
            view.setBackgroundDrawable(draw);
        else
            view.setBackground(draw);
    }
}
