package com.usinformatics.nytrip.databases.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by D1m11n on 22.07.2015.
 */
@DatabaseTable(tableName = "class")
public class ClassDBModel {

    public String currentCourse;

    @DatabaseField(canBeNull = false, columnName = "id", dataType = DataType.STRING, unique = true, id = true)
    public String id;

    @DatabaseField(canBeNull = true, columnName = "name", dataType = DataType.STRING )
    public String name;

    @DatabaseField(canBeNull = true, columnName = "desc", dataType = DataType.STRING )
    public String desc;

    @DatabaseField(canBeNull = true, columnName = "courses", dataType = DataType.STRING )
    public String  courseIDs;
}
