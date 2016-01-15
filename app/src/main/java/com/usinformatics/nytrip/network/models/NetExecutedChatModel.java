package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.models.types.ChatType;

/**
 * Created by D1m11n on 30.07.2015.
 */
public class NetExecutedChatModel {

    public long endTime;

    @SerializedName("taskId")
    public String taskId;

    public String episodeSceneName;

    @SerializedName("result")
    public int completedMarkPercent;

    @SerializedName("_type")
    public ChatType type;

    @SerializedName("chat")
    public NetExecutedAnswerModel [] itemResults;

    /**
     * IF type is TUTOR , mark is empty
     *
     */
}
