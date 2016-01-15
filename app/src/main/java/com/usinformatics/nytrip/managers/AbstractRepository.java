package com.usinformatics.nytrip.managers;

/**
 * Created by D1m11n on 28.07.2015.
 */
 abstract class AbstractRepository {

    protected static void notifyError(RepositoryCallback<?> callback, String error){
        if(callback!=null)
            callback.onError(error);
    }


    protected static <T> void notifySuccess(RepositoryCallback<T> callback, T success){
        if(callback!=null)
            callback.onSuccess(success);
    }

}
