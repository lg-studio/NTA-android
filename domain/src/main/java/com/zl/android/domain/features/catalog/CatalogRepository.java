package com.zl.android.domain.features.catalog;

import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;

import java.util.List;

import rx.Observable;

public interface CatalogRepository {

    Observable<List<Episode>> getEpisodes(String courseId);

    Observable<List<Course>> getCourses(String groupId);

}
