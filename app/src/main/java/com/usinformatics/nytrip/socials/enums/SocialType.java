package com.usinformatics.nytrip.socials.enums;

public enum SocialType {
	
		GENERAL("false"),
		FACEBOOK("fb"),
		GPLUS("gplus");
		
		private String abbrev;
		
		private SocialType(String serverValue) {
			this.abbrev=serverValue;
		}
		
		public String getServerValue(){
			return abbrev;
	}

}
