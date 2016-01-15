package com.usinformatics.nytrip.socials.gplus.listeners;


import com.usinformatics.nytrip.socials.gplus.models.GPlusState;

public interface GPLusActionsListener {
	

	void onGplusClientActionsChange(GPlusState states, Object... data);

}
