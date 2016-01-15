package com.usinformatics.nytrip.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.usinformatics.nytrip.network.Api;

import java.util.Locale;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class LocationModel /*implements Parcelable*/ {


//    private double lon;
//
//    private double lat;
//
    public LatLng latLng;

    public String imgUrl;

    public LocationModel(){}

    public LocationModel(Parcel in){
        latLng=in.readParcelable(LatLng.class.getClassLoader());
        imgUrl=in.readString();
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

  /*  @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(latLng, flags);
        dest.writeString(imgUrl);
    }*/
}
