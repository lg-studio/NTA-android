package com.usinformatics.nytrip.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.usinformatics.nytrip.databases.model.EpisodeDBModel;
import com.usinformatics.nytrip.databases.model.TaskDbModel;

import java.sql.SQLException;

/**
 * Created by D1m11n on 22.06.2015.
 */

public class TaskDao extends BaseDaoImpl<TaskDbModel, String> {

    public TaskDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TaskDbModel.class);
    }


}
