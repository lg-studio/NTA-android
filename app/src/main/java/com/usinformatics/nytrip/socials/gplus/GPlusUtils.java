package com.usinformatics.nytrip.socials.gplus;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

public class GPlusUtils {
	
	public static void setBtnSignInText( SignInButton btn, String text) {
		for (int i = 0; i < btn.getChildCount(); i++) {
			View v = btn.getChildAt(i);
			if (v instanceof TextView) {
				TextView tv = (TextView) v;
				tv.setText(text);
				return;
	}}}

}
