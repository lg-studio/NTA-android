package com.usinformatics.nytrip.databases.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by D1m11n on 22.06.2015.
 */

//http://stackoverflow.com/questions/17441694/one-many-relationship-in-ormlite-android
@DatabaseTable(tableName = "episode")
public class EpisodeDBModel {


    public static interface Columns {
        public String ID="id";

        public String COURSE_ID="course_id";
    }

    @DatabaseField(canBeNull = false, columnName = Columns.ID, dataType = DataType.STRING, unique = true, id = true)
    public String id;

    @DatabaseField(canBeNull = true, columnName = "name", dataType = DataType.STRING)
    public String name;

    @DatabaseField(canBeNull = true, columnName = "desc", dataType = DataType.STRING )
    public String desc;

    @DatabaseField(canBeNull = true, columnName = "scenes", dataType = DataType.STRING)
    public String scenesJsonArr;

    @DatabaseField(canBeNull = true, columnName = Columns.COURSE_ID, dataType = DataType.STRING)
    public String courseID;


}
