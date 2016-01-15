package com.usinformatics.nytrip.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.usinformatics.nytrip.databases.model.EpisodeDBModel;

import java.sql.SQLException;

/**
 * Created by D1m11n on 22.06.2015.
 */

public class EpisodeDao extends BaseDaoImpl<EpisodeDBModel, String> {

    public EpisodeDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, EpisodeDBModel.class);
    }


}
