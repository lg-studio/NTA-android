package com.usinformatics.nytrip.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.usinformatics.nytrip.network.Api;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class TaskModel implements Parcelable {



    public String desc;

    public String uuid;

    public String sceneId;

    public String name;

    private String image;

    public String goalDescription;

    public String status;

    public float rating;

    public int timesStarted;

    public int timesCompleted;

    public int [] excercisesIds;

    public int [] bestThreeFriendsScores;

    public CharacterModel character;

    public LocationModel location;

    //TODO TIMESTAMP
    public long dueDate;

    public String getImageUrl(){
        if(TextUtils.isEmpty(image))
            return null;
        return String.format(Locale.ENGLISH, Api.URI_IMAGE,image);
    }

    public TaskModel(){}

    public TaskModel(Parcel in){
        String [] arr=new String[7];
        in.readStringArray(arr);
        desc=arr[0];
        uuid=arr[1];
        sceneId=arr[2];
        name=arr[3];
        image=arr[4];
        goalDescription=arr[5];
        status=arr[6];
        rating=in.readFloat();
        dueDate=in.readLong();
        character=(CharacterModel)in.readParcelable(CharacterModel.class.getClassLoader());
        location=(LocationModel)in.readParcelable(LocationModel.class.getClassLoader());
    }


    @Override
    public String toString() {
        return "TaskModel{" +
                "desc='" + desc + '\'' +
                ", uuid='" + uuid + '\'' +
                ", sceneId='" + sceneId + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", goalDescription='" + goalDescription + '\'' +
                ", status='" + status + '\'' +
                ", rating=" + rating +
                ", timesStarted=" + timesStarted +
                ", timesCompleted=" + timesCompleted +
                ", excercisesIds=" + Arrays.toString(excercisesIds) +
                ", bestThreeFriendsScores=" + Arrays.toString(bestThreeFriendsScores) +
                '}';
    }

    public static String toString(TaskModel [] models){
        if (models==null) return "";
        StringBuilder bb= new StringBuilder("");
        for (int i=0; i<models.length; i++)
            bb.append(models[i].toString());
        return bb.toString();
    }

    public String getName() {
        return name==null?"---":name;
    }

    public String getDesc() {
        return desc==null?"---":desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String []{ desc, uuid,sceneId,name, image,goalDescription, status});
        dest.writeFloat(rating);
        dest.writeLong(dueDate);
        dest.writeParcelable(character,flags);
        dest.writeParcelable(location, flags);
    }

    public static final Parcelable.Creator<TaskModel> CREATOR= new Parcelable.Creator<TaskModel>() {


        @Override
        public TaskModel createFromParcel(Parcel source) {
            return new TaskModel(source);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };


    
}
