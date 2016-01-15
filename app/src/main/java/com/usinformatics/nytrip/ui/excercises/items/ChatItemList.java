package com.usinformatics.nytrip.ui.excercises.items;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by admin on 7/8/15.
 */
public interface ChatItemList {

    public int getViewType();
    public View getView(LayoutInflater inflater, int position, View convertView);

    public int getCount();

}
