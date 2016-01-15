package com.usinformatics.nytrip.ui.additional.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.helpers.ToastHelper;

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
       showSimpleOneButtonDialog(context, title, message, null);
    }

    public static void showSimpleOneButtonDialog(final Context context, String title, String message, final OnOkClickListener listener){
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
                if(listener!=null)
                    listener.wasOkClicked(dlg, true);
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

    /**
     *
     * @param context
     * @param listener ok -> tutor selected , false -> recognition selected
     */
    public static void showModeRatingSelection(final Context context, final OnOkClickListener listener){
        View vw=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dlg_mode_rating, null, false);
        final Dialog dlg=new AlertDialog.Builder(context)
                .setTitle("")
                .setView(vw)
                .show();
        vw.findViewById(R.id.tv_recognize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.wasOkClicked(dlg,false);
                dlg.dismiss();
            }
        });
        vw.findViewById(R.id.tv_tutor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener!=null)
                    listener.wasOkClicked(dlg,true);
                dlg.dismiss();

            }
        });
    }

    /**
     *
     * @param context
     * @param listener ok -> try again  selected , false -> skip  selected
     */
    public static void showLowMarkInfoSelection(final Context context, String taskName, String markPercent, final OnOkClickListener listener){
        View vw=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dlg_low_mark, null, false);
        ((TextView)vw.findViewById(R.id.tv_taskname)).setText(taskName);
        ((TextView)vw.findViewById(R.id.tv_mark_percent)).setText(String.valueOf(markPercent) + "%");
        final Dialog dlg=new AlertDialog.Builder(context)
                .setTitle("")
                .setView(vw)
                .setCancelable(false)
                .show();
        vw.findViewById(R.id.tv_try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.wasOkClicked(dlg,true);
                dlg.dismiss();
            }
        });
        vw.findViewById(R.id.tv_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.wasOkClicked(dlg,false);
                dlg.dismiss();

            }
        });
    }

    /**
     *
     * @param context
     * @param listener ok -> try again  selected , false -> skip  selected
     */
    public static void showFeedback(final Activity context, final FeedbackCallback listener){
        View vw=((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dlg_feedback, null, false);
        final Dialog dlg=new AlertDialog.Builder(context)
                .setTitle("")
                .setView(vw)
                .setCancelable(true)
                .show();
        vw.findViewById(R.id.feedback_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener==null){
                    dlg.dismiss();
                    return;
                }
                String theme=((EditText)dlg.findViewById(R.id.feedback_theme)).getText().toString();
                String body=((EditText)dlg.findViewById(R.id.feedback_body)).getText().toString();
                if(TextUtils.isEmpty(body)||TextUtils.isEmpty(theme)){
                    ToastHelper.showSimple(context, "ALL FIELDS MUST BE NOT EMPTY");
                    return;
                }
                listener.onFeedback(theme, body);
                dlg.dismiss();
            }
        });
    }



    public static void showRecordDialog(final Activity activity , int duration, final RecordProcessCallback callback){
        new RecordProcessDialog(activity).show(callback,duration);
    }

    public static void showRecordDialog(final Activity activity ,  final RecordProcessCallback callback){
        new RecordProcessDialog(activity).show(callback);
    }

    //TODO ADD HERE UPDATER
    public static interface RecordStateUpdater{

        public void updateTime(String now);

        public void finish();
    }

    public static ProgressDialog newProgressDialog(Context context) {
        return newProgressDialog(context, "Loading...");
    }

    public static ProgressDialog newProgressDialog(Context context, String message) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(message);
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        return pd;
    }

    public static interface RecordProcessCallback{

        public void started();

        public void stopped();

        public void aborted();
    }

    public static interface FeedbackCallback{

        public void onFeedback(String theme, String body);
    }
}

