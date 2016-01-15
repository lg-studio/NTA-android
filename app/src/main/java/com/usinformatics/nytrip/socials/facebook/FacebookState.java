package com.usinformatics.nytrip.socials.facebook;

public enum FacebookState {

    /**
     * data[0] will have LoginResult loginResult
     */
    LOGIN_SUCCESS,
    LOGIN_CANCEL,
    LOGIN_ERROR,

    /**
     * data[0] will have old and data[1] new AccessToken
     */
    TOKEN_CHANGED,

    /**
     * data[0] will have old and data[1] new Profile
     */
    PROFILE_CHANGED,

    EMAIL_ERROR,

    /**
     * data[0] will have email, data[1] will have all aditional info about me in json format, Info depends on permissions
     */
    GOT_EMAIL;
	

}
