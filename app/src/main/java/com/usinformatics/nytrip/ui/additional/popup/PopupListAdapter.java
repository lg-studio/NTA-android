package com.usinformatics.nytrip.ui.additional.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.usinformatics.nytrip.R;

import java.util.ArrayList;

import common.utis.ListsUtils;

/**
 * Created by D1m11n on 16.06.2015.
 */
public class PopupListAdapter extends ArrayAdapter<ItemRawPopup> {


    private ArrayList<ItemRawPopup> mList;
    private Context mContext;
    private int notifications =-1;


    public PopupListAdapter(Context activity, ArrayList<ItemRawPopup> list) {
        super(activity,0);
        mContext =activity;
        mList=list;
    }

    @Override
    public int getCount() {
        return ListsUtils.isEmpty(mList)?0:mList.size();
    }

    @Override
    public ItemRawPopup getItem(int position) {
        return ListsUtils.isEmpty(mList)?null:mList.get(position);
    }

    public void updateNotifications(int value){
        notifications=value;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_popup_window, null, false);
            viewHolder = new ViewHolder();
            initViews(viewHolder,convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        fillViewHolderBy(getItem(position),viewHolder);
        return convertView;
    }



    private void initViews(ViewHolder viewHolder, View convertView) {
        viewHolder.title= (TextView) convertView.findViewById(R.id.tv_popup_title);
        viewHolder.subtitle= (TextView) convertView.findViewById(R.id.tv_popup_subtitle);
    }

    private void fillViewHolderBy(ItemRawPopup item, ViewHolder viewHolder) {
        if (item.equals(ItemRawPopup.NOTIFICATIONS)){
            if(notifications==-1)
                notifications= Integer.parseInt(item.getModel().menuSubtitle);
            viewHolder.subtitle.setText(String.valueOf(notifications));
        } else{
            viewHolder.subtitle.setText(String.valueOf(item.getModel().menuSubtitle));
        }
       viewHolder.title.setText(item.getModel().menuTitle);

       //viewHolder.ivIcon
       //viewHolder.ivExtra
    }

    private class ViewHolder{
        TextView title;
        TextView subtitle;
    }
}
