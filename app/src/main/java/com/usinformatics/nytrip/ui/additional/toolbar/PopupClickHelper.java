package com.usinformatics.nytrip.ui.additional.toolbar;

import android.app.Activity;
import android.content.Intent;

import com.usinformatics.nytrip.helpers.UserActionsHelper;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.dictionary.DictionaryActivity;
import com.usinformatics.nytrip.ui.profile.ProfileActivity;
import com.usinformatics.nytrip.ui.courses.CourseSelectionActivity;
import com.usinformatics.nytrip.ui.settings.SettingsActivity;
import com.usinformatics.nytrip.ui.user_activity.UserActivity;

/**
 * Created by D1m11n on 22.06.2015.
 */
public class PopupClickHelper {

    public static void onClick(final Activity activity, final ItemRawPopup popup) {
        switch (popup) {
            case LOGOUT:
                onLogout(activity);
                break;
            case PROFILE:
                onProfile(activity);
                break;
            case NOTIFICATIONS:
                onNotifications(activity);
                break;
            case SEMESTER_SELECTION:
                onSemester(activity);
                break;
            case SETTINGS:
                onSetting(activity);
                break;
            case USER_ACTIVITY:
                onUserActivity(activity);
                break;
            case DICTIONARY:
                onDictionary(activity);
        }
    }

    private static void onDictionary(Activity activity) {
        Intent intent = new Intent(activity, DictionaryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    private static void onUserActivity(Activity activity) {
        Intent intent = new Intent(activity, UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    private static void onNotifications(Activity activity) {

    }

    private static void onProfile(final Activity activity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    private static void onSemester(final Activity activity) {
        Intent intent = new Intent(activity, CourseSelectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    private static void onSetting(final Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    private static void onLogout(Activity activity) {
        UserActionsHelper.logout(activity);
    }
}
