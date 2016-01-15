package com.usinformatics.nytrip.storages;

import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.models.EpisodeModel;

import java.util.ArrayList;

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

}
