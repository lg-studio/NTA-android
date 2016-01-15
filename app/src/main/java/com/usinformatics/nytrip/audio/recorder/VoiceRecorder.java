package com.usinformatics.nytrip.audio.recorder;

import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.text.TextUtils;
import android.util.Log;

import com.ideaheap.io.VorbisFileOutputStream;

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
    private final Context mContext;
    private AudioRecord mRecorder;
    private AtomicBoolean mIsRunning = new AtomicBoolean(false);
    private boolean mDeleteFile = false;
    private Thread mAudioThread;
    private VorbisFileOutputStream mVorbisStream;
    private String mFileName;
    private VoiceRecorderCallback mCallback;
//
    public VoiceRecorder(Context context) {
        this.mContext = context;
    }
//
//
//
    public void start(File srcFile, VoiceRecorderConfig config, VoiceRecorderCallback callback) throws IOException {
        mCallback=callback;
        if(srcFile==null) {
            notifyError(VoiceRecorderCallback.RecordError.FILE, "FILE is null");
            return;
        }
        if(srcFile.exists()||!srcFile.createNewFile()){
            notifyError(VoiceRecorderCallback.RecordError.FILE, "Cannot create file");
            return;
        }
        start(srcFile.getAbsolutePath(), config);
    }

    public void start(File srcFile, VoiceRecorderCallback callback) throws IOException {
      start(srcFile, null, callback);
    }

//    public void start(String fileName, VoiceRecorderCallback callback) throws IOException{
//        start(fileName,null,callback);
//    }

    private void start(String fileName, VoiceRecorderConfig config) throws IOException {
        if (TextUtils.isEmpty(fileName)) {
            notifyError(VoiceRecorderCallback.RecordError.FILE, "Filename is Empty");
            return;
        }
        mFileName=fileName;
        initRecorder(config==null?new VoiceRecorderConfig():config);
        mAudioThread=new Thread(this);
        mAudioThread.start();
    }

    private void initRecorder(VoiceRecorderConfig config) {
        mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, config.getSampleRate(), config.getChannel(), config.getAudioFormat(), BUFF_COUNT);
    }

    public void stopAndSave() {
        mIsRunning.set(false);
    }

    public void stopNotSave() {
        mIsRunning.set(false);
        mDeleteFile = true;
    }

    public boolean isRunning() {
        return mIsRunning.get();
    }

    @Override
    public void run() {
        try {
            if (!tryStartRecord())
                return;
            mVorbisStream = new VorbisFileOutputStream(mFileName);
            short[] buffer = new short[BUFF_COUNT];
            mIsRunning.set(true);
            notifyStart();
            while (mIsRunning.get()) {
                int samplesRead = mRecorder.read(buffer, 0, buffer.length);
                if (isError(samplesRead))
                    return;
                tryRecordDataToOgg(buffer);
            }
        } catch (Exception e) {
            notifyError(VoiceRecorderCallback.RecordError.PROCESS, e.getMessage());
        } finally {
            release();
        }
    }

    private boolean tryRecordDataToOgg(short[] buffer) throws IOException {
        //Log.e(TAG, "buffer " + (buffer!=null?buffer.length:"null"));
        if (buffer == null) return false;
        mVorbisStream.write(buffer);
        return true;
    }

    private boolean tryStartRecord() {
        if(mRecorder.getState()==AudioRecord.ERROR_BAD_VALUE){
            notifyError(VoiceRecorderCallback.RecordError.STATE, "Wrong parameters");
        }
        if (mRecorder.getState() != AudioRecord.STATE_INITIALIZED) {
            notifyError(VoiceRecorderCallback.RecordError.STATE, "Recorder is not initalized");
            return false;
        }
        try {
            mRecorder.startRecording();
            return true;
        } catch (IllegalStateException e) {
            notifyError(VoiceRecorderCallback.RecordError.STATE, e.getMessage());
            return false;
        }
    }

    private boolean isError(int samplesRead) {
        if (samplesRead == AudioRecord.ERROR) {
            notifyError(VoiceRecorderCallback.RecordError.STATE, " getMinBufferSize returned ERROR");
            return true;
        }
        if(mRecorder.getState()==AudioRecord.ERROR_BAD_VALUE){
            notifyError(VoiceRecorderCallback.RecordError.STATE, "read bad value");
            return true;
        }
        return false;
    }

    private void release() {
        mIsRunning.set(false);
        if (mVorbisStream != null)
            try {
                mVorbisStream.close();
                mVorbisStream.flush();
                mVorbisStream = null;
            } catch (IOException e) {
                Log.e(TAG, "release" + e.toString());
            }
        if (mRecorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING)
            mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mAudioThread = null;
        if (mDeleteFile) {
            new File(mFileName).delete();
            notifyError(VoiceRecorderCallback.RecordError.FILE, "file was deleted");
        }else{
            notifyEnd();
        }
    }

    private void notifyError(VoiceRecorderCallback.RecordError error, String message) {
        Log.e(TAG, "error " + mCallback);
        if (mCallback != null) {
            mCallback.onError(error, message);
        }
    }

    private void notifyEnd() {
        if (mCallback != null) {
            mCallback.onEnd(mFileName);
        }
    }

    private void notifyStart() {
        if (mCallback != null) {
            mCallback.onStart();
        }
    }

}
