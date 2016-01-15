package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by D1m11n on 02.07.2015.
 */
public class CourseListModel {


    public String currentCourse;

    public String id;

    public String name;

    public String desc;

    @SerializedName("semesters")
    public String [] coursesIDs;


    @Override
    public String toString() {
        return "NetworkGroupModel{" +
                "currentCourse='" + currentCourse + '\'' +
                '}';
    }

    public static String toString(CourseListModel[] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }
}
