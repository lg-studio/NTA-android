package com.zl.android.domain.features.account;

import com.zl.android.domain.model.User;

import rx.Observable;

public interface AccountRepository {

    Observable<User> getCurrentUser();

}
