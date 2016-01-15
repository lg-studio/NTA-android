package com.usinformatics.nytrip.databases.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;

/**
 * Created by D1m11n on 22.06.2015.
 */

//http://stackoverflow.com/questions/17441694/one-many-relationship-in-ormlite-android

@DatabaseTable(tableName = "course")
public class CourseDBModel {

    @DatabaseField(canBeNull = false, columnName = "id", dataType = DataType.STRING, unique = true, id = true)
    private String uuid;

    @DatabaseField(canBeNull = false, columnName = "name" ,dataType = DataType.STRING  )
    private String name;

    @DatabaseField(canBeNull = false, columnName = "desc", dataType = DataType.STRING )
    private String desc;

    @DatabaseField(canBeNull = true , columnName = "array", dataType = DataType.STRING)
    private String episodesArray;

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String [] getEpisodesArray() {
        return episodesArray.split(",");
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEpisodesArray(String [] episodesArray) {
        this.episodesArray = Arrays.toString(episodesArray);
    }

    public String getEpisodesArrayAsString(){
        return this.episodesArray;
    }

    public CourseDBModel(){}
}
