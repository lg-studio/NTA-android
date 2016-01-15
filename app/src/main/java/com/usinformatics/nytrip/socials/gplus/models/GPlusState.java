package com.usinformatics.nytrip.socials.gplus.models;

public enum GPlusState {
	
	CONNECTED,
	DISCONNECTED,
	NETWORK_LOST,

    /**
     * Object data will show ConnectionResult data
     */
    FAILED,

    /**
     * Object data[0] will have String token
     */
    GOT_TOKEN,
	UNKNOWN,

    /**
     * Object data[0] will have People.LoadPeopleResult
     */
    GOT_PEOPLE,

    EXCEPTION
	

}
