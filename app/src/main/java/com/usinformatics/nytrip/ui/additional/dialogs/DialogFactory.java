package com.usinformatics.nytrip.ui.additional.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.usinformatics.nytrip.R;

/**
 * Created by D1m11n on 24.06.2015.
 */
public final class DialogFactory {


    private static final String TAG = "DIALOG_FACTORY";

    public static interface OnOkClickListener{

        public void wasOkClicked(DialogInterface dialog,boolean isOk);

    }

    private DialogFactory(){}

    public static void showSimpleOneButtonDialog(final Context context, String title, String message){
        Log.e(TAG, "title = " + title + ", message = " + message );
        View vw=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dlg_simple, null, false);
        ((TextView)vw.findViewById(R.id.dlg_title)).setText(String.valueOf(title));
        ((TextView) vw.findViewById(R.id.dlg_message)).setText(String.valueOf(message));
        vw.findViewById(R.id.dlg_disagree).setVisibility(View.GONE);
        final Dialog dlg=new AlertDialog.Builder(context)
                .setTitle("")
                .setView(vw)
                .show();
        Log.e(TAG, "dialog is showing = " + dlg.isShowing());
        vw.findViewById(R.id.dlg_agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "dialog was dismissed ");
                dlg.dismiss();
            }
        });
    }

    public static void showSimpleTwoButtonsDialog(final Context context, String title, String message, final OnOkClickListener listener){
        View vw=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dlg_simple, null, false);
        ((TextView)vw.findViewById(R.id.dlg_title)).setText(String.valueOf(title));
        ((TextView) vw.findViewById(R.id.dlg_message)).setText(String.valueOf(message));
        final Dialog dlg=new AlertDialog.Builder(context)
                .setTitle("")
                .setView(vw)
                .show();
        vw.findViewById(R.id.dlg_agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.wasOkClicked(dlg,true);
                dlg.dismiss();

                
            }
        });
        vw.findViewById(R.id.dlg_disagree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener!=null)
                    listener.wasOkClicked(dlg,false);
                dlg.dismiss();

            }
        });
    }

    //https://github.com/rahatarmanahmed/CircularProgressView
    public static Dialog buildProgressCircularBar(){
        return null;
    }

}
