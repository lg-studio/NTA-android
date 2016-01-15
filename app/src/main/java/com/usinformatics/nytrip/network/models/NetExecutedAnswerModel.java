package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by D1m11n on 16.07.2015.
 */

//TODO REFACTOR NAME
public class NetExecutedAnswerModel {



    @SerializedName("result")
    public int markInPrecent;

    @SerializedName("answer")
    public String selectedAnswer;

    @SerializedName("text")
    public String selectedQuestion;

    @SerializedName("tries")
    public int triesCount;
}
