package com.usinformatics.nytrip.ui.notification;

/**
 * Created by admin on 7/27/15.
 */
public class FeedbackModel  {

    private String taskPath;
    private String taskName;
    private int score;
    private String finishedTime;

    public String getTaskPath() {
        return taskPath;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getScore() {
        return score;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }
}
