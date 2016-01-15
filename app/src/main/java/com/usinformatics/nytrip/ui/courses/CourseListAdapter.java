package com.usinformatics.nytrip.ui.courses;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.CourseModel;
import com.usinformatics.nytrip.storages.StorageFactory;

import java.util.ArrayList;

import common.utis.ListsUtils;
import common.views.FontTextView;

/**
 * Created by admin on 7/9/15.
 */
public class CourseListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CourseModel> mCourseList;
    private int mSelectedPosition = -1;
    private RadioButton mSelectedRB;
    private RelativeLayout selectedCourse;
    private Holder holder;

    public CourseListAdapter(Context context, ArrayList<CourseModel> coursesList) {
        this.mContext = context;
        this.mCourseList = coursesList;
        findSelectedSemester();
    }

    private void findSelectedSemester() {
        String idSemester = StorageFactory.getUserStorage(mContext).getCurrentCourseId();
        if (ListsUtils.isEmpty(mCourseList) || TextUtils.isEmpty(idSemester))
            mSelectedPosition = -1;
        for (int i = 0; i < mCourseList.size(); i++)
            if (mCourseList.get(i).id.equals(idSemester)) {
                mSelectedPosition = i;
                return;
            }
    }

    @Override
    public int getCount() {
        return ListsUtils.isEmpty(mCourseList) ? 0 : mCourseList.size();
    }

    @Override
    public Object getItem(int position) {
        return ListsUtils.isEmpty(mCourseList) ? null : mCourseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_select_semester, parent, false);
            holder = new Holder();
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.semester_radio_btn);
            holder.courseImage = (ImageView) convertView.findViewById(R.id.semester_image);
            holder.courseName = (FontTextView) convertView.findViewById(R.id.semester_name);
            holder.chooseCourse = (RelativeLayout) convertView.findViewById(R.id.select_semester_btn);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.courseName.setText(mCourseList.get(position).getName());
        holder.chooseCourse.setTag(holder);
        holder.radioButton.setClickable(false);

        if (position == mSelectedPosition) {
            setItemChecked();
        } else {
            setItemUnchecked();
        }
        initSelectSemester(position);
        return convertView;
    }

    private void setItemUnchecked() {
        holder.radioButton.setChecked(false);
        holder.chooseCourse.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }

    private void setItemChecked() {
        holder.radioButton.setChecked(true);
        mSelectedRB = holder.radioButton;
        selectedCourse = holder.chooseCourse;
        holder.chooseCourse.setBackgroundColor(mContext.getResources().getColor(R.color.orange_lite));

    }

    private void initSelectSemester(final int position) {
        holder.chooseCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder h = (Holder) v.getTag();
                h.radioButton.setChecked(true);
                v.setBackgroundColor(mContext.getResources().getColor(R.color.orange_lite));

                if (position != mSelectedPosition && mSelectedRB != null) {
                    mSelectedRB.setChecked(false);
                    selectedCourse.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                mSelectedPosition = position;
                mSelectedRB = (RadioButton) h.radioButton;
                selectedCourse = (RelativeLayout) h.chooseCourse;
            }

        });
    }

    private class Holder {
        ImageView courseImage;
        FontTextView courseName;
        RadioButton radioButton;
        RelativeLayout chooseCourse;
    }
}
