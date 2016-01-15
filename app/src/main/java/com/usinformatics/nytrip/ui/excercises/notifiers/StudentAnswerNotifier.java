package com.usinformatics.nytrip.ui.excercises.notifiers;

import com.usinformatics.nytrip.models.ExecutedAnswerModel;

/**
 * Created by D1m11n on 14.07.2015.
 */
public interface StudentAnswerNotifier {

    void notify(ExecutedAnswerModel answer,  int position);

}
