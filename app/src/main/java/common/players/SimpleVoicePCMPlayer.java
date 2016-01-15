package common.players;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by D1m11n on 07.07.2015.
 */
public class SimpleVoicePCMPlayer {

    private static final String TAG = "VOICE_PLAYER";
    private final PCMConfig config;


  public SimpleVoicePCMPlayer(){
        this.config=new PCMConfig();
    }

    public void PlayShortAudioFileViaAudioTrack(String filePath) throws IOException {
        // We keep temporarily filePath globally as we have only two sample sounds now..
        if (filePath==null)
            return;

        //Reading the file..
        File file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or "/sdcard/samplesound.wav"
        byte[] byteData = new byte[(int) file.length()];
        Log.d(TAG, (int) file.length() + "");

        FileInputStream in = null;
        try {
            in = new FileInputStream( file );
            in.read( byteData );
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Set and push to audio track..
        int intSize = android.media.AudioTrack.getMinBufferSize(config.getSampleRate(), config.getChannel(), config.getAudioFormat());
        Log.e(TAG, intSize+"");

        AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, config.getSampleRate(), AudioFormat.CHANNEL_IN_STEREO, config.getAudioFormat(), intSize, AudioTrack.MODE_STREAM);
        if (at!=null) {
            at.play();
            // Write the byte array to the track
            at.write(byteData, 0, byteData.length);
            at.stop();
            at.release();
        }
        else
            Log.d(TAG, "audio track is not initialised ");

    }
}
