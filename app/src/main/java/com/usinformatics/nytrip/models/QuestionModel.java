package com.usinformatics.nytrip.models;

import android.util.Log;

/**
 * Created by D1m11n on 14.07.2015.
 */
public class QuestionModel {

    public enum Type{
        AUDIO , TEXT;
    }

    private String type;

    public String urlAudio;

    public String text;

    @Deprecated
    public void setType(Type type){
        this.type=type.toString();
    }

    public Type getType(){
        if(type==null) return null;
        try {
            return Type.valueOf(type.toUpperCase());
        }catch (IllegalArgumentException e)  {
            Log.e("QUESTION_MODEL_TYPE", "illegal argument e = " + e.toString());
            return null;
        }
    }


}
