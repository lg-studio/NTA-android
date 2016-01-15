package com.usinformatics.nytrip.socials.gplus.models;

import com.google.android.gms.plus.model.people.Person;

import java.net.URI;

public class GPlusPerson {
	
	
	public String id;
	public String displayName;
	public String nickName;
	public String name;
	public URI avatarURL;
	public String accountName;
	
	public GPlusPerson() {
	}
	
	public GPlusPerson(Person user){
		generateFrom(user);
	}
	
	void generateFrom(Person user){
		id=user.getId();
        accountName=user.hasName()?(user.getName().getFamilyName()):"";
		displayName=user.getDisplayName()!=null?user.getDisplayName():"";
		nickName=user.getNickname()!=null?user.getNickname():"";
		if (user.hasCover()&&user.getCover().hasCoverPhoto()){
		  String url=user.getCover().getCoverPhoto().getUrl();
		  avatarURL=URI.create(url!=null?url:"");
		}
	}

	
	public String toString(){
		return "id = " + id+
			   ", displayName = " +displayName +
			   ", nickname = "    +nickName +
			   ", name = " + name+
               ", accountName = " + accountName +
			   ", avatarURL = " + avatarURL; 
	}
	

}
