package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by D1m11n on 30.07.2015.
 */
public class NetAudioFileIdModel {

    @SerializedName("taskId")
    public String taskId;

    @SerializedName("audioIds")
    public String [] audioIds;
}
