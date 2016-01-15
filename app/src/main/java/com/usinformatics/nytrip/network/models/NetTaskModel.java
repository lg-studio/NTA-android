package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class NetTaskModel  {

    public String desc;

    public String id;

    public String name;

    public String goal;

    public String status;

    public float rating;

    public long timesStarted;

    public long timesCompleted;

    public int [] excercisesIds;

    public int [] bestThreeFriendsScores;

    public NetCharacterModel character;

    public NetLocationModel location;

    @SerializedName("chat")
    private NetQuestionModel [] questions;

    //TODO TIMESTAMP
    public long dueDate;

    private NetChatModel  mChat;

    public NetChatModel getChat(){
        if(mChat!=null)
        return mChat;
        mChat= new NetChatModel();
        mChat.questions=this.questions;
        return mChat;
    }

    public String getImageUrl(){
        return String.format(Locale.ENGLISH,Api.URI_TASK_IMAGE, id);
//        if(TextUtils.isEmpty(imageSSS))
//            return null;
//        return String.format(Locale.ENGLISH, Api.URI_IMAGE,imageSSS);
    }

    @Override
    public String toString() {
        return "NetTaskModel{" +
                "desc='" + desc + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", goal='" + goal + '\'' +
                ", status='" + status + '\'' +
                ", rating=" + rating +
                ", timesStarted=" + timesStarted +
                ", timesCompleted=" + timesCompleted +
                ", excercisesIds=" + Arrays.toString(excercisesIds) +
                ", bestThreeFriendsScores=" + Arrays.toString(bestThreeFriendsScores) +
                ", character=" + character +
                ", location=" + location +
                ", questions=" + Arrays.toString(questions) +
                ", dueDate=" + dueDate +
                ", mChat=" + mChat +
                '}';
    }

    public static String toString(NetTaskModel[] models){
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
}
