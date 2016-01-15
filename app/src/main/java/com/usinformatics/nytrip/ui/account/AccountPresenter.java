package com.usinformatics.nytrip.ui.account;

import android.content.Intent;
import android.util.Log;

import com.facebook.FacebookRequestError;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.usinformatics.nytrip.NyTripApplication;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.network.NetworkErrorHelper;
import com.usinformatics.nytrip.network.OnServerResponseCallback;
import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.network.gcm.InitGCMService;
import com.usinformatics.nytrip.socials.facebook.FacebookState;
import com.usinformatics.nytrip.socials.gplus.listeners.GPLusActionsListener;
import com.usinformatics.nytrip.socials.gplus.models.GPlusState;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.storages.UserDataStorage;
import com.usinformatics.nytrip.ui.account.activities.BaseAccountActivity;
import com.usinformatics.nytrip.ui.account.model.AccountType;

import java.util.Arrays;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 11.06.2015.
 */
public class AccountPresenter implements GPLusActionsListener{

    private static final String TAG="ACCOUNT_PRESENTER";

    private IAccountView mView;
    private BaseAccountActivity mActivity;
    private UserDataStorage mUserStorage;

    public AccountPresenter(BaseAccountActivity activity, IAccountView view) {
        mActivity = activity;
        mView = view;
        mActivity.stopGplusApiClient();
        mActivity.stopFacebook();
        mUserStorage = StorageFactory.getUserStorage(activity);
    }

    public void startRegister (AccountType type,final UserModel user, String password){
        if(user==null) return;
        user.setPassword(password);
        startActionAccount(user);
    }

    public void startLogin (AccountType type,UserModel user, String password){
        if(user==null) return;
        user.setPassword(password);
        switch(type){
            case DEFAULT:
                startDefaultLogin(user); return;
            case FACEBOOK:
                startFacebookLogin(user); return;
            case GPLUS:
                startGPlusLogin(user); return;
        }
    }

    private void startGPlusLogin(UserModel user) {
        mActivity.startGplusApiClient();
    }

    private void startFacebookLogin(UserModel user) {
       mActivity.startFacebook();
    }

    private void startDefaultLogin(UserModel user) {
       startActionAccount(user);
    }

    private void startActionAccount(final UserModel user){
        RequestExecutor.getInstance(mActivity).account(user, new OnServerResponseCallback<UserModel>() {
            @Override
            public void onResponse(UserModel objects, Response responseBody, RetrofitError error) {
                Log.e(TAG, "resp = " + objects + ", error " + error);
                if (NetworkErrorHelper.showNetworkErrorDialogIfNeeded(mActivity, error)) {
                    return;
                }
                Log.e(TAG, "user = " + user.toString());
                Log.e(TAG, " network user  = " + objects.toString());
               //ModelBridge.updateModel(user, objects);
                objects.setPassword(user.getPassword());
                mUserStorage.saveUser(objects);

                String token = mUserStorage.getUser().token;
                Log.e(TAG, " access token after   = " + token);

                NyTripApplication.getScope(mActivity.getApplication())
                        .webServiceLocator()
                        .setAccessToken(token);

                initGCM();
                mView.navigateToMain();
                mView.finishActivity();
            }
        });
    }

    private void initGCM() {
        Intent intent = new Intent(mActivity, InitGCMService.class);
        intent.putExtra(mActivity.getResources().getString(R.string.user_token), mUserStorage.getUser().token);
        mActivity.startService(intent);
    }

    @Override
    public void onGplusClientActionsChange(GPlusState states, Object... data) {
        switch (states){
            case CONNECTED:
                Log.e("EMAIL","EMAIL" + "GPLUS CONNECTED+++++++++  ");
                 mActivity.getGplusToken();
                break;
            case GOT_TOKEN:
                mView.showDialog("Token"," " +  String.valueOf(data[0]));
                mView.setUsernameError("ME " + mActivity.getGplusMe());
                break;
            case EXCEPTION:
                mView.showDialog("EXCEPTION", String.valueOf(data[0]));
        }
        Log.e("ACCOUNT_PRESENTER", " states = " + states.toString() + ", data " + Arrays.toString(data));
    }

    public void onFacebookActionsChange(FacebookState states, Object[] data) {
        switch(states){
            case LOGIN_SUCCESS:
                Log.e(TAG, "success " + ((LoginResult)data[0]).getAccessToken());
                Log.e(TAG, "success " + mActivity.tryGetFacebookToken().getToken());
                Profile p=mActivity.tryGetFacebookProfile();
                mActivity.tryGetEmail();
                Log.e(TAG, "success person = " + String.valueOf(p));
                return;
            case GOT_EMAIL:
                Log.e(TAG, "email " + String.valueOf(data[0]) + " "  +String.valueOf(data[1])); break;
            case EMAIL_ERROR:
                Log.e(TAG, "email error " + (FacebookRequestError)data[0]); break;
            case LOGIN_CANCEL:
            case LOGIN_ERROR:
                mView.setUsernameError("error" + states.toString());
                return;
        }
    }
}
