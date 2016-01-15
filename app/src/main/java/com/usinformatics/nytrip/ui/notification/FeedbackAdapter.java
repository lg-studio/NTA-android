package com.usinformatics.nytrip.ui.notification;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.usinformatics.nytrip.R;

import java.util.ArrayList;

import common.views.FontTextView;

/**
 * Created by admin on 7/27/15.
 */
public class FeedbackAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<FeedbackModel> feedback;
    private Holder holder;


    public FeedbackAdapter(Context mContext, ArrayList<FeedbackModel> feedback) {
        this.mContext = mContext;
        this.feedback = feedback;
    }

    @Override
    public int getCount() {
        return feedback.size();
    }

    @Override
    public Object getItem(int position) {
        return feedback.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_notification_feedback, parent, false);
            holder = new Holder();
            holder.taskName = (FontTextView) convertView.findViewById(R.id.feedback_task_name);
            holder.taskPath = (FontTextView) convertView.findViewById(R.id.feedback_task_path);
            holder.taskScore = (FontTextView) convertView.findViewById(R.id.feedback_score);
            holder.finishedTime = (FontTextView) convertView.findViewById(R.id.feedback_finished_time);
            holder.imageCircle = (ImageView) convertView.findViewById(R.id.feedback_score_circle);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        int colour = getAppropriateColour(feedback.get(position).getScore());

        holder.taskPath.setText(feedback.get(position).getTaskPath());
        holder.taskName.setText(feedback.get(position).getTaskName());
        holder.finishedTime.setText(feedback.get(position).getFinishedTime());

        holder.taskScore.setText(feedback.get(position).getScore() + "%");
        holder.taskScore.setTextColor(colour);

        return convertView;
    }

    private int getAppropriateColour(int score) {
        if (score < 35) {
            return mContext.getResources().getColor(R.color.red_54);

        } else if (score < 70) {
            return mContext.getResources().getColor(R.color.orange);

        } else {
            return mContext.getResources().getColor(R.color.green);
        }
    }

    private class Holder {
        FontTextView taskPath;
        FontTextView taskName;
        FontTextView taskScore;
        FontTextView finishedTime;
        ImageView imageCircle;
    }
}
