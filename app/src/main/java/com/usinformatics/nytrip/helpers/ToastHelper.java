package com.usinformatics.nytrip.helpers;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by D1m11n on 16.06.2015.
 */
public class ToastHelper {

    private ToastHelper(){}

    public static void showSimple(Activity activity, String text){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
}
