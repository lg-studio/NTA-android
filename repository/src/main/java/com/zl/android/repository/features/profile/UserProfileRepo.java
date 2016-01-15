package com.zl.android.repository.features.profile;

import com.zl.android.domain.features.catalog.model.TaskStep;
import com.zl.android.domain.features.profile.UserProfileRepository;
import com.zl.android.repository.storages.db.DbStorage;
import com.zl.android.repository.storages.web.WebStorage;

import rx.Scheduler;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class UserProfileRepo implements UserProfileRepository {

    private final DbStorage mDbStorage;
    private final WebStorage mWebStorage;

    public UserProfileRepo(DbStorage dbStorage, WebStorage webStorage) {
        mDbStorage = dbStorage;
        mWebStorage = webStorage;
    }

    @Override
    public void storeProgress(final TaskStep taskStep) {

        final Scheduler.Worker worker = Schedulers.io().createWorker();
        worker.schedule(new Action0() {
            @Override
            public void call() {
                mDbStorage.store(taskStep);
                mWebStorage.store(taskStep);
                worker.unsubscribe();
            }
        });

    }

}
