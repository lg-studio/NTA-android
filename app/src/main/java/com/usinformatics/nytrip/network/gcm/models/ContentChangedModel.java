package com.usinformatics.nytrip.network.gcm.models;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by D1m11n on 30.07.2015.
 */
public class ContentChangedModel {

    private ContentChangedType mType;

    public String taskId;

    public String sceneId;

    public String episodeId;

    public String courseId;

    public String userId;

    public void setType(String type){
        if(TextUtils.isEmpty(type)){
            mType=null;
            return;
        }
        try {
            mType=ContentChangedType.valueOf(type.toUpperCase());
        }catch (IllegalArgumentException e){
                Log.e(ContentChangedModel.class.getSimpleName(), "illegal = " + e.toString());
                mType=null;
        }
    }

    public ContentChangedType getType(){
        return mType;
    }

    @Override
    public String toString() {
        return "ContentChangedModel{" +
                "mType=" + mType +
                ", taskId='" + taskId + '\'' +
                ", sceneId='" + sceneId + '\'' +
                ", episodeId='" + episodeId + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}
