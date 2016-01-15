package com.zl.android.repository.storages.memory;

import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;

import java.util.List;

import rx.Observable;

public class MemoryStorage {

    public Observable<List<Episode>> getEpisodes(String courseId) {
        return Observable.empty();
    }

    public void saveEpisodes(String courseId, List<Episode> episodes) {

    }

    public Observable<List<Course>> getCourses(String groupId) {
        return Observable.empty();
    }

    public void saveCourses(String groupId, List<Course> courses) {

    }
}
