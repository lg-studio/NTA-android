package com.usinformatics.nytrip.helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.usinformatics.nytrip.network.RequestExecutor;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.account.activities.AccountActivity;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;

/**
 * Created by D1m11n on 30.06.2015.
 */
public final class UserActionsHelper {

    private UserActionsHelper(){}

    public static void logout(final Activity activity) {
        if (activity==null) return;
        DialogFactory.showSimpleTwoButtonsDialog(activity, "Logout", "Do you want logout", new DialogFactory.OnOkClickListener() {
            @Override
            public void wasOkClicked(DialogInterface dialog, boolean wasOk) {
                if (wasOk) {
                    StorageFactory.getUserStorage(activity).clearUserData();
                    StorageFactory.getUserStorage(activity).setCurrentCourseId(null);
                    activity.startActivity(new Intent(activity, AccountActivity.class));
                    activity.finish();
                    RequestExecutor.getInstance(activity).reset();
                    StorageFactory.getEduStorage(activity).clearAllCourses();

                }
            }
        });
    }

//    public static void login(){
//        RequestExecutor.getInstance(mActivity).account(ModelBridge.getNetworkUserModel(user, password), new OnServerResponseCallback<NetworkUserModel>() {
//            @Override
//            public void onResponse(NetworkUserModel objects, Response responseBody, RetrofitError error) {
//                Log.e(TAG, "resp = " + objects + ", error " + error);
//                if (NetworkErrorHelper.showNetworkErrorDialogIfNeeded(mActivity, error)) {
//                    return;
//                }
//                Log.e(TAG, "user = " +user.toString());
//                Log.e(TAG, " network user  = " + objects.toString());
//                ModelBridge.updateModel(user, objects);
//                mUserStorage.saveUser(user);
//                Log.e(TAG, " access token after   = " +mUserStorage.getUser().token);
//                AccountPresenter.this.getSemesters();
//                initGCM();
//            }
//        });
//    }
}
