package com.zl.android.domain.features.profile;

import com.zl.android.domain.UseCase;
import com.zl.android.domain.features.catalog.model.TaskStep;

public class UserProfileUseCase extends UseCase {

    private final UserProfileRepository mUserProfileRepository;

    public UserProfileUseCase(UserProfileRepository userProfileRepository) {
        mUserProfileRepository = userProfileRepository;
    }

    public void storeUserAnswer(TaskStep taskStep) {
        if (taskStep.progress == null) {
            throw new IllegalArgumentException("progress must not be null");
        }
        mUserProfileRepository.storeProgress(taskStep);
    }

}
