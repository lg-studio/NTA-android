package com.zl.android.domain.features.catalog;

import com.zl.android.domain.UseCase;
import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;

import java.util.List;

import rx.Observable;

public class CatalogUseCase extends UseCase {

    private final CatalogRepository mCatalogRepository;

    public CatalogUseCase(CatalogRepository catalogRepository) {
        mCatalogRepository = catalogRepository;
    }

    public Observable<List<Episode>> getEpisodes(String courseId) {
        return mCatalogRepository.getEpisodes(courseId);
    }

    public Observable<List<Course>> getCourses(String groupId) {
        return mCatalogRepository.getCourses(groupId);
    }

}
