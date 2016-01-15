package com.usinformatics.nytrip.ui.profile;

import android.app.Fragment;

/**
 * Created by D1m11n on 06.07.2015.
 */
interface IFragment {

    public enum Type{
        OVERVIEW, STATISTICS;
    }

    public String getTitle();

    public Fragment getInstance();


    public void update();

//    public Type getType();


}
