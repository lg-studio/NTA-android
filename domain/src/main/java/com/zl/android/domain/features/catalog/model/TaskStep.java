package com.zl.android.domain.features.catalog.model;

import com.zl.android.domain.features.profile.model.TaskStepProgress;

public class TaskStep {

    public String taskId;
    public int index; // position in the task
    public int actor; // npc, teacher

    public String text;
    public String audioFileName;

    public TaskStepProgress progress;

}
