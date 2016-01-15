package com.usinformatics.nytrip.socials.facebook;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
	
	public static void generateHash(Context context){
		// Add code to print out the key hash
	    try {
	        PackageInfo info = context.getPackageManager().getPackageInfo(
	                "com.usinformatics.nytrip",
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {
	    	Log.e("KeyHash:", "error = " + e.toString());

	    } catch (NoSuchAlgorithmException e) {
	    	Log.e("KeyHash:", "error = " + e.toString());
	    }

	}

}
