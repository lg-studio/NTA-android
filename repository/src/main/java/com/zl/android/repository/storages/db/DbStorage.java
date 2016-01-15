package com.zl.android.repository.storages.db;

import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;
import com.zl.android.domain.features.catalog.model.TaskStep;

import java.util.List;

import rx.Observable;

public class DbStorage {

    public void store(TaskStep taskStep) {
        // todo
    }

    public void saveCourses(String groupId, List<Course> courses) {

    }

    public Observable<List<Course>> getCourses(String groupId) {
        return Observable.empty();
    }

    public void saveEpisodes(String courseId, List<Episode> episodes) {

    }

    public Observable<List<Episode>> getEpisodes(String courseId) {
        return Observable.empty();
    }
}
