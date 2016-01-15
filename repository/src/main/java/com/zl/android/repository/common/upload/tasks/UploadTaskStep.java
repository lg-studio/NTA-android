package com.zl.android.repository.common.upload.tasks;

import com.zl.android.domain.features.catalog.model.TaskStep;
import com.zl.android.repository.ModelMapping;
import com.zl.android.repository.features.profile.model.WebTaskStepAnswer;
import com.zl.android.repository.common.upload.UploadTask;

import java.io.File;

import retrofit.mime.TypedFile;

public class UploadTaskStep extends UploadTask<TaskStep> {


    public UploadTaskStep(TaskStep taskStep) {
        super(taskStep);
    }

    public UploadTaskStep() {
        super(null);
    }

    @Override
    protected void upload(TaskStep dataObject, Callback callback) {

        WebTaskStepAnswer answer = ModelMapping.toWebTaskStepAnswer(dataObject);

        File audioFile = new File(getContext().getFilesDir(), dataObject.audioFileName);
        TypedFile file = new TypedFile("audio/mpeg", audioFile);

        try {
            getWebService().storeTaskStepAnswer(answer, file);
            callback.onSuccess(this);

        } catch (Throwable e) {
            callback.onFailure(this, e);
        }

    }

}
