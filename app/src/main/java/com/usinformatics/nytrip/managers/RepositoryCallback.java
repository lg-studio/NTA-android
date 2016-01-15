package com.usinformatics.nytrip.managers;

/**
 * Created by D1m11n on 28.07.2015.
 */
public  interface RepositoryCallback<T> {

    public void onSuccess(T objects);

    public void onError(String error); //TODO UPDATE
}