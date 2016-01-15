package com.usinformatics.nytrip.audio.recorder;

import com.ideaheap.io.VorbisFileOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by D1m11n on 07.07.2015.
 */
public class AudioTypesConverter {

    private static final int RESOLUTION_IN_BYTES = 2 ;
    private static final int CHANNELS = 1;
    private static final int BUFFER_SIZE=2048;




    public static byte[] getRecordingAsWav(byte[] pcm, int sampleRate) {
        int headerLen = 44;
        int totalDataLen = pcm.length + headerLen;
        int byteRate = sampleRate * RESOLUTION_IN_BYTES; // mSampleRate*(16/8)*1 ???
        int totalAudioLen = pcm.length;

        byte[] header = new byte[headerLen];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) CHANNELS;
        header[23] = 0;
        header[24] = (byte) (sampleRate & 0xff);
        header[25] = (byte) ((sampleRate >> 8) & 0xff);
        header[26] = (byte) ((sampleRate >> 16) & 0xff);
        header[27] = (byte) ((sampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8);  // block align
        header[33] = 0;
        header[34] = (byte) 8*RESOLUTION_IN_BYTES;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        byte[] wav = new byte[header.length + pcm.length];
        System.arraycopy(header, 0, wav, 0, header.length);
        System.arraycopy(pcm, 0, wav, header.length, pcm.length);
        return wav;
    }

    public static byte[] getRecordingAsWav(File f, int sampleRate) throws IOException {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        FileInputStream mFileReader = new FileInputStream(f);
        byte [] buffer= new byte[BUFFER_SIZE];
        int size=0;
        while((size=mFileReader.read(buffer))>0){
            stream.write(buffer, 0, size);
        }
        stream.flush();
        byte[] b= getRecordingAsWav(stream.toByteArray(),sampleRate);
        stream.close();
        mFileReader.close();
        return b;
    }


    public static void saveRecordingAsWav(File src,File output, int sampleRate) throws IOException {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        FileInputStream mFileReader = new FileInputStream(src);
        byte [] buffer= new byte[BUFFER_SIZE];
        int size=0;
        while((size=mFileReader.read(buffer))>0){
            stream.write(buffer, 0, size);
        }
        stream.flush();
        FileOutputStream str=new FileOutputStream(output);
        str.write(getRecordingAsWav(stream.toByteArray(),sampleRate));
        str.flush();
        str.close();
        mFileReader.close();
    }

    public static void saveRecordingAsOgg(File src,String fileName) throws IOException {
        VorbisFileOutputStream stream= new VorbisFileOutputStream(fileName);
        FileInputStream mFileReader = new FileInputStream(src);
        byte data[] = new byte[BUFFER_SIZE];
        int size=0;
        while((size=mFileReader.read(data))>0){
            stream.write(byte2short(data));
        }
        stream.flush();
        stream.close();
        mFileReader.close();
    }

    private static byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }

    private static short [] byte2short(byte [] bytes){
        short[] shorts = new short[bytes.length/2];
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().array();
    }


    public static  void testSquareWave() throws Exception {
        short buf[] = new short[1000];
        String fname = "/sdcard/squareWave.ogg";

        VorbisFileOutputStream s = new VorbisFileOutputStream(fname);
        // Write a square wave
        for (int j=0; j < 1000; ) {
            for (int i=0; i < 100 && j < 1000; i++) {
                buf[j++] = (short) (5000);
            }
            for (int i=0; i < 100 && j< 1000; i++) {
                buf[j++] = (short) (-5000);
            }
        }

        s.write(buf);
        for(int i=0; i < 500; i++) {
            s.write(buf);
        }
        s.close();
    }



}
