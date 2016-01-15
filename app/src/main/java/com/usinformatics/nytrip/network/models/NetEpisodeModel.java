package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by D1m11n on 30.06.2015.
 */
public class NetEpisodeModel {

    @SerializedName("id")
    public String episodeID;

    public String name;

    public String desc;

    public NetSceneModel [] scenes;

    public String getImageUrl(){
        return String.format(Locale.ENGLISH, Api.URI_EPISODE_IMAGE, episodeID);
    }

    @Override
    public String toString() {
        return "EpisodeModel{" +
                "episodeID='" + episodeID + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", sceneIDs=" + Arrays.toString(scenes) +
                '}';
    }

    public static String toString(NetEpisodeModel[] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }
}
