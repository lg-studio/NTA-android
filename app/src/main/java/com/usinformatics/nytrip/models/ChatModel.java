package com.usinformatics.nytrip.models;

import java.util.ArrayList;

/**
 * Created by D1m11n on 14.07.2015.
 */
public class ChatModel {

    public enum ChatType{
        RECOGNITION, TEACHER, USER_CHOICE;
    }

    public ChatType chatType=ChatType.RECOGNITION;

    public ArrayList<QuestionModel> questions;
    public ArrayList<AnswerModel> answers;
}
