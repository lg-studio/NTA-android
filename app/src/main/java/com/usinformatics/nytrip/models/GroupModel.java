package com.usinformatics.nytrip.models;

/**
 * Created by D1m11n on 02.07.2015.
 *
 * Use class model instead of this
 */
@Deprecated()
public class GroupModel {


    public String currentCourse;

    public String id;

    public String name;

    public String desc;

    public String [] coursesIDs;


    @Override
    public String toString() {
        return "NetGroupModel{" +
                "currentCourse='" + currentCourse + '\'' +
                '}';
    }

    public static String toString(GroupModel[] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }
}
