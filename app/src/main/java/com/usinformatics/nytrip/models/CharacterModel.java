package com.usinformatics.nytrip.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.usinformatics.nytrip.network.Api;

import java.util.Locale;

/**
 * Created by D1m11n on 10.07.2015.
 */
public class CharacterModel implements Parcelable  {

    public String name;

    public String desc;

    public String imgUrl;

    public LatLng latLng;

    public CharacterModel(Parcel source) {
        name=source.readString();
        desc=source.readString();
        imgUrl=source.readString();
        latLng=source.readParcelable(LatLng.class.getClassLoader());
    }

    public CharacterModel(){}

    public String getImageUrl(){
        if(imgUrl==null)
            return null;
        return String.format(Locale.ENGLISH, Api.URI_IMAGE,imgUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(imgUrl);
        dest.writeParcelable(latLng, flags);
    }


    public static final Parcelable.Creator<CharacterModel> CREATOR= new Parcelable.Creator<CharacterModel>() {


        @Override
        public CharacterModel createFromParcel(Parcel source) {
            return new CharacterModel(source);
        }

        @Override
        public CharacterModel[] newArray(int size) {
            return new CharacterModel[size];
        }
    };
}
