package com.usinformatics.nytrip.socials.gplus;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;


class GPlusApiBuilder {

	
	public static GoogleApiClient buildClient(Context context, GoogleApiClient.ConnectionCallbacks callback, GoogleApiClient.OnConnectionFailedListener listener){
		return new GoogleApiClient.Builder(context)
				.addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN)
				.addScope(Plus.SCOPE_PLUS_PROFILE)
				.addOnConnectionFailedListener(listener)
				.addConnectionCallbacks(callback)
				.build();
	}
	

}
