package com.usinformatics.nytrip.socials.gplus.listeners;


import com.usinformatics.nytrip.socials.gplus.models.GPlusPerson;

import java.util.List;

public interface OnGetFriendsListener {
	
	public void onGet(List<GPlusPerson> list);

}
