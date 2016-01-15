package com.usinformatics.nytrip.socials.gplus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.usinformatics.nytrip.socials.gplus.listeners.GPLusActionsListener;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by D1m11n on 24.04.2015.
 * http://www.riskcompletefailure.com/2014/02/migrating-from-plusclient-to.html
 */
public abstract class GPlusManagerActivity extends AppCompatActivity implements GPLusActionsListener, ResultCallback<People.LoadPeopleResult> {

    private static final String TAG="GPLUS_ACTIVITY";
    private GoogleApiClient mApiClient;
    private Person mPerson;
    private GPlusEventsListener mEventsListener;
    private AtomicBoolean mIsOnProcess=new AtomicBoolean(false);

    public void startGplusApiClient(){
        mIsOnProcess.set(true);
        mEventsListener= new GPlusEventsListener(this, this);
        if (mApiClient!=null&&mApiClient.isConnected()){
            mEventsListener.onConnected(null);
            return;
        }
        mApiClient=GPlusApiBuilder.buildClient(this,mEventsListener, mEventsListener);
        mApiClient.connect();
    }

    public void stopGplusApiClient(){
        if (mApiClient==null)
            return;
        mApiClient.clearDefaultAccountAndReconnect();
        mApiClient.disconnect();
        mApiClient=null;
    }

    @Override
    public final void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if(mIsOnProcess.get())
          handleResult(requestCode,responseCode,intent);
        else
          this.unhandledGplusActivityResult(requestCode, responseCode, intent);
    }

    private void handleResult(int requestCode, int responseCode, Intent intent){
        Log.e(TAG, "onActivityResult " + requestCode + "RESPONSECODE = " + responseCode + "intent = " + intent);
        Log.e(TAG, "onActivityResult " + "client = " + mApiClient.isConnected() );
        Log.e(TAG, "onActivityResult " + "succcess is = " + ConnectionResult.SUCCESS );
        if (requestCode== GplusConsts.REQUEST_RESOLVE) {
            mApiClient.connect();
            return;
        }
        if (mApiClient!=null&&!mApiClient.isConnecting()) {
            mApiClient.connect();
            mEventsListener.onConnected(null);
            mIsOnProcess.set(false);
            return;
        }
    }

    protected abstract void unhandledGplusActivityResult(int requestCode, int responseCode, Intent intent);

    public boolean isGplusApiConnected(){
        return mApiClient!=null&&mApiClient.isConnected();
    }

    public Person getGplusMe(){
        getMyPersonIfNeeded();
        return mPerson!=null?mPerson:null;
    }

    private void getMyPersonIfNeeded(){
        Log.e("EMAIL ", " email = " + Plus.AccountApi.getAccountName(mApiClient));
        Log.e("EMAIL ", " connected = " + mApiClient.isConnected());
        Plus.PeopleApi.loadVisible(mApiClient, null).setResultCallback(this);

        String personName="Unknown";
        if (Plus.PeopleApi.getCurrentPerson(mApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mApiClient);
            personName = currentPerson.getDisplayName();
            Log.e(TAG, " gplusperson = " + personName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //http://stackoverflow.com/questions/26073699/get-access-token-for-my-application-from-the-googleapiclient-in-android
    //TODO UPDATE WITH PREVIOUS
    public void getGplusToken(){
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                try {
                    Log.e("TOKEN  = ", "IS CONNECTIED " + mApiClient.isConnected());
                    token = GoogleAuthUtil.getToken(
                            GPlusManagerActivity.this,
                            Plus.AccountApi.getAccountName(mApiClient),
                            "oauth2:" + Plus.SCOPE_PLUS_PROFILE);
                } catch (IOException transientEx) {
                    Log.e(TAG, transientEx.toString());
                    mEventsListener.onException(transientEx);
                } catch (UserRecoverableAuthException e) {
                    startActivityForResult(e.getIntent(), GplusConsts.AUTH_CODE_REQUEST);
                    mEventsListener.onException(e);
                } catch (GoogleAuthException authEx) {
                    mEventsListener.onException(authEx);
                    Log.e(TAG, authEx.toString());
                }
                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                if (!TextUtils.isEmpty(token))
                   mEventsListener.OnGetToken(token);
            }

        };
        task.execute();
    }

    public String getGPlusEmail(){
        getMyPersonIfNeeded();
        return Plus.AccountApi.getAccountName(mApiClient);
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        Log.e("EMAIL ===== ", String.valueOf(loadPeopleResult.getStatus()));
        PersonBuffer buf=loadPeopleResult.getPersonBuffer();
        Log.e("EMAIL ", String.valueOf(buf));
    }


}
