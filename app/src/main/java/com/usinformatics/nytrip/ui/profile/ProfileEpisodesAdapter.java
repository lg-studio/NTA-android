package com.usinformatics.nytrip.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.usinformatics.nytrip.R;

import java.util.List;

/**
 * Created by admin on 7/3/15.
 */
public class ProfileEpisodesAdapter extends BaseAdapter {

    private List<EpisodeProgress> mProgressList;
    private Context mContext;

    public ProfileEpisodesAdapter(List<EpisodeProgress> progressList, Context context) {
        this.mProgressList = progressList;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return mProgressList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProgressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.profile_episode_item, parent, false);
            holder = new ViewHolder();
            holder.episodeNumber = (TextView) convertView.findViewById(R.id.profile_episode_number);
            holder.name = (TextView) convertView.findViewById(R.id.profile_episode_name);
            holder.progress = (TextView) convertView.findViewById(R.id.profile_episode_progress);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
            holder.episodeNumber.setText(mProgressList.get(position).getEpisodeNumber());
            holder.name.setText(mProgressList.get(position).getName());
            holder.progress.setText(mProgressList.get(position).getProgress());

        return convertView;

    }

    static class ViewHolder{
        TextView episodeNumber;
        TextView name;
        TextView progress;
    }
}
