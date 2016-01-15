package com.usinformatics.nytrip.network;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.usinformatics.nytrip.DevConsts;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by D1m11n on 19.06.2015.
 */
class ServiceGenerator {


    private ServiceGenerator() {
    }


    public static <S> S createService(Class<S> serviceClass, String accessToken) {
        Log.e("SERVICE_GENERATOR", " TOKEN = " + accessToken);
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Api.BASE_URL)
                .setClient(new OkClient(new OkHttpClient()));
        if (DevConsts.IS_RETROFIT_LOGS_ENABLED)
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
        updateBuilderWith(accessToken, builder);
        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }


    /**
     * Onlu for register and account
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
        return ServiceGenerator.createService(serviceClass, null);
    }

    private static void updateBuilderWith(final String accessToken, RestAdapter.Builder builder) {
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json;versions=1");
            }
        });
        if (accessToken != null) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json;versions=1");
                    request.addHeader("Authorization", accessToken);
                }
            });
        }

    }


}
