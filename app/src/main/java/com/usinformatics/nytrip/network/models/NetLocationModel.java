package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Locale;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class NetLocationModel {


    public  double lon;

    public double lat;

    @SerializedName("image")
    private String imgUrl; //TODO UPDATE WITH

    public NetLocationModel(){}

    public String getImageUrl(){
        if(imgUrl==null)
            return null;
        return String.format(Locale.ENGLISH, Api.URI_LOCATION_IMAGE,imgUrl);
    }
}
