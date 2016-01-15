package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by D1m11n on 30.06.2015.
 */
public class NetSceneModel {

    @SerializedName("id")
    public String sceneID;

    public String name;

    public String desc;

    @SerializedName("tasks")
    public String [] taskIDs;

    private NetImageModel image;

    public String getImage(){
        //if (TextUtils.isEmpty(image)) return null;
        if (image==null) return null;
        return String.format(Locale.ENGLISH, Api.URI_SCENE_IMAGE,sceneID);
    }

    @Override
    public String toString() {
        return "SceneModel{" +
                "sceneID='" + sceneID + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", taskIDs=" + Arrays.toString(taskIDs) +
                '}';
    }

    public static String toString(NetSceneModel[] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }

}
