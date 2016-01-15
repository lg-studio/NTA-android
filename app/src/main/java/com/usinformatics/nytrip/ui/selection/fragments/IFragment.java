package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Fragment;

/**
 * Created by D1m11n on 01.07.2015.
 */
public interface IFragment {

    public enum Type{
        EPISODES, TASKS, MAP, REFRESH;
    }

    public Type getFragmentType();

    public void updateContent();

    public Fragment getInstance();

}
