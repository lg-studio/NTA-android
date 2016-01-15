package com.usinformatics.nytrip.network.models;

import com.google.gson.annotations.SerializedName;
import com.usinformatics.nytrip.network.Api;

import java.util.Locale;

/**
 * Created by D1m11n on 14.07.2015.
 */
public class NetQuestionModel {

    public String id;

    @SerializedName("_type")
    public String type;

    @SerializedName("audioId")
    private String urlAudioFromTeacher;

    @SerializedName("audioResponseId")
    public String audioResponseId;

    private Object text;

    public String getText() {
        if (text == null) {
            return "Error";
        }
        return text.toString();
    }

    @SerializedName("answers")
    public String[] mVariants;

    public String getUrlAudioFromTeacher() {
        if (urlAudioFromTeacher == null) return null;
        return String.format(Locale.ENGLISH, Api.URI_TASK_AUDIO, urlAudioFromTeacher);
    }
}
