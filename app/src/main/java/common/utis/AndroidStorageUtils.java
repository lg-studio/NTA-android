package common.utis;

import android.os.Environment;

import java.io.File;

/**
 * Created by D1m11n on 21.07.2015.
 */
public final class AndroidStorageUtils {

    private AndroidStorageUtils(){}

    public static long getFreeExternalStorageBytes(){
        return Environment.getExternalStorageDirectory().getFreeSpace();
    }

    public static boolean isEnoughExternalStorageMemory(long bytesToSave){
        return getFreeExternalStorageBytes()>bytesToSave;
    }

    public static boolean isEnoughExternalStorageMemory(File f){
        if(f==null||!f.exists()) return true;
        return getFreeExternalStorageBytes()>f.length();
    }

}
