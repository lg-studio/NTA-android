package com.usinformatics.nytrip.network;

import android.app.Activity;
import android.util.Log;

import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;

import retrofit.RetrofitError;

/**
 * Created by D1m11n on 25.06.2015.
 */
public class NetworkErrorHelper {

    /**
     *
     * @return false if <b>NO ERROR</>
     */
    public static boolean showNetworkErrorDialogIfNeeded(final Activity activity, final RetrofitError error){
        if (error==null) return false;
        if (error.getResponse()==null){
            DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getMessage());
            Log.e("NETWORK_ERROR_HELPER","showNetworkErrorDialogIfNeeded; response null  error from " + activity.getLocalClassName());
            return true;
        }
        if (error.getKind()== RetrofitError.Kind.CONVERSION){
            DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getMessage());
            return true;
        }
        if(error.getResponse()!=null&&error.getResponse().getStatus()!=200) {
            DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getResponse().getReason());
            Log.e("NETWORK_ERROR_HELPER", "showNetworkErrorDialogIfNeeded; response not null error from " + activity.getLocalClassName());
            return true;
        }
        return false;
    }





    /**
     *
     * @param activity
     * @param error
     * @return null, if error null or response of error is null
     */
    public static String getNetworkErrorDescription(Activity activity, RetrofitError error){
        if (error==null) return null;
        if (error.getResponse()==null)
            return null;
        if (error.getResponse().getStatus()==401)
            return "User is unauthorized; " + error.getMessage();
        else
            return error.getMessage();
    }

}
