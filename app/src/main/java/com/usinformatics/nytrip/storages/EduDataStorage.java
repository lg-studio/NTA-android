package com.usinformatics.nytrip.storages;

import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.TaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D1m11n on 22.06.2015.
 */
public interface EduDataStorage {

    public boolean saveCourse(CourseModel model);

    public boolean saveCourses(CourseModel [] models);

    public boolean saveEpisodes(EpisodeModel [] models);

    public boolean saveEpisode(EpisodeModel model);

    public ArrayList<CourseModel> getCourses();

    public void clearAllCourses();

    public void saveTasks(List<TaskModel> tasks);

    public List<TaskModel> getTasksBy(String sceneId);

    public TaskModel getTask(String taskId);

    public void saveEpisodes(List<EpisodeModel> episodes);

    public List<EpisodeModel> getEpisodesBy(String courseId);

    public EpisodeModel getEpisode(String episodeId);

    public void saveTask(TaskModel task);

    public boolean removeTasks(String[] taskIDs);

    public boolean removeTask(String taskID);

    public List<String> removeEpisode(String episodeID);

    public boolean removeScene(String sceneID);

    public boolean removeTaskBy(String sceneId);

    public boolean removeTasksBy(String[] sceneId);

    boolean removeTasksBy(List<String> scenesId);

    boolean removeTaskFromScene(String sceneId, String taskId);

    boolean addTaskIdToScene(String taskId, String sceneId);

    boolean removeEpisodesOf(String courseId);
}
