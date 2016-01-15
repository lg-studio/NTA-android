package com.usinformatics.nytrip.databases.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.usinformatics.nytrip.databases.dao.AudioDao;

/**
 * Created by admin on 7/22/15.
 */

@DatabaseTable(tableName = "audio", daoClass = AudioDao.class)
public class AudioModel {

    @DatabaseField(canBeNull = false, columnName = "id", dataType = DataType.STRING, unique = true, id = true)
    private String id;

    @DatabaseField(canBeNull = false, columnName = "file_path" ,dataType = DataType.STRING  )
    private String filePath;

    @DatabaseField(canBeNull = false, columnName = "audio_id", dataType = DataType.STRING )
    private String audioId;

    public String getAudioId() {
        return audioId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }
}
