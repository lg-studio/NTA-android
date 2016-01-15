package com.zl.android.repository.storages.web;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;
import com.zl.android.domain.features.catalog.model.TaskStep;
import com.zl.android.repository.ModelMapping;
import com.zl.android.repository.features.catalog.CatalogWebService;
import com.zl.android.repository.features.catalog.model.WebCourse;
import com.zl.android.repository.features.catalog.model.WebEpisode;
import com.zl.android.repository.common.upload.UploadService;
import com.zl.android.repository.common.upload.UploadTaskQueue;
import com.zl.android.repository.common.upload.tasks.UploadTaskStep;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class WebStorage {

    private final CatalogWebService mCatalogWebService;
    private final UploadTaskQueue mUploadTaskQueue;
    private final Context mContext;

    public WebStorage(CatalogWebService catalogWebService, UploadTaskQueue uploadTaskQueue, Context context) {
        mCatalogWebService = catalogWebService;
        mUploadTaskQueue = uploadTaskQueue;
        mContext = context;

        context.registerReceiver(mConnectionStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    // region -- Update progress methods

    public void store(TaskStep taskStep) {
        mUploadTaskQueue.add(new UploadTaskStep(taskStep));
        uploadTask();
    }

    private void uploadTask() {
        // todo check if online
        mContext.startService(new Intent(mContext, UploadService.class));
    }

    // endregion

    // region -- Catalog methods

    public Observable<List<Episode>> getEpisodes(String courseId) {
        return mCatalogWebService.getEpisodes(courseId)
                .map(new Func1<List<WebEpisode>, List<Episode>>() {
                    @Override
                    public List<Episode> call(List<WebEpisode> webEpisodes) {
                        return ModelMapping.toEpisodes(webEpisodes);
                    }
                });
    }

    public Observable<List<Course>> getCourses(String groupId) {
        return mCatalogWebService.getCourses(groupId)
                .map(new Func1<List<WebCourse>, List<Course>>() {
                    @Override
                    public List<Course> call(List<WebCourse> webCourse) {
                        return ModelMapping.toCourse(webCourse);
                    }
                });
    }

    // endregion

    // connection state listener
    private BroadcastReceiver mConnectionStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mUploadTaskQueue.size() == 0) return;

            ConnectivityManager cm = (ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            boolean connected = cm.getActiveNetworkInfo().isConnectedOrConnecting();
            if (connected) {
                uploadTask();
            }
        }
    };

}
