package com.usinformatics.nytrip.ui.notification;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemList;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemType;

import common.views.FontTextView;

/**
 * Created by admin on 7/28/15.
 */
public class VoiceMessageListItem implements ChatItemList {

    private Holder holder;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public VoiceMessageListItem(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getViewType() {
        return ChatItemType.VOICE_MESSAGE.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, int position, View convertView) {
        if (convertView == null) {
            holder = new Holder();
            convertView = createView(inflater);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }


    private View createView(LayoutInflater inflater) {
        if (inflater == null)
            mInflater = mActivity.getLayoutInflater();
        else {
            mInflater = inflater;
        }
        View view = mInflater.inflate(R.layout.item_voice_massage, null, false);
        holder.teacherName = (FontTextView) view.findViewById(R.id.teacher_name);
        holder.teacherImage = (ImageView) view.findViewById(R.id.teacher_image);
        holder.playStopBtn = (ImageView) view.findViewById(R.id.message_play_btn);
        return view;
    }

    private class Holder {

        ImageView teacherImage;
        FontTextView teacherName;
        ImageView playStopBtn;
    }
}
