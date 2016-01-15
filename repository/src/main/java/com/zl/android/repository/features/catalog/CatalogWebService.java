package com.zl.android.repository.features.catalog;

import com.zl.android.repository.features.catalog.model.WebCourse;
import com.zl.android.repository.features.catalog.model.WebEpisode;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface CatalogWebService {

    @GET("/classes/{classid}/courses")
    Observable<List<WebCourse>> getCourses(@Path("classid") String groupId);

    @GET("/courses/{courseid}/episodes/all")
    Observable<List<WebEpisode>> getEpisodes(@Path("courseid") String courseId);

}
