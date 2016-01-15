package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Arrays;
import java.util.Locale;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 11.06.2015.
 */
public class NetUserModel {

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    public String email;

    public String token;

    public String id;

    private String [] classes;


    public String role;

    public int code;

    private String password;

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

    @Override
    public String toString() {
        return "UserModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", groups=" + Arrays.toString(getClasses()) +
                '}';
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getImageUrl() {
        return String.format(Locale.ENGLISH, Api.URI_USER_IMAGE,id);
    }
}
