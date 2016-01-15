package com.usinformatics.nytrip.ui.user_activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.usinformatics.nytrip.R;

import java.util.ArrayList;

import common.views.FontTextView;

/**
 * Created by admin on 7/15/15.
 */
public class UserActivityAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UserActivityModel> userActivityList;
    private Holder holder;

    UserActivityAdapter(Context context, ArrayList<UserActivityModel> userActivityList) {
        mContext = context;
        this.userActivityList = userActivityList;
    }

    @Override
    public int getCount() {
        return userActivityList.size();
    }

    @Override
    public Object getItem(int position) {
        return userActivityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater layout = ((Activity) mContext).getLayoutInflater();
            convertView = layout.inflate(R.layout.item_user_activity_list, parent, false);
            holder = new Holder();
            holder.taskPath = ((FontTextView) convertView.findViewById(R.id.activity_task_path));
            holder.taskName = ((FontTextView) convertView.findViewById(R.id.activity_task_name));
            holder.taskTime = ((FontTextView) convertView.findViewById(R.id.activity_task_time));
            holder.taskDate = ((FontTextView) convertView.findViewById(R.id.activity_task_date));
            holder.taskRating = ((FontTextView) convertView.findViewById(R.id.activity_task_rating));
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.taskPath.setText(userActivityList.get(position).getTaskPath());
        holder.taskName.setText(userActivityList.get(position).getTaskName());
        holder.taskTime.setText(userActivityList.get(position).getTaskTime());
        holder.taskDate.setText(userActivityList.get(position).getTaskDate());
        holder.taskRating.setText(userActivityList.get(position).getTaskRating());

        return convertView;
    }


    private class Holder {
        FontTextView taskPath;
        FontTextView taskName;
        FontTextView taskTime;
        FontTextView taskDate;
        FontTextView taskRating;
    }

}
