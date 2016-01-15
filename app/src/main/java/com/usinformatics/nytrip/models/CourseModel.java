package com.usinformatics.nytrip.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by D1m11n on 24.06.2015.
 */
public class CourseModel {


    public String id;

    private String name;

    public String desc;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    @SerializedName("current")
    public boolean isCurrent;

    @SerializedName("episodes")
    public String [] episodeIDs;


    @Override
    public String toString() {
        return "CourseModel{" +
                "uuid='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", isCurrent=" + isCurrent +
                ", episodeIDs=" + Arrays.toString(episodeIDs) +
                '}';
    }

    public static String toString(CourseModel [] model){
        if(model==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<model.length; i++) {
            bb.append(model[i].toString());
        }
        return bb.toString();
    }

}
