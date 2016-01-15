package com.zl.android.repository.features.profile;

import com.zl.android.repository.features.catalog.model.WebTask;
import com.zl.android.repository.features.profile.model.WebTaskStepAnswer;

import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface UserProfileWebService {

    @Multipart
    @PUT("/todo/updateMySuperAnswer")
    WebTask storeTaskStepAnswer(@Part("answer") WebTaskStepAnswer step, @Part("audio") TypedFile file);

}
