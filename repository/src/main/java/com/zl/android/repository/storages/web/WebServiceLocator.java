package com.zl.android.repository.storages.web;

import com.squareup.okhttp.OkHttpClient;
import com.zl.android.repository.features.catalog.CatalogWebService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

// access point to all web services
public class WebServiceLocator {

    private static final String API_VERSION="/v1";
    private static final String BASE_URL ="http://ny-api.herokuapp.com" + API_VERSION;

    private final CatalogWebService mWebService;
    private String mAccessToken;

    public WebServiceLocator() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json;versions=1");
                        String token = getAccessToken();
                        if (token != null) {
                            request.addHeader("Authorization", token);
                        }
                    }
                })
                .build();
        mWebService = adapter.create(CatalogWebService.class);
    }

    public CatalogWebService getCatalogWebService() {
        return mWebService;
    }

    public void setAccessToken(String token) {
        mAccessToken = token;
    }

    private String getAccessToken() {
        return mAccessToken;
    }

}
