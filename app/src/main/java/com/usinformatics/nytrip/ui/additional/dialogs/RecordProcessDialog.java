package com.usinformatics.nytrip.ui.additional.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.R;

/**
 * Created by D1m11n on 23.07.2015.
 */
public class RecordProcessDialog extends Dialog implements DialogFactory.RecordStateUpdater {


    private DialogFactory.RecordProcessCallback mCallback;

    private int mDuration= AppConsts.MAX_RECORD_DURATIONs;

    private long mStartTime=-1L;

    private static final int TICK=100;

    private String mstrStartRecord, mstrFinish;

    private int mRedColor;
    private int mBlackColor;

    public RecordProcessDialog(Context context) {
        super(context);
    }

    public RecordProcessDialog(Context context, int theme) {
        super(context, theme);
    }

    protected RecordProcessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private TextView mTitle;
    private TextView mTimeShower;
    private CountDownTimer mTimer;
    private ProgressWheel mProgressWheel;
    private boolean isClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_record);
        mstrStartRecord =getContext().getString(R.string.CLICK_TO_START_RECORD);
        mstrFinish =getContext().getString(R.string.STOP_TO_FINISH_RECORDING);
        mBlackColor=getContext().getResources().getColor(R.color.black_54);
        mRedColor=getContext().getResources().getColor(R.color.red_54);
        mProgressWheel= (ProgressWheel) findViewById(R.id.progress_wheel);
        mTitle=(TextView)findViewById(R.id.record_title);
        mTimeShower=(TextView)findViewById(R.id.record_timer);
        mTimeShower.setTextColor(mBlackColor);
        findViewById(R.id.record_mic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer == null) {
                    initTimer();
                    isClicked = true;
                    return;
                }
                if(isClicked)
                isClicked = false;
                RecordProcessDialog.this.dismiss();
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                if (mCallback !=null&&mTimer==null) {
//                    mCallback.aborted();
//                    return;
//                }
              //  if(isClicked) {
                    finish();
               // }
            }
        });
    }

    public void show(DialogFactory.RecordProcessCallback callback) {
        super.show();
        this.mCallback =callback;
    }

    public void show(DialogFactory.RecordProcessCallback callback, int durationSec) {
        super.show();
        mDuration=(durationSec<0)?AppConsts.MAX_RECORD_DURATIONs:durationSec;
        updateTextInTimeShower(mDuration);
        mTitle.setText(mstrStartRecord);
        this.mCallback = callback;

    }

    @Override
    public void updateTime(String now) {
    }

    @Override
    public void finish() {
        //if(mTimer!=null&& mCallback !=null) {

        //}
        if(isShowing()) {
            RecordProcessDialog.this.dismiss();
        }
        mCallback.stopped();

    }

    private void initTimer(){
       if(mTimer!=null) {
           mTimer.cancel();
       }
        mTitle.setText(mstrFinish);
        mProgressWheel.setProgress(1);
        RecordProcessDialog.this.mTimeShower.setText("0:" + mDuration);
        mStartTime= SystemClock.elapsedRealtime();
        mTimer= new CountDownTimer(mDuration*1000, TICK) {
            @Override
            public void onTick(long millisUntilFinished) {
               updateTextInTimeShower(getTickTime());
            }

            @Override
            public void onFinish() {
                mTimer=null;
                RecordProcessDialog.this.dismiss();
            }
        };
        mTimer.start();
        if(mCallback !=null)
            mCallback.started();
    }

    private void updateTextInTimeShower(int time) {
        RecordProcessDialog.this.mTimeShower.setText(getTextTimer(time));
        if(time<=5){
            RecordProcessDialog.this.mTimeShower.setTextColor(mRedColor);
        }
    }

    private String getTextTimer(int time){
       return  "0:" +(time<10?"0"+time:time);
    }

    private int getTickTime(){
        return mDuration -(int) ((SystemClock.elapsedRealtime() -mStartTime)/1000);
    }
}
