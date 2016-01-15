package com.usinformatics.nytrip;

import com.usinformatics.nytrip.models.AnswerModel;
import com.usinformatics.nytrip.models.ChatModel;
import com.usinformatics.nytrip.models.QuestionModel;
import com.usinformatics.nytrip.network.Api;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.intros.IntroModel;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class FakeData {


    public static final String SEMESTER_ID="5595c6afbea5bd2407c67708";

    public static final String EPISODE_ID="5595672dd049676425733398";

    public static final String SCENE_ID="5595672dd049676425733395";

    public static IntroModel [] getFakeIntro(){
        int size=3;
        IntroModel [] mm= new IntroModel[size];
        for (int i=0; i<size;i++){
            mm[i]= new IntroModel();
            mm[i].title="fragment " + i;
            mm[i].coverUrl="";
        }
        return mm;
    }

    public static ArrayList<ItemRawPopup> getPopupWindowAkaSettingsData(){
        ArrayList<ItemRawPopup> list = new ArrayList<>();
        for (int i=0; i< ItemRawPopup.values().length; i++){
            list.add(ItemRawPopup.values()[i]);
        }
        return list;
    }


//    public static ArrayList<TaskModel> getTaskslist(){
//        ArrayList<TaskModel> list = new ArrayList<>();
//        for (int i=0; i<20; i++){
//            TaskModel m= new TaskModel();
//            m.name="My name is Task " + i;
//            m.goalDescription="my description of task is null";
//            list.add(m);
//        }
//        return list;
//    }

    public static ChatModel getChat(){
        ChatModel mChat= new ChatModel();
        mChat.answers= new ArrayList<>();
        mChat.questions= new ArrayList<>();
        addFirstChat(mChat);
        addSecondChat(mChat);
        addThirdChat(mChat);
        //addFourthChat(mChat);
        //addFifthChat(mChat);
        return mChat;
    }

    private static void addFirstChat(ChatModel chat){
        AnswerModel a= new AnswerModel();
        a.variants= new String[]{"Hello", "Hi", "Wellcome"};
        QuestionModel q= new QuestionModel();
        q.setType(QuestionModel.Type.TEXT);
        q.text="Good afternoon";
        chat.questions.add(q);
        chat.answers.add(a);
    }

    private static void addSecondChat(ChatModel chat){
        AnswerModel a= new AnswerModel();
        a.variants= new String[]{"New York", "Lviv", "Lutsk"};
        QuestionModel q= new QuestionModel();
        q.setType(QuestionModel.Type.TEXT);
        q.text="Where are you from";
        ChatModel m= new ChatModel();
        chat.questions.add(q);
        chat.answers.add(a);
    }

    private static void addThirdChat(ChatModel chat){
        AnswerModel a= new AnswerModel();
        a.variants= new String[]{"about 18", "about 21", "more than 21"};
        QuestionModel q= new QuestionModel();
        q.setType(QuestionModel.Type.AUDIO);
        q.urlAudio= Api.BASE_URL + "/audio/55ae29c7c6f759480cb544f4";
        ChatModel m= new ChatModel();
        chat.questions.add(q);
        chat.answers.add(a);
    }

    private static void addFourthChat(ChatModel chat){
        AnswerModel a= new AnswerModel();
        a.variants= new String[]{"I'm a student", "I'm a teacher"};
        QuestionModel q= new QuestionModel();
        q.setType(QuestionModel.Type.TEXT);
        q.text="How are you?";
        ChatModel m= new ChatModel();
        chat.questions.add(q);
        chat.answers.add(a);
    }

    private static void addFifthChat(ChatModel chat){
        AnswerModel a= new AnswerModel();
        a.variants= new String[]{"Yes, it is", "No, it is not"};
        QuestionModel q= new QuestionModel();
        q.setType(QuestionModel.Type.AUDIO);
        q.urlAudio="";
        ChatModel m= new ChatModel();
        chat.questions.add(q);
        chat.answers.add(a);
    }




    public static final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();
        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }

}
