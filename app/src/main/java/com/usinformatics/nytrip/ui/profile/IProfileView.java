package com.usinformatics.nytrip.ui.profile;

import com.usinformatics.nytrip.models.UserModel;

/**
 * Created by D1m11n on 22.06.2015.
 */
public interface IProfileView {



    public void showUser(UserModel user);


    public void showOverallProgress();

    public void showStatistics();

}
