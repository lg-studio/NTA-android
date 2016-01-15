package com.usinformatics.nytrip.models;

import java.util.Arrays;

/**
 * Created by D1m11n on 14.07.2015.
 */
public class ChatModel/* implements Parcelable*/ {


    public QuestionModel [] questions;


    public long chatDuration;

    public long chatTimeEnded;

    public float mark;


    @Override
    public String toString() {
        return "ChatModel{" +
                "questions=" + Arrays.toString(questions) +
                ", chatDuration=" + chatDuration +
                ", chatTimeEnded=" + chatTimeEnded +
                ", mark=" + mark +
                '}';
    }

    public ChatModel() {
    }

//    public ChatModel(Parcel in) {
//        Parcelable[] parcelableArray =
//                in.readParcelableArray(ItemQuestion.class.getClassLoader());
//        if (parcelableArray != null)
//            questions = Arrays.copyOf(parcelableArray, parcelableArray.length, ItemQuestion[].class);
//    }

  /*  @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        questions = in.createTypedArray(ItemQuestion.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(questions, flags);
    }


    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel in) {
            return new ChatModel(in);
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

*/
}
