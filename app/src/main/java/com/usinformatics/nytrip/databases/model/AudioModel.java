package com.usinformatics.nytrip.databases.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.usinformatics.nytrip.databases.dao.AudioDao;

/**
 * Created by admin on 7/22/15.
 */

@DatabaseTable(tableName = "audio_file", daoClass = AudioDao.class)
public class AudioModel {

    @DatabaseField(canBeNull = false, columnName = "id", unique = true,  generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, columnName = "file_path", dataType = DataType.STRING)
    private String filePath;

    @DatabaseField(canBeNull = false, columnName = "episode", dataType = DataType.STRING)
    private String episode;

    @DatabaseField(canBeNull = false, columnName = "scene", dataType = DataType.STRING)
    private String scene;

    @DatabaseField(canBeNull = false, columnName = "task", dataType = DataType.STRING)
    private String task;

    @DatabaseField(canBeNull = false, columnName = "question", dataType = DataType.STRING)
    private String question;

    @DatabaseField(canBeNull = false, columnName = "audio_response_id", dataType = DataType.STRING)
    private String audioResponseId;


    public String getAudioId() {
        return audioResponseId;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getId() {
        return id;
    }

    public String getEpisode() {
        return episode;
    }

    public String getScene() {
        return scene;
    }

    public String getTask() {
        return task;
    }

    public String getQuestion() {
        return question;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setAudioId(String audioId) {
        this.audioResponseId = audioId;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public interface Column {

        public String filePath = "file_path";
        public String episode = "episode";
        public String scene = "scene";
        public String task = "task";
        public String question = "question";
        public String audioId = "audio_response_id";
    }
}
