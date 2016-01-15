package com.zl.android.repository.common.upload;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.WorkerThread;

import javax.inject.Inject;

public class UploadService extends IntentService implements UploadTask.Callback {

    private boolean running;

    @Inject
    UploadTaskQueue queue;

    public UploadService() {
        super("Uploader");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // todo inject things here
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        executeNextTask();
    }

    @WorkerThread
    private void executeNextTask() {
        if (running) return; // one task at a time

        UploadTask task = queue.peek();
        if (task == null) { // no more tasks
            stopSelf();
            return;
        }

        running = true;
        task.execute(this);
    }

    @Override
    public void onSuccess(UploadTask task) {
        running = false;
        queue.remove();
        executeNextTask();
    }

    @Override
    public void onFailure(UploadTask task, Throwable e) {
        // todo

        // no internet?
        //      wait for internet
        // server error?
        //      skip the task?

    }
}
