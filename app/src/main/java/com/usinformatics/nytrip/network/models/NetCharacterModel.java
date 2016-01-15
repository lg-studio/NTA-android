package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Locale;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class NetCharacterModel  {

    public String name;

    public String desc;

    @SerializedName("image")
    private String imgUrl;

    public double lat;

    public double lon;


    public NetCharacterModel(){}

    public String getImageUrl(){
        if(imgUrl==null)
            return null;
        return String.format(Locale.ENGLISH, Api.URI_IMAGE,imgUrl);
    }

}
