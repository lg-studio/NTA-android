package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.usinformatics.nytrip.databases.model.AudioModel;

import java.sql.SQLException;

/**
 * Created by admin on 7/27/15.
 */
public class AudioDBHelper extends OrmLiteSqliteOpenHelper {


    private static final String TAG = "DATABASE_HELPER";

    private static final String DB_NAME = "audio";
    private static final int DB_VER = 1;
    private Dao<AudioModel, Integer> audioDAO = null;
    private static AudioDBHelper audioDBHelper;

    public AudioDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AudioModel.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, AudioModel.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Dao<AudioModel, Integer> getDao() throws SQLException {
        if (audioDAO == null) {
            audioDAO = getDao(AudioModel.class);
        }
        return audioDAO;
    }

    public static synchronized AudioDBHelper getHelper(Context context) {
        if (audioDBHelper == null) {
            audioDBHelper = new AudioDBHelper(context);
        }
        return audioDBHelper;
    }
}
