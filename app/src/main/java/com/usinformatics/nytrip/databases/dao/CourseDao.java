package com.usinformatics.nytrip.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.usinformatics.nytrip.databases.model.CourseDBModel;

import java.sql.SQLException;

/**
 * Created by D1m11n on 22.06.2015.
 */

public class CourseDao extends BaseDaoImpl<CourseDBModel, String> {

    public CourseDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, CourseDBModel.class);
    }

    public boolean clearAll(){
        try {
            TableUtils.clearTable(super.getConnectionSource(), CourseDBModel.class);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


}
