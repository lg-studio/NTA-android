package com.zl.android.domain.features.profile;

import com.zl.android.domain.features.catalog.model.TaskStep;

public interface UserProfileRepository {

    void storeProgress(TaskStep taskStep);

}
