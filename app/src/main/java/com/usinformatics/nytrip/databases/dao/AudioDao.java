package com.usinformatics.nytrip.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.usinformatics.nytrip.databases.model.AudioModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 7/22/15.
 */
public class AudioDao extends BaseDaoImpl<AudioModel, String> {

    private Dao<AudioModel, String> audioDao;


    public AudioDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, AudioModel.class);
        audioDao = DaoManager.createDao(connectionSource, AudioModel.class);
    }


    public void updateAudio(AudioModel audioModel) throws SQLException {
        audioDao.update(audioModel);
    }

    public void createAudio(AudioModel audioModel) throws SQLException {
        audioDao.create(audioModel);
    }

    public void deleteAudio(AudioModel audioModel) throws SQLException {
        audioDao.delete(audioModel);
    }

    public AudioModel getAudio(String audioPath) throws SQLException {
        QueryBuilder<AudioModel, String> qb = audioDao.queryBuilder();
        return qb.where().eq("file_path", audioPath).queryForFirst();
    }

    public boolean audioIsExist(AudioModel audio) throws SQLException {
        QueryBuilder<AudioModel, String> qb = audioDao.queryBuilder();
        AudioModel audioModel = qb.where().eq(AudioModel.Column.audioId, audio.getAudioId()).queryForFirst();
               if(audioModel == null){
                   return false;
               }
        return true;
    }

    public void deleteAudioById(String audioId) throws SQLException {
        QueryBuilder<AudioModel, String> qb = audioDao.queryBuilder();
        List<AudioModel> audioList = new ArrayList<>();
        audioList = qb.where().eq(AudioModel.Column.audioId, audioId).query();
        for (AudioModel audio :
                audioList) {
            deleteAudio(audio);
        }
    }
}
