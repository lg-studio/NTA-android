package com.usinformatics.nytrip.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by D1m11n on 30.06.2015.
 */
public class EpisodeModel {

    @SerializedName("id")
    public String episodeID;

    public String name;

    public String desc;

    @SerializedName("scenes")
    public SceneModel [] scenes;
//
//    @SerializedName("scenes")
//    public String [] scenes;


    @Override
    public String toString() {
        return "EpisodeModel{" +
                "episodeID='" + episodeID + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", sceneIDs=" + Arrays.toString(scenes) +
                '}';
    }

    public static String toString(EpisodeModel [] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }
}
