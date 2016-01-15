package com.usinformatics.nytrip.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by D1m11n on 30.06.2015.
 */
public class SceneModel {


    @SerializedName("id")
    public String sceneID;

    public String name;

    public String desc;

    @SerializedName("tasks")
    public String [] taskIDs;


    @Override
    public String toString() {
        return "SceneModel{" +
                "sceneID='" + sceneID + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", taskIDs=" + Arrays.toString(taskIDs) +
                '}';
    }

    public static String toString(SceneModel [] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }

}
