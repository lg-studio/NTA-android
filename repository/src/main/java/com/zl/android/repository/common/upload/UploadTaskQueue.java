package com.zl.android.repository.common.upload;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.tape.FileObjectQueue;
import com.squareup.tape.ObjectQueue;
import com.squareup.tape.TaskInjector;
import com.squareup.tape.TaskQueue;

import java.io.File;
import java.io.IOException;

public class UploadTaskQueue extends TaskQueue<UploadTask> {

    private static final String FILENAME = "uploads";
    private final Context mContext;

    private UploadTaskQueue(Context context, ObjectQueue<UploadTask> delegate, TaskInjector<UploadTask> injector) {
        super(delegate, injector);
        mContext = context;
    }

    public static UploadTaskQueue create(Context context, Gson gson) {
        File queueFile = new File(context.getFilesDir(), FILENAME);
        FileObjectQueue.Converter<UploadTask> converter = new GsonTaskSerializer<>(gson, UploadTask.class);
        FileObjectQueue<UploadTask> delegate;
        try {
            delegate = new FileObjectQueue<UploadTask>(queueFile, converter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadTaskQueue(context, delegate, null);
    }

}
