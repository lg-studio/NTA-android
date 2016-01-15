package com.usinformatics.nytrip.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.usinformatics.nytrip.databases.dao.CourseDao;
import com.usinformatics.nytrip.databases.dao.EpisodeDao;
import com.usinformatics.nytrip.databases.dao.SceneDao;
import com.usinformatics.nytrip.databases.dao.TaskDao;
import com.usinformatics.nytrip.databases.model.AudioModel;
import com.usinformatics.nytrip.databases.model.CourseDBModel;

import java.sql.SQLException;

/**
 * Created by D1m11n on 22.06.2015.
 */
//https://github.com/j256/ormlite-core/issues/26
class EduMaterialDbHelper extends OrmLiteSqliteOpenHelper {


    private static final String TAG = "DATABASE_HELPER";

    private static final String DB_NAME = "edu";
    private static final int DB_VER = 3;


    private EpisodeDao mEpisodeDao;
    private SceneDao mSceneDao;
    private CourseDao mCourseDao;
    private TaskDao mTaskDao;



    public EduMaterialDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            createTables(connectionSource);
            initSemesterDao(connectionSource);
//            initEpisodeDao(connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate; sqlException e= " + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            dropTables(connectionSource);
            createTables(connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "onUpgrade; error upgrading db " + DB_NAME + "from ver " + oldVersion + ", e= " + e.toString());
            throw new RuntimeException(e);
        }
    }

    private void dropTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.dropTable(connectionSource, CourseDBModel.class, true);
        TableUtils.dropTable(connectionSource, AudioModel.class, true);
//        TableUtils.dropTable(connectionSource, EpisodeDBModel.class, true);
//        TableUtils.dropTable(connectionSource, SceneDbModel.class, true);
//        TableUtils.dropTable(connectionSource, TaskDbModel.class, true);

    }

    private void createTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.createTable(connectionSource, CourseDBModel.class);
        TableUtils.clearTable(connectionSource, AudioModel.class);
//        TableUtils.createTable(connectionSource, EpisodeDBModel.class);
//        TableUtils.createTable(connectionSource, SceneDbModel.class);
//        TableUtils.createTable(connectionSource, TaskDbModel.class);

    }

    boolean clearSemesterTable(){
        try {
            TableUtils.clearTable(connectionSource, CourseDBModel.class);
            return true;
        } catch (SQLException e) {
            Log.e(TAG,"clear table error = " +e.toString());
            return false;
        }
    }

    CourseDao getSemesterDao(){
        if(mCourseDao ==null)
            initSemesterDao(connectionSource);
        return mCourseDao;
    }

    EpisodeDao getEpisodeDao(){
        if(mEpisodeDao==null)
            initEpisodeDao(connectionSource);
        return  mEpisodeDao;
    }

    private void initEpisodeDao(ConnectionSource connectionSource) {
        try {
            mEpisodeDao = new EpisodeDao(connectionSource);
        }catch (SQLException e){
            Log.e(TAG,"initSemesterDao = " +e.toString());
        }
    }

    private void initSemesterDao(ConnectionSource srs) {
        try {
            mCourseDao = new CourseDao(srs);
        }catch (SQLException e){
            Log.e(TAG,"initSemesterDao = " +e.toString());
        }
    }
}
