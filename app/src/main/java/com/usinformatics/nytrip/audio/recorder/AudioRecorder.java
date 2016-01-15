package com.usinformatics.nytrip.audio.recorder;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by D1m11n on 06.07.2015.
 */
public class AudioRecorder {


//    public static final int REQUEST_RECORD = 501;
//
//    private Activity mActivity;
//
//    private AudioRecorder(Activity activity) {
//        mActivity = activity;
//    }
//
//    public static AudioRecorder newInstance(Activity activity) {
//        return new AudioRecorder(activity);
//    }
//
//    public void start() {
//        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//        if (isAvailable(mActivity.getApplicationContext(), intent)) {
//            mActivity.startActivityForResult(intent,
//                    REQUEST_RECORD);
//        }
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//
//    }
//
//    public static boolean isAvailable(Context ctx, Intent intent) {
//        final PackageManager mgr = ctx.getPackageManager();
//        List<ResolveInfo> list =
//                mgr.queryIntentActivities(intent,
//                        PackageManager.MATCH_DEFAULT_ONLY);
//        return list.size() > 0;
//    }

    MediaRecorder recorder = null;



    private void startRecording(File file) {
        if (recorder != null) {
            recorder.release();
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(file.getAbsolutePath());
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("giftlist", "io problems while preparing [" +
                    file.getAbsolutePath() + "]: " + e.getMessage());
        }
    }

    public void startRecording(File file, int millisDuration) {
        if (millisDuration<1000) return;
        if (recorder != null) {
            recorder.release();
        }
        recorder = new MediaRecorder();
        recorder.setMaxDuration(millisDuration);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(file.getAbsolutePath());
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("giftlist", "io problems while preparing [" +
                    file.getAbsolutePath() + "]: " + e.getMessage());
        }
    }
}
