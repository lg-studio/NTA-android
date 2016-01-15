package com.usinformatics.nytrip.ui.excercises.notifiers;

import com.usinformatics.nytrip.models.QuestionModel;

/**
 * Created by D1m11n on 14.07.2015.
 */
public interface QuestionNotifier {

    void notify(QuestionModel question, int position);

}
