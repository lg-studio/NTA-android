package com.usinformatics.nytrip.socials.gplus;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class GooglePlayAvailabilityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GooglePlayAvailabilityException() {
		this("Google play services is not available; Minimum required = " + GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE + "; Update it please.");
	}

	public GooglePlayAvailabilityException(String message) {
		super(message);
	}

}
