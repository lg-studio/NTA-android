package com.usinformatics.nytrip.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.usinformatics.nytrip.helpers.NetworkHelper;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * Created by D1m11n on 25.06.2015.
 */
public class NetworkErrorHelper {

    private static final String TAG="NETWORK_ERROR_HELPER";

    /**
     *
     * @return false if <b>NO ERROR</>
     */
    public static boolean showNetworkErrorDialogIfNeeded(final Activity activity, final RetrofitError error){
        if (error==null) return false;
        printRetrofitError(error);
        if (error.getResponse()==null){
            DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getMessage());
            Log.e(TAG,"showNetworkErrorDialogIfNeeded; response null  error from " + activity.getLocalClassName());
            return true;
        }
        if (error.getKind()== RetrofitError.Kind.CONVERSION){
            DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getMessage());
            return true;
        }
        if(error.getResponse()!=null&&error.getResponse().getStatus()!=200) {
            DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getResponse().getReason());
            Log.e(TAG, "showNetworkErrorDialogIfNeeded; response not null error from " + activity.getLocalClassName());
            return true;
        }
        return false;
    }


    public  static String getErrorString(Context context, final RetrofitError error){
        if (error==null) return null;
        if(error.getMessage()!=null&&(error.getMessage().contains("Unable to resolve host")||error.getMessage().toLowerCase().contains("failed to connect")))
            return "Cannot connect with server." + (NetworkHelper.isConnectingToInternet(context)?"":" Check your settings.");
        printRetrofitError(error);
        String body=tryGetValueOfErrorBody(error);
        if(body!=null)  return body;
        if (error.getResponse()==null){
            return error.getMessage();
        }
        if (error.getKind()== RetrofitError.Kind.CONVERSION){
            return error.getMessage();
        ///    DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getMessage());
        //    return true;
        }
        if(error.getResponse()!=null&&error.getResponse().getStatus()!=200) {
            //DialogFactory.showSimpleOneButtonDialog(activity, error.getKind().toString(), error.getResponse().getReason());
            //Log.e("NETWORK_ERROR_HELPER", "showNetworkErrorDialogIfNeeded; response not null error from " + activity.getLocalClassName());
            //return true;
            return error.getResponse().getReason();
        }
        return null;
    }

    private static void printRetrofitError(RetrofitError error) {
        Log.e(TAG,"KIND = " + error.getKind());
        Log.e(TAG,"MESSAGE= " + error.getMessage());
        if(error.getResponse()!=null) {
            Log.e(TAG, "REASON = " + error.getResponse().getReason());
            Log.e(TAG, "BODY = " + NetworkUtils.getResponseBody(error.getResponse()));
            Log.e(TAG, error.getResponse().getReason());
        }
    }


//    /**
//     *
//     * @param activity
//     * @param error
//     * @return null, if error null or response of error is null
//     */
//    public static String getNetworkErrorDescription(Activity activity, RetrofitError error){
//        if (error==null) return null;
//        if (error.getResponse()==null)
//            return null;
//        if (error.getResponse().getStatus()==401)
//            return "User is unauthorized; " + error.getMessage();
//        else
//            return error.getMessage();
//    }

    private static String tryGetValueOfErrorBody(RetrofitError error){
        try{
            String err=NetworkUtils.getResponseBody(error.getResponse());
            if(err==null)
                return null;
            JSONObject obj=new JSONObject(err);
            if(obj.has("error"))
                return obj.getString("error");
            return err;
        }catch (JSONException e){
            Log.e(TAG,"try get value of ErrorBody = " + e.toString());
            return NetworkUtils.getResponseBody(error.getResponse());
        }
    }

}
