package com.usinformatics.nytrip.models;

import com.usinformatics.nytrip.models.types.QuestionType;

import java.util.Arrays;

/**
 * Created by D1m11n on 14.07.2015.
 */
public class QuestionModel /*implements Parcelable */{

    public String id;

    public QuestionType type;

    public String urlAudioFromTeacher;

    public String text;

    public String [] variants;

    public String audioId;

    public String getText(){
        return String.valueOf(text);
    }

    public QuestionModel(){}

    public String[] getVariants() {
        return variants;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", urlAudioFromTeacher='" + urlAudioFromTeacher + '\'' +
                ", text='" + text + '\'' +
                ", variants=" + Arrays.toString(variants) +
                ", audioId='" + audioId + '\'' +
                '}';
    }

}
