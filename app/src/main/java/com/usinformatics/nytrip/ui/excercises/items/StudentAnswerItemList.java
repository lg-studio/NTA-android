package com.usinformatics.nytrip.ui.excercises.items;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.ExecutedAnswerModel;
import com.usinformatics.nytrip.ui.excercises.ExcerciseActivity;
import com.usinformatics.nytrip.ui.excercises.notifiers.StudentAnswerNotifier;

import java.util.ArrayList;

/**
 * Created by admin on 7/8/15.
 */
public class StudentAnswerItemList implements ChatItemList,StudentAnswerNotifier {

    private Holder holder;
    private Activity mActivity;
    private ArrayList<ExecutedAnswerModel> mMessages= new ArrayList<>();
    private LayoutInflater mInflater;



    public StudentAnswerItemList(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getViewType() {
        return ChatItemType.STUDENT_MESSAGE.ordinal();
    }

    @Override
    public int getCount() {
        return mMessages.size();
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
        holder.message.setText(mMessages.get(position).selectedAnswer);
        return convertView;
    }

    private View createView(LayoutInflater inflater) {
        if (inflater == null)
            mInflater = mActivity.getLayoutInflater();
        else {
            mInflater = inflater;
        }
        View view = mInflater.inflate(R.layout.item_student_massage, null, false);
        holder.message = (TextView) view.findViewById(R.id.student_massage);
        return view;
    }

    @Override
    public void notify(ExecutedAnswerModel answer, int position) {
        mMessages.add(answer);
    }

    private class Holder {
        TextView message;
    }
}
