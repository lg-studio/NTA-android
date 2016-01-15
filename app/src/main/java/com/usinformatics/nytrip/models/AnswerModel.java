package com.usinformatics.nytrip.models;

/**
 * Created by D1m11n on 14.07.2015.
 */
public class AnswerModel /*implements Parcelable*/{

    public Object [] variants;

    private String [] strVariants;

    public String[] getVariants(){
        if(strVariants!=null)
            return strVariants;
        strVariants= new String[variants.length];
        for(int i=0; i<strVariants.length; i++)
            strVariants[i]=variants[i].toString();
        return strVariants;
    }


////    protected AnswerModel(Parcel in) {
////        strVariants = in.createStringArray();
////    }
//
//    public AnswerModel(){}
//
//    public static final Creator<AnswerModel> CREATOR = new Creator<AnswerModel>() {
//        @Override
//        public AnswerModel createFromParcel(Parcel in) {
//            return new AnswerModel(in);
//        }
//
//        @Override
//        public AnswerModel[] newArray(int size) {
//            return new AnswerModel[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeStringArray(getVariants());
//    }
}
