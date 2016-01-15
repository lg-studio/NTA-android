package com.usinformatics.nytrip.ui.excercises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.usinformatics.nytrip.ui.excercises.items.ChatItemList;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemType;

import java.util.ArrayList;

/**
 * Created by admin on 7/8/15.
 */
public class ChatAdapter extends ArrayAdapter<ChatItemList> {

    protected static LayoutInflater mInflater;
    private ArrayList<ChatItemList> mList;

    public ChatAdapter( Context context, ArrayList<ChatItemList> list){
        super( context, 0, list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    public void addItem(ChatItemList item){
        this.add(item);
        notifyDataSetChanged();
    }

    @Override
    public ChatItemList getItem(int position) {
           return mList.get(getItemViewType(position));
    }

    @Override
    public int getCount() {
        int size=0;
        for(int i=0; i<mList.size(); i++)
            size+=mList.get(i).getCount();
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getViewTypeCount() {
        return ChatItemType.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, position/2, convertView);
    }
}
