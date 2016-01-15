package com.usinformatics.nytrip.ui.profile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import common.utis.ListsUtils;

/**
 * Created by admin on 7/3/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<IFragment> mFragments;


    public ViewPagerAdapter(FragmentManager fm, ArrayList<IFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return ListsUtils.isEmpty(mFragments) ? null : mFragments.get(position).getInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  ListsUtils.isEmpty(mFragments) ? "" : mFragments.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return ListsUtils.isEmpty(mFragments) ? 0 : mFragments.size();
    }

}