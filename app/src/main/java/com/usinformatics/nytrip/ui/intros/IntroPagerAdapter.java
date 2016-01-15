package com.usinformatics.nytrip.ui.intros;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class IntroPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<IntroFragment> fragments;

    public IntroPagerAdapter(FragmentManager fm, ArrayList<IntroFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if(ListsUtils.isEmpty(fragments))
           return null;
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return ListsUtils.isEmpty(fragments)?0:fragments.size();
    }
}
