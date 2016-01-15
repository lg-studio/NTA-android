package com.usinformatics.nytrip.socials.gplus.listeners;


import com.usinformatics.nytrip.socials.gplus.models.GPlusPerson;

public interface OnGConnectListener {
	
	public void onConnect(GPlusPerson person, Exception error);

}
