package com.usinformatics.nytrip.audio.callbacks;

import java.util.List;

/**
 * Created by D1m11n on 07.07.2015.
 */
public interface OnGetTextCallback {

    void onGetSpokenText(List<String> text, float[] marks);

    void onError(String error);
}
