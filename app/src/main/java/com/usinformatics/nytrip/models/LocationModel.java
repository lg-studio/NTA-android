package com.usinformatics.nytrip.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Locale;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class LocationModel implements Parcelable {


    private double lon;

    private double lat;
    
    @SerializedName("image")
    private String imgUrl;

    public LocationModel(){}

    public LocationModel(Parcel in){
        lon=in.readLong();
        lat=in.readLong();
        imgUrl=in.readString();
    }


    public double getLatitude(){
        return lat;
    }

    public double getLongitude(){
        return lon;
    }

    public String getImageUrl(){
        if(imgUrl==null)
            return null;
        return String.format(Locale.ENGLISH, Api.URI_IMAGE,imgUrl);
    }


    public static final Parcelable.Creator<LocationModel> CREATOR= new Parcelable.Creator<LocationModel>() {


        @Override
        public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeString(imgUrl);
    }
}
