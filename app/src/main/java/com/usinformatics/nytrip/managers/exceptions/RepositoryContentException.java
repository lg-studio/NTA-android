package com.usinformatics.nytrip.managers.exceptions;

/**
 * Created by D1m11n on 30.07.2015.
 */
public class RepositoryContentException extends Exception {


    public RepositoryContentException(String message){
        super("RepositoryContentException: " + message);
    }

}
