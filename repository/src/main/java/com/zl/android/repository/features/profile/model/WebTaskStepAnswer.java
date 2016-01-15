package com.zl.android.repository.features.profile.model;

import com.google.gson.annotations.SerializedName;

public class WebTaskStepAnswer {

    @SerializedName("task_id")
    public String taskId;

    @SerializedName("index")
    public int taskStepIndex;

    @SerializedName("selected_text")
    public String answerText;

}
