package com.usinformatics.nytrip.models;

/**
 * Created by admin on 7/15/15.
 */
public class UserActivityModel {

    private String taskPath;
    private String taskName;
    private String taskTime;
    private String taskRating;
    private String taskDate;


    public String getTaskPath() {
        return taskPath;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskRating() {
        return taskRating;
    }

    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskRating(String taskRating) {
        this.taskRating = taskRating;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }
}
