package com.usinformatics.nytrip.socials.gplus;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.People;
import com.usinformatics.nytrip.socials.gplus.listeners.GPLusActionsListener;
import com.usinformatics.nytrip.socials.gplus.models.GPlusState;

class GPlusEventsListener implements ConnectionCallbacks, OnConnectionFailedListener{

    private static final String TAG="GPLUS_EVENTS_LISTENER";

    private Activity mActivity;
	private GPLusActionsListener mListener;
	private GoogleApiClient client;
	
	public GPlusEventsListener(Activity activity,  GPLusActionsListener listener) {
        mActivity=activity;
		mListener=listener;
	}


	@Override
	public void onConnected(Bundle connectionHint) {
		mListener.onGplusClientActionsChange(GPlusState.CONNECTED);
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.e(TAG, "DISCONNECTED i = " + i);
		mListener.onGplusClientActionsChange(i == ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED ? GPlusState.DISCONNECTED : GPlusState.NETWORK_LOST, null);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.e(TAG, "FAILED code=" + result.getErrorCode() + ", result =  " + result.getResolution());
        try {
            result.startResolutionForResult(mActivity,GplusConsts.REQUEST_RESOLVE);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "onCOnnectionFailedd " + e.toString());
        }
        mListener.onGplusClientActionsChange(GPlusState.FAILED, result);
	}

    void OnGetToken(String token){
        mListener.onGplusClientActionsChange(GPlusState.GOT_TOKEN, token);
    }

    public void onException(Exception e){
        mListener.onGplusClientActionsChange(GPlusState.EXCEPTION, e);
    }

    public void onGetPeople(People.LoadPeopleResult result){
        mListener.onGplusClientActionsChange(GPlusState.GOT_PEOPLE, result);
    }




}
