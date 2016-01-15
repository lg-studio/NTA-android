package com.usinformatics.nytrip.socials.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.usinformatics.nytrip.socials.gplus.GPlusManagerActivity;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by D1m11n on 11.06.2015.
 */
public abstract class FacebookManagerActivity extends GPlusManagerActivity implements FacebookActionsListener {


    //http://stackoverflow.com/questions/17772806/facebook-requestcodes
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private FacebookEventsObserver mFacebookObserver;
    private ProfileTracker mProfileTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFacebookObserver=new FacebookEventsObserver(this,this);
    }

    public void startFacebook() {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(this.getApplicationContext());
        }
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,mFacebookObserver);
        loginFacebook();
    }

    public void stopFacebook(){

    }

    public void enableTokenTRacking(){
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,AccessToken currentAccessToken) {
                mFacebookObserver.accessTokenChanged(oldAccessToken,currentAccessToken);
            }
        };
    }

    public void enableProfileTRacking(){
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                mFacebookObserver.profileChanged(oldProfile, currentProfile);
            }
        };
    }


    private void loginFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
    }


    public AccessToken tryGetFacebookToken(){
        return AccessToken.getCurrentAccessToken();
    }

    public Profile tryGetFacebookProfile(){
        return Profile.getCurrentProfile();
    }

    public void tryGetEmail(){
        GraphRequest.newMeRequest(
                tryGetFacebookToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            mFacebookObserver.onError(response.getError());
                        } else {
                           mFacebookObserver.onGotEmail(me.optString("email"), response.getRawResponse());
//                           String email = me.optString("email");
//                            String id = me.optString("id");
                            // send email and id to your web server
                        }
                    }
                }).executeAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAccessTokenTracker !=null)
            mAccessTokenTracker.stopTracking();
        if (mProfileTracker !=null)
            mProfileTracker.stopTracking();
        mProfileTracker =null;
    }


    @Override
    protected void unhandledGplusActivityResult(int requestCode, int responseCode, Intent intent) {
        Log.e("FACEBOOK_MANAGER_ACTIVITY", "======REQUESTCODE = " + requestCode + "RESPONSECODE = " + responseCode + "intent = " + intent);
        if (mCallbackManager!=null)
            mCallbackManager.onActivityResult(requestCode, responseCode, intent);
        else
            this.unhandledFacebookActivityResult(requestCode,responseCode,intent);

    }

    protected abstract void unhandledFacebookActivityResult(int requestCode, int responseCode, Intent intent);
}


