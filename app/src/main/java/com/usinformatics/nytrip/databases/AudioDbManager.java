package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.usinformatics.nytrip.databases.dao.AudioDao;
import com.usinformatics.nytrip.databases.model.AudioModel;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by admin on 7/27/15.
 */
public class AudioDbManager {


    private static AudioDbManager mInstance;
    private Context mContext;
    private static AudioDBHelper mDatabaseHelper;
    private AudioDao audioDao;

    private AudioDbManager(Context context) {
        mContext = context.getApplicationContext();

        if (mDatabaseHelper == null) {
            mDatabaseHelper = AudioDBHelper.getHelper(mContext);
        }

        try {
            audioDao = new AudioDao(mDatabaseHelper.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AudioDbManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (EduMaterialDbManager.class) {
                if (mInstance == null)
                    mInstance = new AudioDbManager(context);
            }
        }
        return mInstance;
    }

    public void saveAudio(AudioModel audio){
        try {
            deleteIfExist(audio);
            audioDao.createAudio(audio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteIfExist(AudioModel audio) throws SQLException {
        boolean isExist = false;

            isExist = audioDao.audioIsExist(audio);
            if(isExist){
                audioDao.deleteAudioById(audio.getAudioId());
                deleteAudioFile(audio.getFilePath());
            }
    }

    private void deleteAudioFile(String filePath) {
        File file = new File(filePath);
        boolean deleted = file.delete();
        Log.d("deleteAudioFile", deleted+"");
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        mDatabaseHelper = null;
    }
}
