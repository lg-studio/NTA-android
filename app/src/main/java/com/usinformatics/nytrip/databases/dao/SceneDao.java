package com.usinformatics.nytrip.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.usinformatics.nytrip.databases.model.SceneDbModel;

import java.sql.SQLException;

/**
 * Created by D1m11n on 22.06.2015.
 */
public class SceneDao extends BaseDaoImpl<SceneDbModel, Long> {

    public SceneDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SceneDbModel.class);
    }


}
