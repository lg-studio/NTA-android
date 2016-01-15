package com.usinformatics.nytrip.storages;

import com.usinformatics.nytrip.models.EpisodeModel;
import com.usinformatics.nytrip.models.SceneModel;
import com.usinformatics.nytrip.models.UserModel;

/**
 * Created by D1m11n on 11.06.2015.
 */
public interface UserDataStorage {

    void saveUser(UserModel user);

    UserModel getUser();

    void clearUserData();

    void setCurrentCourseId(String currentSemesterId);

    String getCurrentCourseId();

    void setCurrentScene(SceneModel model);

    SceneModel getCurrentScene();

    void setCurrentEpisode(EpisodeModel model);

    EpisodeModel getCurrentEpisode();

    boolean needUpdateCurrentCourse();

    String getLastUsedEmail();

    void setCurrentTaskPath(String taskPath);

    String getCurrentTaskPath();

}
