package com.usinformatics.nytrip.audio.recorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by D1m11n on 06.07.2015.
 */
public class VoiceRecorder implements Runnable {

//
    private static final String TAG = "AUDIO_THREAD";
    private static int BUFF_COUNT = 2048;
    private int BITRATE=80*1024;
    private final Context mContext;
    private MediaRecorder mRecorder;

    private AtomicBoolean mIsRunning = new AtomicBoolean(false);
    private boolean mDeleteFile = false;
    private Thread mAudioThread;
    private String mFileName;
    private VoiceRecorderCallback mCallback;
//
    public VoiceRecorder(Context context) {
        this.mContext = context;
    }
//
//
//
    public void start(File srcFile, MediaRecorderConfig config, VoiceRecorderCallback callback)  {
        try {
            mCallback = callback;
            if (srcFile == null) {
//                notifyError(VoiceRecorderCallback.RecordError.FILE, "FILE is null");
                return;
            }
            if (!srcFile.exists() && !srcFile.createNewFile()) {
//                notifyError(VoiceRecorderCallback.RecordError.FILE, "Cannot create file");
                return;
            }
            start(srcFile.getAbsolutePath(), config);
        }catch (IOException e){
//            notifyError(VoiceRecorderCallback.RecordError.PROCESS, e.getMessage());
        }
    }

    public void start(File srcFile, VoiceRecorderCallback callback)  {
            start(srcFile, null, callback);
    }

    private void start(String fileName, MediaRecorderConfig config) {
        if (TextUtils.isEmpty(fileName)) {
//            notifyError(VoiceRecorderCallback.RecordError.FILE, "Filename is Empty");
            return;
        }
        mFileName=fileName;
        initRecorder(config==null?new MediaRecorderConfig():config);
        mAudioThread=new Thread(this);
        mAudioThread.start();
    }

    private void initRecorder(MediaRecorderConfig config) {
        mRecorder  = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioEncodingBitRate(BITRATE);
    }

    public void stopAndSave() {
        mIsRunning.set(false);
        release();
    }

    public void stopNotSave() {
        mIsRunning.set(false);
        mDeleteFile = true;
        release();
    }

    public boolean isRunning() {
        return mIsRunning.get();
    }

    @Override
    public void run() {
        try {
            mRecorder.prepare();
            mRecorder.start();
            notifyStart();
        } catch (Exception e) {
//            notifyError(VoiceRecorderCallback.RecordError.PROCESS, e.getMessage());
        }
    }

    private void release() {
        mIsRunning.set(false);
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
        }
        mRecorder = null;
        mAudioThread = null;
        if (mDeleteFile) {
            new File(mFileName).delete();
//            notifyError(VoiceRecorderCallback.RecordError.FILE, "file was deleted");
        }else{
//            notifyEnd();
        }
    }
//
//    private void notifyError(VoiceRecorderCallback.RecordError error, String message) {
//        Log.e(TAG, "error " + mCallback);
//        if (mCallback != null) {
//            mCallback.onError(error, message);
//        }
//    }
//
//    private void notifyEnd() {
//        if (mCallback != null) {
//            mCallback.onEnd(mFileName);
//        }
//    }

    private void notifyStart() {
        if (mCallback != null) {
            mCallback.onStart();
        }
    }

}
