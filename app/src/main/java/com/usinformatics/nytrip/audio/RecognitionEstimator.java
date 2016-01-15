package com.usinformatics.nytrip.audio;

import android.text.TextUtils;

import org.simmetrics.StringMetrics;

import java.util.Arrays;
import java.util.List;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 20.07.2015.
 */
public final class RecognitionEstimator {


    public static enum EstimatorMode{
        LEVENSHTEIN, COSINE;
    }

    private RecognitionEstimator(){

    }

    public static float getEstimateOf(String src, String recognition, EstimatorMode mode){
        if(TextUtils.isEmpty(src)||TextUtils.isEmpty(recognition)||mode==null)
            return 0.0F;
        switch(mode){
            case COSINE:
                return getCosineEstimate(src,recognition);
            case LEVENSHTEIN:
                return getLevenshteinEstimate(src, recognition);
        }
        return 0.0F;
    }

    public static float getAverageEstimateOf(String src, String [] recognitions, EstimatorMode mode){
       return getAverageEstimateOf(src, Arrays.asList(recognitions), mode);
    }

    public static float getAverageEstimateOf(String src, List<String> recognitions, EstimatorMode mode){
        float [] estimates=getEstimatesOf(src, recognitions, mode);
        float summary=0.0F;
        for(int i=0; i<estimates.length; i++)
            summary+=estimates[i];
        return summary/estimates.length;

    }

    public static float [] getEstimatesOf(String src, List<String> recognitions, EstimatorMode mode){
        if(TextUtils.isEmpty(src)|| ListsUtils.isEmpty(recognitions)||mode==null)
            return new float[]{0.0F};
        float [] estimates=null;
        switch(mode){
            case COSINE:
                estimates=getLevenshteinEstimates(src, recognitions);
                break;
            case LEVENSHTEIN:
                estimates=getLevenshteinEstimates(src, recognitions);
                break;
        }
        if(ListsUtils.isEmpty(estimates))
           return new float[]{0.0F};
        else
           return estimates;
    }

    public static float getBestEstimateOf(String src, List<String> recognitions, EstimatorMode mode){
       float [] estimates=getEstimatesOf(src, recognitions, mode);
        float best=0.0f;
       for(int i=0; i<estimates.length; i++){
           if(best<estimates[i])
               best=estimates[i];
       }
        return best;
    }

    private static float [] getCosineEstimates(String src, List<String>  recognitions){
        int size=recognitions.size();
        float estimates[] = new float[size];
        for(int i=0; i<size; i++){
            estimates[i]=getCosineEstimate(src, recognitions.get(i));
        }
        return estimates;
    }

    private static float [] getLevenshteinEstimates(String src, List<String>  recognitions){
        int size=recognitions.size();
        float estimates[] = new float[size];
        for(int i=0; i<size; i++){
            estimates[i]=getLevenshteinEstimate(src, recognitions.get(i));
        }
        return estimates;
    }

    private static float getCosineEstimate(String src, String recognition){
        return  StringMetrics.cosineSimilarity().compare(src, recognition);
    }

    private static float getLevenshteinEstimate(String src, String recognition){
        return  StringMetrics.levenshtein().compare(src, recognition);
    }

}
