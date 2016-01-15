package com.usinformatics.nytrip.ui.account;

import com.usinformatics.nytrip.ui.account.model.AccountType;
import com.usinformatics.nytrip.models.UserModel;

/**
 * Created by D1m11n on 11.06.2015.
 */
public interface IAccountView {


    void showProgress(AccountType type, String progressMessage);
    void hideProgress();
    void showDialog(String title,String message);
    void setUsernameError(String error);
    void setPasswordError(String error);
    void setNetworkError(String error);
    void navigateToMain();
    void showAccountData(UserModel user);
    void setTitle(String title);
    void finishActivity();


    
    
}
