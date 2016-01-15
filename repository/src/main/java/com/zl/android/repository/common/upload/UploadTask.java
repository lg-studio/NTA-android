package com.zl.android.repository.common.upload;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.squareup.tape.Task;
import com.zl.android.repository.features.profile.UserProfileWebService;

public abstract class UploadTask<T> implements Task<UploadTask.Callback> {

    public interface Callback {
        void onSuccess(UploadTask task);
        void onFailure(UploadTask task, Throwable e);
    }

    public T dataObject;

    // todo inject fields
    UserProfileWebService mWebService;
    Context mContext;

    public UploadTask(T dataObject) {
        this.dataObject = dataObject;
    }

    @WorkerThread
    @Override
    public void execute(Callback callback) {
        upload(dataObject, callback);
    }

    protected UserProfileWebService getWebService() {
        return mWebService;
    }

    protected Context getContext() {
        return mContext;
    }

    protected abstract void upload(T dataObject, Callback callback);

}
