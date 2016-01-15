package com.usinformatics.nytrip.socials.facebook;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

/**
 * Created by D1m11n on 12.06.2015.
 */
class FacebookEventsObserver implements FacebookCallback<LoginResult> {

    private final Context context;
    private FacebookActionsListener mListener;

    FacebookEventsObserver(Context context, FacebookActionsListener listener){
        this.context=context;
        mListener=listener;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        if (mListener!=null)
           mListener.onFacebookActionsChange(FacebookState.LOGIN_SUCCESS, loginResult);
    }

    @Override
    public void onCancel() {
        if (mListener!=null)
            mListener.onFacebookActionsChange(FacebookState.LOGIN_CANCEL);
    }

    @Override
    public void onError(FacebookException e) {
        if (mListener!=null)
            mListener.onFacebookActionsChange(FacebookState.LOGIN_ERROR);
    }

    public void onError(FacebookRequestError error) {
        if (mListener!=null)
            mListener.onFacebookActionsChange(FacebookState.EMAIL_ERROR, error);
    }

    public void onGotEmail(String email, String response) {
        if (mListener!=null)
            mListener.onFacebookActionsChange(FacebookState.GOT_EMAIL, email, response);
    }

     void accessTokenChanged(AccessToken oldAccessToken,AccessToken currentAccessToken) {
         if (mListener!=null){
             mListener.onFacebookActionsChange(FacebookState.TOKEN_CHANGED, oldAccessToken, currentAccessToken);
         }
     }

    void profileChanged(Profile oldProfile, Profile currentProfile) {
       if (mListener!=null)
           mListener.onFacebookActionsChange(FacebookState.PROFILE_CHANGED, oldProfile, currentProfile);
    }


}
