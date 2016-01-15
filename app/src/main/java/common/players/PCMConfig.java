package common.players;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

/**
 * Created by D1m11n on 06.07.2015.
 * http://stackoverflow.com/questions/17192256/recording-wav-with-android-audiorecorder
 *
 * http://stackoverflow.com/questions/8499042/android-audiorecord-example
 * http://habrahabr.ru/post/137708/
 */
class PCMConfig {


    private int[] rates = {8000, 11025, 22050,44100, 48000, 96000 };
    private int[] chans = {AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO};
    private int[] encs  = {AudioFormat.ENCODING_PCM_16BIT, AudioFormat.ENCODING_PCM_8BIT};

    private static int sampleRate=44100;
    private static int channel= AudioFormat.CHANNEL_IN_MONO;
    private static int audioFormat= AudioFormat.ENCODING_PCM_16BIT;


    int getSampleRate() {
        return sampleRate;
    }

    int getChannel() {
        return channel;
    }

    int getAudioFormat() {
        return audioFormat;
    }

    PCMConfig(){
        if(sampleRate>0)
            return;
        if(!generateCorrectConfig()){
            Log.e("RECOREDER_CONFIG","Cannot generate correct config");
        }
    }

    int getInternalBufferSize(){
       return AudioRecord.getMinBufferSize(sampleRate,
                channel, audioFormat)*10;
    }

    private boolean generateCorrectConfig(){
        for(int enc : encs){
            for(int ch : chans){
                for(int rate : rates){
                    int t = AudioRecord.getMinBufferSize(rate, ch, enc);
                    if((t != AudioRecord.ERROR) && (t != AudioRecord.ERROR_BAD_VALUE)){
                        channel=ch;
                        audioFormat=enc;
                        sampleRate=rate;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
