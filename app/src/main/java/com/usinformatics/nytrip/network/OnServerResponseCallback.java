package com.usinformatics.nytrip.network;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by D1m11n on 19.06.2015.
 */
public abstract class OnServerResponseCallback<T> implements Callback<T> {

    @Override
    public void success(T o, Response response) {
        onResponse(o, response, null);
    }

    @Override
    public void failure(RetrofitError error) {
        onResponse(null, null, error);
    }

    public abstract void onResponse(T objects, Response responseBody, RetrofitError error);

}
