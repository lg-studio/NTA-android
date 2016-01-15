package com.usinformatics.nytrip.models;

import com.usinformatics.nytrip.models.types.ChatType;
import com.usinformatics.nytrip.network.Api;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class TaskModel/* implements Parcelable*/ {



    public String desc;

    public String id;

    public String sceneId;

    public String name;

    public String imageUrl;

    public String getImageUrl(){
        return String.format(Locale.ENGLISH, Api.URI_TASK_IMAGE, id);
    }

    public String goal;

    public String status;

    public float rating;

    public long timesStarted;

    public long timesCompleted;

    public int [] excercisesIds;

    public int [] bestThreeFriendsScores;

    public CharacterModel character;

    public LocationModel location;

    //TODO TIMESTAMP
    public long dueDate;

    public ChatType chatType = ChatType.USER_CHOICE;

    private ChatModel  mChat;

    public ChatModel getChat(){
        return mChat;
    }

    public void setChat(ChatModel chat){
        mChat=chat;
    }

    public TaskModel(){}

    @Override
    public String toString() {
        return "TaskModel{" +
                "desc='" + desc + '\'' +
                ", id='" + id + '\'' +
                ", sceneId='" + sceneId + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", goal='" + goal + '\'' +
                ", status='" + status + '\'' +
                ", rating=" + rating +
                ", timesStarted=" + timesStarted +
                ", timesCompleted=" + timesCompleted +
                ", excercisesIds=" + Arrays.toString(excercisesIds) +
                ", bestThreeFriendsScores=" + Arrays.toString(bestThreeFriendsScores) +
                ", character=" + character +
                ", location=" + location +
                ", questions=" + Arrays.toString(mChat.questions) +
                ", dueDate=" + dueDate +
                ", chatType=" + chatType +
                ", mChat=" + mChat +
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

}
