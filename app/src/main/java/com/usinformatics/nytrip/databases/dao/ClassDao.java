package com.usinformatics.nytrip.databases.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.usinformatics.nytrip.databases.model.ClassDBModel;

import java.sql.SQLException;

/**
 * Created by D1m11n on 22.07.2015.
 */
public class ClassDao extends BaseDaoImpl<ClassDBModel, String> {

    public ClassDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ClassDBModel.class);
    }

    public boolean clearAll() {
        try {
            TableUtils.clearTable(super.getConnectionSource(), ClassDBModel.class);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}