package com.usinformatics.nytrip.models;

import android.support.annotation.NonNull;

/**
 * Created by D1m11n on 16.07.2015.
 */

//TODO REFACTOR NAME
public class ExecutedAnswerModel  {


    //public AnswerModel answer;

    public float mark;

    public String selectedAnswer;

    @NonNull
    public String selectedQuestion;

    public int triesCount;


    public int getMarkInPercent(){
        return (int)(mark*100);
    }



}
