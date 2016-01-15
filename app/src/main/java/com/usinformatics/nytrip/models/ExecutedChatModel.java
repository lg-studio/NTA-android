package com.usinformatics.nytrip.models;

import com.usinformatics.nytrip.models.types.ChatType;

import java.util.List;

/**
 * Created by D1m11n on 30.07.2015.
 */
public class ExecutedChatModel {

    public long endTime;

    public String taskId;

    public String taskName;

    public String imageUrl;

    public String episodeSceneName;

    public float completedMark;

    public ChatType type;

    @Deprecated
    public List<ExecutedAnswerModel> itemResults;

    /**
     * IF type is TUTOR , mark is empty
     *
     */
    public int getCompletedMarkPercent(){
        return (int)(completedMark*100);
    }

}
