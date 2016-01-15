package com.usinformatics.nytrip.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 11.06.2015.
 */
public class UserModel {

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    public String email;

    public String teacherCode;

    public String token;

    public String id;

    private String [] classes;

    private String password;

    public String role;

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

//    public String  getGroup(){
//        if(ListsUtils.isEmpty(groupID)) return "";
//        return groupID[0];
//    }

    public String [] getClasses(){
        if(ListsUtils.isEmpty(classes)) return new String[]{""};
        return classes;
    }

//    public String  getAllGroupsAsString(){
//        if(ListsUtils.isEmpty(groupID)) return "";
//        return Arrays.toString(groupID);
//    }

    public void  setClasses(String [] classes){
        this.classes=classes;
    }

    private StatisticModel statistic;

    public StatisticModel getStatistics(){
        if(statistic==null){
            //TODO GENERATE FAKE DATA
        }
        return null;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", teacherCode='" + teacherCode + '\'' +
                ", token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", groups=" + Arrays.toString(getClasses()) +
                '}';
    }
}
