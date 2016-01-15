package com.zl.android.repository.features.catalog;

import com.zl.android.domain.features.catalog.CatalogRepository;
import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;
import com.zl.android.repository.storages.db.DbStorage;
import com.zl.android.repository.storages.memory.MemoryStorage;
import com.zl.android.repository.storages.web.WebStorage;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class CatalogRepo implements CatalogRepository {

    private final MemoryStorage mMemoryStorage;
    private final DbStorage mDbStorage;
    private final WebStorage mWebStorage;

    public CatalogRepo(MemoryStorage memoryStorage, DbStorage dbStorage, WebStorage webStorage) {
        mMemoryStorage = memoryStorage;
        mDbStorage = dbStorage;
        mWebStorage = webStorage;
    }

    @Override
    public Observable<List<Episode>> getEpisodes(final String courseId) {

        Observable<List<Episode>> memory = mMemoryStorage.getEpisodes(courseId);
        Observable<List<Episode>> disk = mDbStorage.getEpisodes(courseId)
                .doOnNext(new Action1<List<Episode>>() {
                    @Override
                    public void call(List<Episode> episodes) {
                        if (episodes != null) {
                            mMemoryStorage.saveEpisodes(courseId, episodes);
                        }
                    }
                });
        Observable<List<Episode>> web = mWebStorage.getEpisodes(courseId)
                .doOnNext(new Action1<List<Episode>>() {
                    @Override
                    public void call(List<Episode> episodes) {
                        if (episodes != null) {
                            mDbStorage.saveEpisodes(courseId, episodes);
                            mMemoryStorage.saveEpisodes(courseId, episodes);
                        }
                    }
                });

        Observable<List<Episode>> episodes = Observable
                .concat(memory, disk, web)
                .filter(new Func1<List<Episode>, Boolean>() {
                    @Override
                    public Boolean call(List<Episode> episodes) {
                        return episodes != null;
                    }
                })
                .first();

        return episodes;
    }

    @Override
    public Observable<List<Course>> getCourses(final String groupId) {

        Observable<List<Course>> memory = mMemoryStorage.getCourses(groupId);
        Observable<List<Course>> disk = mDbStorage.getCourses(groupId)
                .doOnNext(new Action1<List<Course>>() {
                    @Override
                    public void call(List<Course> courses) {
                        mMemoryStorage.saveCourses(groupId, courses);
                    }
                });
        Observable<List<Course>> web = mWebStorage.getCourses(groupId)
                .doOnNext(new Action1<List<Course>>() {
                    @Override
                    public void call(List<Course> courses) {
                        mDbStorage.saveCourses(groupId, courses);
                        mMemoryStorage.saveCourses(groupId, courses);
                    }
                });

        Observable<List<Course>> courses = Observable
                .concat(memory, disk, web)
                .filter(new Func1<List<Course>, Boolean>() {
                    @Override
                    public Boolean call(List<Course> courses) {
                        return courses != null;
                    }
                })
                .first();

        return courses;
    }

}
