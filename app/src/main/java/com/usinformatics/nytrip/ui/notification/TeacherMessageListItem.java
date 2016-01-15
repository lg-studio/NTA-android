package com.usinformatics.nytrip.ui.notification;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.QuestionModel;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemList;
import com.usinformatics.nytrip.ui.excercises.items.ChatItemType;
import com.usinformatics.nytrip.ui.excercises.notifiers.QuestionNotifier;

import java.util.ArrayList;

import common.views.FontTextView;

/**
 * Created by admin on 7/28/15.
 */
public class TeacherMessageListItem implements ChatItemList, TeacherMassageNotifier {

    private Holder holder;
    private Activity mActivity;
    private ArrayList<TeacherMessageModel> mMessage = new ArrayList<>();
    private LayoutInflater mInflater;

    public TeacherMessageListItem(Activity activity){
        mActivity = activity;
    }

    @Override
    public int getViewType() {
        return ChatItemType.TEACHER_MESSAGE.ordinal();
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

        holder.teacherName.setText(mMessage.get(position).teacherName);
        holder.message.setText(mMessage.get(position).message);


        return convertView;
    }

    @Override
    public int getCount() {
        return mMessage.size();
    }

    @Override
    public void notify(TeacherMessageModel message) {
        mMessage.add(message);
    }


    private View createView(LayoutInflater inflater) {
        if (inflater == null)
            mInflater = mActivity.getLayoutInflater();
        else {
            mInflater = inflater;
        }
        View view = mInflater.inflate(R.layout.item_teacher_massage, null, false);
        holder.message = (FontTextView) view.findViewById(R.id.teacher_message);
        holder.teacherName = (FontTextView) view.findViewById(R.id.teacher_name);
        holder.teacherImage = (ImageView) view.findViewById(R.id.teacher_image);
        return view;
    }


    private class Holder {

        FontTextView message;
        FontTextView teacherName;
        ImageView teacherImage;
    }
}
