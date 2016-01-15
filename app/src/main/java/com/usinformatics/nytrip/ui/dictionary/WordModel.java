package com.usinformatics.nytrip.ui.dictionary;

/**
 * Created by admin on 7/16/15.
 */
public class WordModel {

    private String word;
    private String transcription;
    private String translation;
    private String description;

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public String getDescription() {
        return description;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
