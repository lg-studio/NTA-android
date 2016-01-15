package com.usinformatics.nytrip.network.voice;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.usinformatics.nytrip.audio.recorder.VoiceRecorder;
import com.usinformatics.nytrip.audio.recorder.VoiceRecorderCallback;
import com.usinformatics.nytrip.databases.AudioDbManager;
import com.usinformatics.nytrip.databases.model.AudioModel;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.preferences.PrefsUser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by admin on 7/22/15.
 */
public class SendVoice {

    private static final String AUDIO_FORMAT = ".wav";
    private Context mContext;
    private VoiceRecorder voiceRecorder;
    private File file;
    private String mTaskName;

    public SendVoice(Context context, String taskName) {
        mContext = context;
        voiceRecorder = new VoiceRecorder(context);
        mTaskName = taskName;
    }

    public void stopRecording() {
        voiceRecorder.stopAndSave();
    }

    public void startRecording(AudioModel audio) {
        generateFilePath(audio);

        try {
            file.createNewFile();

            voiceRecorder.start(file, new VoiceRecorderCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onEnd(String fileName) {

                }

                @Override
                public void onError(RecordError error, String message) {

                }


            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateFilePath(AudioModel audio) {
        String filePath = Environment.getExternalStorageDirectory()+ "/NYTrip/"+  audio.getAudioId()+ AUDIO_FORMAT;
        file = new File(Environment.getExternalStorageDirectory()+ "/NYTrip");

        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(filePath);
        audio.setFilePath(filePath);
        saveAudioToBD(audio);
    }

    private void saveAudioToBD(AudioModel audio) {
        AudioDbManager.getInstance(mContext).saveAudio(audio);
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if (file.getName().endsWith(AUDIO_FORMAT)) {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    private void findAndSendAddAudio() {
        TypedFile typedFile;
        List<File> files = getListFiles(new File(getTaskPath()));
        for (File currentFile :
                files) {
            typedFile = new TypedFile("multipart/form-data", currentFile);
            sendAudioToServer(typedFile);
        }
    }

    public void sendAudioToServer(TypedFile file) {
        RequestExecutor.getInstance(mContext).sendAudio("1", file, new OnServerResponseCallback<Object>() {
            @Override
            public void onResponse(Object objects, Response responseBody, RetrofitError error) {
                Log.d("sendAudioFile", "onResponse");
            }
        });
    }

    private String getTaskPath() {
        return PrefsUser.getInstance(mContext).getCurrentTaskPath();
    }
}
