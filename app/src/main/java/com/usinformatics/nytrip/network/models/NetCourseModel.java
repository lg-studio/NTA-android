package com.usinformatics.nytrip.network.models;

import java.util.Arrays;

/**
 * Created by D1m11n on 24.06.2015.
 */
public class NetCourseModel {


    public String id;

    public String name;

    public String desc;

    public String getName(){
        return name;
    }

    public String [] episodeIDs;


    @Override
    public String toString() {
        return "CourseModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", episodeIDs=" + Arrays.toString(episodeIDs) +
                '}';
    }

    public static String toString(NetCourseModel[] model){
        if(model==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<model.length; i++) {
            bb.append(model[i].toString());
        }
        return bb.toString();
    }

}
