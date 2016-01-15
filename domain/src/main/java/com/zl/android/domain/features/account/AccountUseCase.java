package com.zl.android.domain.features.account;

import com.zl.android.domain.UseCase;
import com.zl.android.domain.model.User;

import rx.Observable;

public class AccountUseCase extends UseCase {

    private AccountRepository mAccountRepository;

    public Observable<User> getCurrentUser() {
        return mAccountRepository.getCurrentUser();
    }

    public Observable<User> login() {


        return null;
    }

}
