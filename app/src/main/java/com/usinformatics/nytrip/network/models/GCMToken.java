package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 7/14/15.
 */
public class GCMToken {

    public GCMToken(String token) {
        this.token = token;
    }

    @SerializedName("registrationId")
    public String token;

}
