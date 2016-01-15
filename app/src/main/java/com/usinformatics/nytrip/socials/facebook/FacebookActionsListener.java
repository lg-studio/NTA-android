package com.usinformatics.nytrip.socials.facebook;


import com.usinformatics.nytrip.socials.facebook.FacebookState;

public interface FacebookActionsListener  {
	

	void onFacebookActionsChange(FacebookState states, Object... data);

}
